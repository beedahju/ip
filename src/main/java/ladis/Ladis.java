package ladis;

import java.io.IOException;

import ladis.command.Command;
import ladis.command.DeleteCommand;
import ladis.command.ExitCommand;
import ladis.command.FindCommand;
import ladis.command.ListCommand;
import ladis.command.MarkCommand;
import ladis.command.UnmarkCommand;
import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.Task;
import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.util.Parser;

/**
 * Main entry point and controller for the Ladis task management application.
 * Manages the main application loop, coordinates between UI, Parser, Storage, and TaskList.
 */
public class Ladis {
    private final Storage storage;
    private TaskList tasks;
    private final UI ui;
    private final Parser parser;
    private String commandType;

    /**
     * Constructs a Ladis application instance with the specified data file path.
     *
     * @param filePath The path to the file where task data is stored.
     */
    public Ladis(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new UI();
        this.parser = new Parser();
        this.tasks = new TaskList();
        this.commandType = "";
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Oops! Something went wrong when loading from disk!");
        }
    }

    /**
     * Runs the main application loop.
     * Loads tasks from storage, displays welcome message, and continuously processes user commands
     * until an exit command is received.
     */
    public void run() {
        ui.showWelcome();

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Oops! Something went wrong when loading from disk!");
        }

        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                Command command = parser.parse(userInput);
                isExit = command.execute(tasks, ui, storage);
            } catch (LadisException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    /**
     * Entry point for the Ladis application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String... args) {
        new Ladis("./data/ladis.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     * Used by the GUI to process commands.
     *
     * @param input The user's input command.
     * @return The response from executing the command.
     */
    public String getResponse(String input) {
        try {
            assert input != null && !input.trim().isEmpty() : "Input should not be null or empty";
            Command command = parser.parse(input);
            assert command != null : "Parsed command should not be null";
            commandType = command.getClass().getSimpleName();
            assert commandType != null && !commandType.isEmpty() : "Command type should be set after parsing";

            if (command instanceof ExitCommand) {
                return "Goodbye! Hope to see you again soon!";
            } else if (command instanceof ListCommand) {
                return handleListCommand();
            } else if (command instanceof FindCommand) {
                return handleFindCommand(input);
            } else if (command instanceof MarkCommand) {
                return handleMarkCommand(input);
            } else if (command instanceof UnmarkCommand) {
                return handleUnmarkCommand(input);
            } else if (command instanceof DeleteCommand) {
                return handleDeleteCommand(input);
            } else {
                return handleAddCommand(command);
            }
        } catch (LadisException e) {
            commandType = "";
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Handles the list command by building a formatted list of all tasks.
     *
     * @return Formatted list of tasks or empty list message.
     */
    private String handleListCommand() {
        if (tasks.size() == 0) {
            return "You have no tasks.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.getTask(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Handles the find command by searching tasks by keyword.
     *
     * @param input The user input containing the search keyword.
     * @return Formatted list of matching tasks or no matches message.
     */
    private String handleFindCommand(String input) {
        String keyword = getKeywordFromInput(input);
        StringBuilder results = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                results.append(count).append(". ").append(task.toString()).append("\n");
            }
        }

        return count == 0 ? "No matching tasks found." : results.toString();
    }

    /**
     * Handles the mark command by marking a task as done.
     *
     * @param input The user input containing the task index.
     * @return Status message about the marked task.
     * @throws LadisException If an error occurs during task marking.
     */
    private String handleMarkCommand(String input) throws LadisException {
        int index = getLastTaskIndexFromMarkCommand(input);
        if (!isValidTaskIndex(index)) {
            return "Task marked as done!";
        }
        try {
            tasks.markTask(index);
            storage.save(tasks.getTasks());
            return "Nice! I've marked this task as done:\n  " + tasks.getTask(index);
        } catch (IOException e) {
            return "Task marked, but failed to save: " + e.getMessage();
        }
    }

    /**
     * Handles the unmark command by marking a task as not done.
     *
     * @param input The user input containing the task index.
     * @return Status message about the unmarked task.
     * @throws LadisException If an error occurs during task unmarking.
     */
    private String handleUnmarkCommand(String input) throws LadisException {
        int index = getLastTaskIndexFromMarkCommand(input);
        if (!isValidTaskIndex(index)) {
            return "Task unmarked!";
        }
        try {
            tasks.unmarkTask(index);
            storage.save(tasks.getTasks());
            return "OK, I've marked this task as not done yet:\n  " + tasks.getTask(index);
        } catch (IOException e) {
            return "Task unmarked, but failed to save: " + e.getMessage();
        }
    }

    /**
     * Handles the delete command by removing a task from the list.
     *
     * @param input The user input containing the task index.
     * @return Status message about the deleted task.
     */
    private String handleDeleteCommand(String input) {
        int index = getLastTaskIndexFromMarkCommand(input);
        if (!isValidTaskIndex(index)) {
            return "Task deleted!";
        }
        Task deletedTask = tasks.getTask(index);
        tasks.removeTask(index);
        try {
            storage.save(tasks.getTasks());
            return "Noted. I've removed this task:\n  " + deletedTask
                    + "\nNow you have " + tasks.size() + " task(s) in the list.";
        } catch (IOException e) {
            return "Task deleted, but failed to save: " + e.getMessage();
        }
    }

    /**
     * Handles add commands by executing the command and returning a status message.
     *
     * @param command The add command to execute.
     * @return Status message about the added task.
     * @throws LadisException If an error occurs during command execution.
     */
    private String handleAddCommand(Command command) throws LadisException {
        int beforeSize = tasks.size();
        command.execute(tasks, ui, storage);
        int afterSize = tasks.size();

        if (afterSize > beforeSize) {
            Task newTask = tasks.getTask(afterSize - 1);
            return "Got it. I've added this task:\n  " + newTask
                    + "\nNow you have " + afterSize + " task(s) in the list.";
        }
        return "Command executed successfully!";
    }

    /**
     * Checks if a task index is valid for the current task list.
     *
     * @param index The index to check.
     * @return true if the index is valid (non-negative and within bounds).
     */
    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Helper method to extract the task index from mark/unmark/delete commands.
     */
    private int getLastTaskIndexFromMarkCommand(String input) {
        try {
            assert input != null : "Input should not be null";
            String[] parts = input.trim().split("\\s+");
            if (parts.length > 1) {
                int index = Integer.parseInt(parts[1]) - 1; // Convert to 0-indexed
                assert index >= -1 : "Parsed task index should be a valid integer";
                return index;
            }
        } catch (NumberFormatException e) {
            // Ignore and return -1
        }
        return -1;
    }

    /**
     * Helper method to extract the keyword from find command.
     */
    private String getKeywordFromInput(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length > 1) {
            return parts[1];
        }
        return "";
    }

    /**
     * Gets the type of the last command that was executed.
     *
     * @return The simple class name of the command.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Checks if the last command was an exit command.
     *
     * @return true if the last command was an exit command, false otherwise.
     */
    public boolean isExitCommand() {
        return commandType.equals("ExitCommand");
    }
}
