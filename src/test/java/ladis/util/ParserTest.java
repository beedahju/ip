package ladis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parse_byeCommand() throws LadisException {
        Command command = parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parse_listCommand() throws LadisException {
        Command command = parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_todoCommand() throws LadisException {
        Command command = parser.parse("todo Buy groceries");
        assertInstanceOf(AddTodoCommand.class, command);
    }

    @Test
    void parse_deadlineCommand() throws LadisException {
        Command command = parser.parse("deadline Submit report /by 2024-12-25");
        assertInstanceOf(AddDeadlineCommand.class, command);
    }

    @Test
    void parse_eventCommand() throws LadisException {
        Command command = parser.parse("event Conference /from 2024-12-20 /to 2024-12-22");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    void parse_markCommand() throws LadisException {
        Command command = parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parse_unmarkCommand() throws LadisException {
        Command command = parser.parse("unmark 2");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    void parse_deleteCommand() throws LadisException {
        Command command = parser.parse("delete 3");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_unknownCommand() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.parse("invalid");
        });
        assertTrue(exception.getMessage().contains("don't know"));
    }

    @Test
    void getCommand_extractsFirstWord() {
        assertEquals("bye", parser.getCommand("bye"));
        assertEquals("list", parser.getCommand("list"));
        assertEquals("todo", parser.getCommand("todo Buy milk"));
        assertEquals("deadline", parser.getCommand("deadline Task /by 2024-12-25"));
    }

    @Test
    void getTaskNumber_validNumber() throws LadisException {
        int taskNum = parser.getTaskNumber("mark 1", "mark");
        assertEquals(0, taskNum);

        taskNum = parser.getTaskNumber("mark 5", "mark");
        assertEquals(4, taskNum);
    }

    @Test
    void getTaskNumber_zeroIndexing() throws LadisException {
        int taskNum = parser.getTaskNumber("delete 1", "delete");
        assertEquals(0, taskNum);
    }

    @Test
    void getTaskNumber_invalidFormat() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getTaskNumber("mark abc", "mark");
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void getTaskNumber_missingNumber() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getTaskNumber("mark", "mark");
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void getTodoDescription_valid() throws LadisException {
        String desc = parser.getTodoDescription("todo Buy groceries");
        assertEquals("Buy groceries", desc);
    }

    @Test
    void getTodoDescription_withMultipleWords() throws LadisException {
        String desc = parser.getTodoDescription("todo Buy milk and eggs from the store");
        assertEquals("Buy milk and eggs from the store", desc);
    }

    @Test
    void getTodoDescription_empty() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getTodoDescription("todo");
        });
        assertTrue(exception.getMessage().contains("proper description"));
    }

    @Test
    void getTodoDescription_onlyCommand() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getTodoDescription("todo");
        });
        assertTrue(exception.getMessage().contains("proper description"));
    }

    @Test
    void getDeadlineInfo_validFormat() throws LadisException {
        String[] info = parser.getDeadlineInfo("deadline Submit report /by 2024-12-25");
        assertEquals(2, info.length);
        assertEquals("Submit report", info[0]);
        assertEquals("2024-12-25", info[1]);
    }

    @Test
    void getDeadlineInfo_withTime() throws LadisException {
        String[] info = parser.getDeadlineInfo("deadline Task /by 2024-12-25 1800");
        assertEquals(2, info.length);
        assertEquals("Task", info[0]);
        assertEquals("2024-12-25 1800", info[1]);
    }

    @Test
    void getDeadlineInfo_missingBy() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getDeadlineInfo("deadline Task");
        });
        assertTrue(exception.getMessage().contains("deadline"));
    }

    @Test
    void getDeadlineInfo_invalidFormat() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getDeadlineInfo("deadline Task /by date1 /by date2");
        });
        assertTrue(exception.getMessage().contains("Invalid"));
    }

    @Test
    void getEventInfo_validFormat() throws LadisException {
        String[] info = parser.getEventInfo("event Conference /from 2024-12-20 /to 2024-12-22");
        assertEquals(3, info.length);
        assertEquals("Conference", info[0]);
        assertEquals("2024-12-20", info[1]);
        assertEquals("2024-12-22", info[2]);
    }

    @Test
    void getEventInfo_withTimes() throws LadisException {
        String[] info = parser.getEventInfo("event Meeting /from 2024-12-25 0900 /to 2024-12-25 1700");
        assertEquals(3, info.length);
        assertEquals("Meeting", info[0]);
        assertEquals("2024-12-25 0900", info[1]);
        assertEquals("2024-12-25 1700", info[2]);
    }

    @Test
    void getEventInfo_missingFrom() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getEventInfo("event Conference /to 2024-12-22");
        });
        assertTrue(exception.getMessage().contains("When"));
    }

    @Test
    void getEventInfo_missingTo() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getEventInfo("event Conference /from 2024-12-20");
        });
        assertTrue(exception.getMessage().contains("When"));
    }

    @Test
    void getEventInfo_invalidFormat() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getEventInfo("event Conference /from date1 /to date2 /to date3");
        });
        assertTrue(exception.getMessage().contains("Invalid"));
    }

    @Test
    void parse_commandWithExtraSpaces() throws LadisException {
        Command command = parser.parse("mark   1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parse_findCommand() throws LadisException {
        Command command = parser.parse("find book");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    void getFindKeyword_validKeyword() throws LadisException {
        String keyword = parser.getFindKeyword("find book");
        assertEquals("book", keyword);
    }

    @Test
    void getFindKeyword_multipleWords() throws LadisException {
        String keyword = parser.getFindKeyword("find read a book");
        assertEquals("read a book", keyword);
    }

    @Test
    void getFindKeyword_withExtraSpaces() throws LadisException {
        String keyword = parser.getFindKeyword("find   book");
        assertEquals("book", keyword);
    }

    @Test
    void getFindKeyword_emptyKeyword() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getFindKeyword("find");
        });
        assertTrue(exception.getMessage().contains("search keyword"));
    }

    @Test
    void getFindKeyword_onlyCommand() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            parser.getFindKeyword("find");
        });
        assertTrue(exception.getMessage().contains("search keyword"));
    }
}
