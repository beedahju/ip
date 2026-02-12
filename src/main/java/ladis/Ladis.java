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
            Command command = parser.parse(input);
            commandType = command.getClass().getSimpleName();

            if (command instanceof ExitCommand) {
                return "Goodbye! Hope to see you again soon!";
            } else if (command instanceof ListCommand) {
                if (tasks.size() == 0) {
                    return "You have no tasks.";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append((i + 1)).append(". ").append(tasks.getTask(i)).append("\n");
                }
                return sb.toString();
            } else if (command instanceof FindCommand) {
                // Execute find by manually building results
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

                if (count == 0) {
                    return "No matching tasks found.";
                } else {
                    return results.toString();
                }
            } else if (command instanceof MarkCommand) {
                // Get the task index and mark it directly without calling execute() to avoid console output
                int index = getLastTaskIndexFromMarkCommand(input);
                if (index >= 0 && index < tasks.size()) {
                    try {
                        tasks.markTask(index);
                        storage.save(tasks.getTasks());
                    } catch (IOException e) {
                        return "Task marked, but failed to save: " + e.getMessage();
                    }
                    return "Nice! I've marked this task as done:\n  " + tasks.getTask(index);
                }
                return "Task marked as done!";
            } else if (command instanceof UnmarkCommand) {
                // Get the task index and unmark it directly without calling execute() to avoid console output
                int index = getLastTaskIndexFromMarkCommand(input);
                if (index >= 0 && index < tasks.size()) {
                    try {
                        tasks.unmarkTask(index);
                        storage.save(tasks.getTasks());
                    } catch (IOException e) {
                        return "Task unmarked, but failed to save: " + e.getMessage();
                    }
                    return "OK, I've marked this task as not done yet:\n  " + tasks.getTask(index);
                }
                return "Task unmarked!";
            } else if (command instanceof DeleteCommand) {
                // Get task info before deletion
                int index = getLastTaskIndexFromMarkCommand(input);
                Task deletedTask = null;
                if (index >= 0 && index < tasks.size()) {
                    deletedTask = tasks.getTask(index);
                    tasks.removeTask(index);
                    try {
                        storage.save(tasks.getTasks());
                    } catch (IOException e) {
                        return "Task deleted, but failed to save: " + e.getMessage();
                    }
                }
                if (deletedTask != null) {
                    return "Noted. I've removed this task:\n  " + deletedTask
                            + "\nNow you have " + tasks.size() + " task(s) in the list.";
                }
                return "Task deleted!";
            } else {
                // For add commands (AddTodoCommand, AddDeadlineCommand, AddEventCommand)
                int beforeSize = tasks.size();
                command.execute(tasks, ui, storage);
                int afterSize = tasks.size();

                if (afterSize > beforeSize) {
                    // A new task was added
                    Task newTask = tasks.getTask(afterSize - 1);
                    return "Got it. I've added this task:\n  " + newTask
                            + "\nNow you have " + afterSize + " task(s) in the list.";
                }
                return "Command executed successfully!";
            }
        } catch (LadisException e) {
            commandType = "";
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Helper method to extract the task index from mark/unmark/delete commands.
     */
    private int getLastTaskIndexFromMarkCommand(String input) {
        try {
            String[] parts = input.trim().split("\\s+");
            if (parts.length > 1) {
                return Integer.parseInt(parts[1]) - 1; // Convert to 0-indexed
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
