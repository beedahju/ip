public class Parser {
    
    public Command parse(String input) throws LadisExeception {
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
            default -> throw new LadisExeception("I'm sorry, but I don't know what that means :-(");
        };
    }

    public String getCommand(String input) {
        return input.split(" ")[0];
    }

    public int getTaskNumber(String input, String command) throws LadisExeception {
        try {
            int startIndex = command.length() + 1;
            return Integer.parseInt(input.substring(startIndex).trim()) - 1;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new LadisExeception("Very funny. Now give me a valid task number.");
        }
    }

    public String getTodoDescription(String input) throws LadisExeception {
        if (input.length() <= 4) {
            throw new LadisExeception("Please don't be funny and give me a proper description.");
        }
        return input.substring(5);
    }

    public String[] getDeadlineInfo(String input) throws LadisExeception {
        if (!input.contains(" /by ")) {
            throw new LadisExeception("So, when is the deadline?");
        }
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length != 2) {
            throw new LadisExeception("Invalid deadline format.");
        }
        return parts;
    }

    public String[] getEventInfo(String input) throws LadisExeception {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LadisExeception("Interesting event. When does it start and end?");
        }
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length != 3) {
            throw new LadisExeception("Invalid event format.");
        }
        return parts;
    }
}
