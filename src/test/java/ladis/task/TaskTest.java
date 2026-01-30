package ladis.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {
    private Task todoTask;
    
    @BeforeEach
    void setUp() {
        todoTask = new Todo("Buy groceries");
    }

    @Test
    void getDescription_returnsCorrectDescription() {
        assertEquals("Buy groceries", todoTask.getDescription());
    }

    @Test
    void getStatusIcon_initiallyUnmarked() {
        assertEquals("[ ]", todoTask.getStatusIcon());
    }

    @Test
    void mark_changesStatusIcon() {
        todoTask.mark();
        assertEquals("[X]", todoTask.getStatusIcon());
    }

    @Test
    void unmark_resetsStatusIcon() {
        todoTask.mark();
        todoTask.unmark();
        assertEquals("[ ]", todoTask.getStatusIcon());
    }

    @Test
    void getTaskType_returnsTodo() {
        assertEquals(TaskType.TODO, todoTask.getTaskType());
    }

    @Test
    void toFileString_unmarkedTask() {
        String fileString = todoTask.toFileString();
        assertTrue(fileString.startsWith("T | 0 |"));
        assertTrue(fileString.contains("Buy groceries"));
    }

    @Test
    void toFileString_markedTask() {
        todoTask.mark();
        String fileString = todoTask.toFileString();
        assertTrue(fileString.startsWith("T | 1 |"));
        assertTrue(fileString.contains("Buy groceries"));
    }

    @Test
    void toString_unmarkedTask() {
        String str = todoTask.toString();
        assertTrue(str.contains("[ ]"));
        assertTrue(str.contains("Buy groceries"));
    }

    @Test
    void toString_markedTask() {
        todoTask.mark();
        String str = todoTask.toString();
        assertTrue(str.contains("[X]"));
        assertTrue(str.contains("Buy groceries"));
    }

    @Test
    void multipleMarkUnmarkOperations() {
        assertFalse(todoTask.isDone());
        
        todoTask.mark();
        assertTrue(todoTask.isDone());
        
        todoTask.unmark();
        assertFalse(todoTask.isDone());
        
        todoTask.mark();
        assertTrue(todoTask.isDone());
    }
}
