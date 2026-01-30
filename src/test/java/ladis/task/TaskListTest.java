package ladis.task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ladis.exception.LadisException;

public class TaskListTest {
    private TaskList taskList;
    
    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void size_emptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    void addTask_singleTask() {
        Task task = new Todo("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
    }

    @Test
    void addTask_multipleTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));
        assertEquals(3, taskList.size());
    }

    @Test
    void getTask_retrievesCorrectTask() {
        Task task1 = new Todo("First task");
        Task task2 = new Todo("Second task");
        taskList.addTask(task1);
        taskList.addTask(task2);
        
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task2, taskList.getTask(1));
    }

    @Test
    void removeTask_removesAndReturnsCorrectTask() {
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);
        
        Task removed = taskList.removeTask(0);
        assertEquals(task1, removed);
        assertEquals(1, taskList.size());
        assertEquals(task2, taskList.getTask(0));
    }

    @Test
    void markTask_validIndex() throws LadisException {
        Task task = new Todo("Task to mark");
        taskList.addTask(task);
        
        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
    }

    @Test
    void markTask_invalidIndexNegative() {
        taskList.addTask(new Todo("Task"));
        
        LadisException exception = assertThrows(LadisException.class, () -> {
            taskList.markTask(-1);
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void markTask_invalidIndexOutOfBounds() {
        taskList.addTask(new Todo("Task"));
        
        LadisException exception = assertThrows(LadisException.class, () -> {
            taskList.markTask(5);
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void markTask_emptyList() {
        LadisException exception = assertThrows(LadisException.class, () -> {
            taskList.markTask(0);
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void unmarkTask_validIndex() throws LadisException {
        Task task = new Todo("Task");
        task.mark();
        taskList.addTask(task);
        
        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
    }

    @Test
    void unmarkTask_invalidIndexNegative() {
        taskList.addTask(new Todo("Task"));
        
        LadisException exception = assertThrows(LadisException.class, () -> {
            taskList.unmarkTask(-1);
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void unmarkTask_invalidIndexOutOfBounds() {
        taskList.addTask(new Todo("Task"));
        
        LadisException exception = assertThrows(LadisException.class, () -> {
            taskList.unmarkTask(10);
        });
        assertTrue(exception.getMessage().contains("valid task number"));
    }

    @Test
    void getTasks_returnsAllTasks() {
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);
        
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
    }

    @Test
    void constructor_withTasks() {
        ArrayList<Task> initialTasks = new ArrayList<>();
        initialTasks.add(new Todo("Task 1"));
        initialTasks.add(new Todo("Task 2"));
        
        TaskList newList = new TaskList(initialTasks);
        assertEquals(2, newList.size());
    }

    @Test
    void sequentialOperations() throws LadisException {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));
        
        assertEquals(3, taskList.size());
        
        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
        
        taskList.removeTask(1);
        assertEquals(2, taskList.size());
        
        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
    }
}
