package ladis.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        String description = "Buy milk, eggs, and bread from the store";
        Todo todo = new Todo(description);
        
        assertEquals(description, todo.getDescription());
    }

    @Test
    void toFileString_unmarkedTodo() {
        Todo todo = new Todo("Task description");
        String fileString = todo.toFileString();
        
        assertTrue(fileString.startsWith("T | 0 |"));
        assertTrue(fileString.contains("Task description"));
    }

    @Test
    void toFileString_markedTodo() {
        Todo todo = new Todo("Task description");
        todo.mark();
        String fileString = todo.toFileString();
        
        assertTrue(fileString.startsWith("T | 1 |"));
        assertTrue(fileString.contains("Task description"));
    }

    @Test
    void multipleOperations() {
        Todo todo = new Todo("Complex task");
        
        assertFalse(todo.isDone());
        assertTrue(todo.toString().contains("[ ]"));
        
        todo.mark();
        assertTrue(todo.isDone());
        assertTrue(todo.toString().contains("[X]"));
        
        todo.unmark();
        assertFalse(todo.isDone());
        assertTrue(todo.toString().contains("[ ]"));
        
        todo.mark();
        assertTrue(todo.isDone());
    }

    @Test
    void constructor_preservesDescriptionWithSpecialCharacters() {
        String description = "Buy @#$%, (test), and more!";
        Todo todo = new Todo(description);
        
        assertEquals(description, todo.getDescription());
    }

    @Test
    void getTaskType_alwaysReturnsTodo() {
        Todo todo1 = new Todo("First");
        Todo todo2 = new Todo("Second");
        
        assertEquals(TaskType.TODO, todo1.getTaskType());
        assertEquals(TaskType.TODO, todo2.getTaskType());
    }
}
