package ladis.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    void constructor_createsValidTodo() {
        Todo todo = new Todo("Buy groceries");

        assertEquals("Buy groceries", todo.getDescription());
        assertEquals(TaskType.TODO, todo.getTaskType());
        assertFalse(todo.isDone());
    }

    @Test
    void getDescription_returnsExactDescription() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
    }

    @Test
    void getTaskType_returnsTodo() {
        Todo todo = new Todo("Buy groceries");
        assertEquals(TaskType.TODO, todo.getTaskType());
    }

    @Test
    void toFileString_returnsTWithOneSpace() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("T 0 Buy groceries", todo.toFileString());
    }

    @Test
    void toFileString_marksCompletedWithX() {
        Todo todo = new Todo("Buy groceries");
        todo.mark();
        assertEquals("T 1 Buy groceries", todo.toFileString());
    }

    @Test
    void toString_formatsWithoutDeadlineOrEvent() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    void toString_showsCheckmarkWhenDone() {
        Todo todo = new Todo("Buy groceries");
        todo.mark();
        assertEquals("[T][X] Buy groceries", todo.toString());
    }
}
