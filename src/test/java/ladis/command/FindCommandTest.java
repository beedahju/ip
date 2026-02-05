package ladis.command;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.Deadline;
import ladis.task.Event;
import ladis.task.TaskList;
import ladis.task.Todo;
import ladis.ui.UI;

public class FindCommandTest {
    private TaskList taskList;
    private UI ui;
    private Storage storage;
    private FindCommand findCommand;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        ui = new UI();
        storage = null;
    }

    @Test
    void execute_findCommand_returnsFalse() throws LadisException {
        taskList.addTask(new Todo("Read a book"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_matchesSingleTask() throws LadisException {
        taskList.addTask(new Todo("Read a book"));
        taskList.addTask(new Todo("Buy groceries"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_matchesMultipleTasks() throws LadisException {
        taskList.addTask(new Todo("Read a book"));
        taskList.addTask(new Todo("Buy a book"));
        taskList.addTask(new Todo("Buy groceries"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_caseInsensitive() throws LadisException {
        taskList.addTask(new Todo("Read a BOOK"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_partialMatch() throws LadisException {
        taskList.addTask(new Todo("Reading is fun"));
        findCommand = new FindCommand("read");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_noMatches() throws LadisException {
        taskList.addTask(new Todo("Read a book"));
        taskList.addTask(new Todo("Buy groceries"));
        findCommand = new FindCommand("exercise");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_withDeadlineTasks() throws LadisException {
        taskList.addTask(new Todo("Read a book"));
        taskList.addTask(new Deadline("Submit book report", "2024-12-25"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_withEventTasks() throws LadisException {
        taskList.addTask(new Todo("Buy books"));
        taskList.addTask(new Event("Book club meeting", "2024-12-20", "2024-12-20"));
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_emptyTaskList() throws LadisException {
        findCommand = new FindCommand("book");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_singleCharacterKeyword() throws LadisException {
        taskList.addTask(new Todo("a book"));
        taskList.addTask(new Todo("read"));
        findCommand = new FindCommand("a");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }

    @Test
    void execute_findCommand_specialCharactersInTask() throws LadisException {
        taskList.addTask(new Todo("Task: Read book!"));
        findCommand = new FindCommand("Task:");
        boolean result = findCommand.execute(taskList, ui, storage);
        assertFalse(result);
    }
}
