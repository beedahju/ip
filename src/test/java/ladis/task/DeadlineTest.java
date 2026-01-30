package ladis.task;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ladis.exception.DateTimeParseException;

public class DeadlineTest {

    @Test
    void constructor_withDateOnly() throws DateTimeParseException {
        Deadline deadline = new Deadline("Complete project", "2024-12-25");
        
        assertEquals("Complete project", deadline.getDescription());
        assertEquals(LocalDate.of(2024, 12, 25), deadline.getDeadlineDay());
        assertNull(deadline.getDeadlineTime());
    }

    @Test
    void constructor_withDateAndTime() throws DateTimeParseException {
        Deadline deadline = new Deadline("Submit report", "2024-12-31 1800");
        
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(LocalDate.of(2024, 12, 31), deadline.getDeadlineDay());
        assertEquals(LocalTime.of(18, 0), deadline.getDeadlineTime());
    }

    @Test
    void constructor_withLocalDateAndTime() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(14, 30);
        Deadline deadline = new Deadline("Meeting", date, time);
        
        assertEquals("Meeting", deadline.getDescription());
        assertEquals(date, deadline.getDeadlineDay());
        assertEquals(time, deadline.getDeadlineTime());
    }

    @Test
    void getTaskType_returnsDeadline() throws DateTimeParseException {
        Deadline deadline = new Deadline("Task", "2024-12-25");
        assertEquals(TaskType.DEADLINE, deadline.getTaskType());
    }

    @Test
    void toFileString_withTimeIncluded() throws DateTimeParseException {
        Deadline deadline = new Deadline("Task", "2024-12-25 1800");
        String fileString = deadline.toFileString();
        
        assertTrue(fileString.contains("D"));
        assertTrue(fileString.contains("2024-12-25"));
        assertTrue(fileString.contains("1800"));
    }

    @Test
    void toFileString_withoutTime() throws DateTimeParseException {
        Deadline deadline = new Deadline("Task", "2024-12-25");
        String fileString = deadline.toFileString();
        
        assertTrue(fileString.contains("D"));
        assertTrue(fileString.contains("2024-12-25"));
        assertFalse(fileString.contains("1800"));
    }

    @Test
    void toString_formattingWithTime() throws DateTimeParseException {
        Deadline deadline = new Deadline("Submit homework", "2024-12-25 1800");
        String str = deadline.toString();
        
        assertTrue(str.contains("Submit homework"));
        assertTrue(str.contains("by:"));
        assertTrue(str.contains("6pm"));
    }

    @Test
    void toString_formattingWithoutTime() throws DateTimeParseException {
        Deadline deadline = new Deadline("Study", "2024-12-25");
        String str = deadline.toString();
        
        assertTrue(str.contains("Study"));
        assertTrue(str.contains("by:"));
    }
}