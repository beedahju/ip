package ladis.util;

import ladis.command.AddDeadlineCommand;
import ladis.command.AddEventCommand;
import ladis.command.AddTodoCommand;
import ladis.command.Command;
import ladis.command.DeleteCommand;
import ladis.command.ExitCommand;
import ladis.command.FindCommand;
import ladis.command.ListCommand;
import ladis.command.MarkCommand;
import ladis.command.UnmarkCommand;
import ladis.exception.LadisException;

/**
 * Parser class to interpret and parse user input into commands.
 * Handles command identification and extraction of command parameters.
 */
public class Parser {
    private static final int TODO_COMMAND_LENGTH = 4;
    private static final int DEADLINE_COMMAND_LENGTH = 8;
    private static final int EVENT_COMMAND_LENGTH = 5;
    private static final int COMMAND_OFFSET = 1; // Space after command

    /**
     * Parses the user input string into a Command object.
     *
     * @param input The user input string to parse.
     * @return A Command object corresponding to the parsed input.
     * @throws LadisException If the input command is not recognized.
     */
    public Command parse(String input) throws LadisException {
        String command = getCommand(input);

        Command result = switch (command) {
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(getTaskNumber(input, "mark"));
        case "unmark" -> new UnmarkCommand(getTaskNumber(input, "unmark"));
        case "todo" -> new AddTodoCommand(getTodoDescription(input));
        case "deadline" -> new AddDeadlineCommand(getDeadlineInfo(input));
        case "event" -> new AddEventCommand(getEventInfo(input));
        case "delete" -> new DeleteCommand(getTaskNumber(input, "delete"));
        case "find" -> new FindCommand(getFindKeyword(input));
        default -> throw new LadisException("I'm sorry, but I don't know what that means :-(");
        };
        assert result != null : "Parsed command should never be null";
        return result;
    }

    /**
     * Extracts the command word from the user input.
     *
     * @param input The user input string.
     * @return The first word of the input (the command).
     */
    public String getCommand(String input) {
        return input.split(" ")[0];
    }

    /**
     * Extracts and validates a task number from the user input.
     *
     * @param input The user input string.
     * @param command The command name (used to extract the task number position).
     * @return The task number as a zero-indexed integer.
     * @throws LadisException If the task number is invalid or missing.
     */
    public int getTaskNumber(String input, String command) throws LadisException {
        try {
            int startIndex = command.length() + COMMAND_OFFSET;
            return Integer.parseInt(input.substring(startIndex).trim()) - 1;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
    }

    /**
     * Extracts the description from a todo command.
     *
     * @param input The user input string.
     * @return The todo description.
     * @throws LadisException If no description is provided.
     */
    public String getTodoDescription(String input) throws LadisException {
        if (input.length() <= TODO_COMMAND_LENGTH) {
            throw new LadisException("Please don't be funny and give me a proper description.");
        }
        return input.substring(TODO_COMMAND_LENGTH + COMMAND_OFFSET);
    }

    /**
     * Extracts the deadline information from a deadline command.
     *
     * @param input The user input string.
     * @return An array of two strings: [description, deadline].
     * @throws LadisException If the deadline format is invalid.
     */
    public String[] getDeadlineInfo(String input) throws LadisException {
        if (!input.contains(" /by ")) {
            throw new LadisException("So, when is the deadline?");
        }
        String[] parts = input.substring(DEADLINE_COMMAND_LENGTH + COMMAND_OFFSET).split(" /by ");
        if (parts.length != 2) {
            throw new LadisException("Invalid deadline format.");
        }
        assert parts.length == 2 : "Deadline must have exactly 2 parts: description and deadline";
        return parts;
    }

    /**
     * Extracts the event information from an event command.
     *
     * @param input The user input string.
     * @return An array of three strings: [description, startDate, endDate].
     * @throws LadisException If the event format is invalid.
     */
    public String[] getEventInfo(String input) throws LadisException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LadisException("Interesting event. When does it start and end?");
        }
        String[] parts = input.substring(EVENT_COMMAND_LENGTH + COMMAND_OFFSET).split(" /from | /to ");
        if (parts.length != 3) {
            throw new LadisException("Invalid event format.");
        }
        assert parts.length == 3 : "Event must have exactly 3 parts: description, start date, and end date";
        return parts;
    }

    /**
     * Extracts the search keyword from a find command.
     *
     * @param input The user input string.
     * @return The search keyword.
     * @throws LadisException If no keyword is provided.
     */
    public String getFindKeyword(String input) throws LadisException {
        if (input.length() <= 4) {
            throw new LadisException("Please provide a search keyword.");
        }
        return input.substring(5).trim();
    }
}
