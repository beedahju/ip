package ladis.util;

import ladis.command.Command;
import ladis.exception.LadisException;
import ladis.command.ExitCommand;
import ladis.command.ListCommand;
import ladis.command.MarkCommand;
import ladis.command.UnmarkCommand;
import ladis.command.AddTodoCommand;
import ladis.command.AddDeadlineCommand;
import ladis.command.AddEventCommand;
import ladis.command.DeleteCommand;

public class Parser {
    
    public Command parse(String input) throws LadisException {
        String command = getCommand(input);

        return switch (command) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(getTaskNumber(input, "mark"));
            case "unmark" -> new UnmarkCommand(getTaskNumber(input, "unmark"));
            case "todo" -> new AddTodoCommand(getTodoDescription(input));
            case "deadline" -> new AddDeadlineCommand(getDeadlineInfo(input));
            case "event" -> new AddEventCommand(getEventInfo(input));
            case "delete" -> new DeleteCommand(getTaskNumber(input, "delete"));
            default -> throw new LadisException("I'm sorry, but I don't know what that means :-(");
        };
    }

    public String getCommand(String input) {
        return input.split(" ")[0];
    }

    public int getTaskNumber(String input, String command) throws LadisException {
        try {
            int startIndex = command.length() + 1;
            return Integer.parseInt(input.substring(startIndex).trim()) - 1;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
    }

    public String getTodoDescription(String input) throws LadisException {
        if (input.length() <= 4) {
            throw new LadisException("Please don't be funny and give me a proper description.");
        }
        return input.substring(5);
    }

    public String[] getDeadlineInfo(String input) throws LadisException {
        if (!input.contains(" /by ")) {
            throw new LadisException("So, when is the deadline?");
        }
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length != 2) {
            throw new LadisException("Invalid deadline format.");
        }
        return parts;
    }

    public String[] getEventInfo(String input) throws LadisException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LadisException("Interesting event. When does it start and end?");
        }
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length != 3) {
            throw new LadisException("Invalid event format.");
        }
        return parts;
    }
}
