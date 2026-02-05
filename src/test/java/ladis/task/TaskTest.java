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
    void getTaskType_returnsCorrectType() {
        assertEquals(TaskType.TODO, todoTask.getTaskType());
    }

    @Test
    void isDone_returnsFalseWhenNotMarked() {
        assertFalse(todoTask.isDone());
    }

    @Test
    void mark_setsTaskAsDone() {
        todoTask.mark();
        assertTrue(todoTask.isDone());
    }

    @Test
    void unmark_setsTaskAsNotDone() {
        todoTask.mark();
        assertTrue(todoTask.isDone());
        todoTask.unmark();
        assertFalse(todoTask.isDone());
    }

    @Test
    void toFileString_startsWithTaskTypeChar() {
        assertTrue(todoTask.toFileString().startsWith("T"));
    }

    @Test
    void toFileString_containsStatusAndDescription() {
        assertTrue(todoTask.toFileString().contains("0 Buy groceries"));
        todoTask.mark();
        assertTrue(todoTask.toFileString().contains("1 Buy groceries"));
    }

    @Test
    void toString_returnsFormattedString() {
        assertEquals("[T][ ] Buy groceries", todoTask.toString());
    }

    @Test
    void toString_showsCheckmarkWhenMarked() {
        todoTask.mark();
        assertEquals("[T][X] Buy groceries", todoTask.toString());
    }
}
