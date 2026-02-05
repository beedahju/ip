package ladis.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ladis.exception.DateTimeParseException;

public class EventTest {

    @Test
    void constructor_datesOnly() throws DateTimeParseException {
        Event event = new Event("Conference", "2024-12-20", "2024-12-22");

        assertEquals("Conference", event.getDescription());
        assertEquals(LocalDate.of(2024, 12, 20), event.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 22), event.getEndDate());
        assertNull(event.getStartTime());
        assertNull(event.getEndTime());
    }

    @Test
    void constructor_withStartAndEndTime() throws DateTimeParseException {
        Event event = new Event("Conference", "2024-12-20 1000", "2024-12-22 1700");

        assertEquals("Conference", event.getDescription());
        assertEquals(LocalDate.of(2024, 12, 20), event.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 22), event.getEndDate());
        assertEquals(LocalTime.of(10, 0), event.getStartTime());
        assertEquals(LocalTime.of(17, 0), event.getEndTime());
    }

    @Test
    void constructor_withLocalDateAndTime() {
        LocalDate startDate = LocalDate.of(2024, 12, 20);
        LocalDate endDate = LocalDate.of(2024, 12, 22);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        Event event = new Event("Conference", startDate, endDate, startTime, endTime);

        assertEquals("Conference", event.getDescription());
        assertEquals(startDate, event.getStartDate());
        assertEquals(endDate, event.getEndDate());
        assertEquals(startTime, event.getStartTime());
        assertEquals(endTime, event.getEndTime());
    }

    @Test
    void getTaskType_returnsEvent() throws DateTimeParseException {
        Event event = new Event("Task", "2024-12-20", "2024-12-22");
        assertEquals(TaskType.EVENT, event.getTaskType());
    }

    @Test
    void toFileString_withTimeIncluded() throws DateTimeParseException {
        Event event = new Event("Task", "2024-12-20 1000", "2024-12-22 1700");
        String fileString = event.toFileString();

        assertTrue(fileString.contains("E"));
        assertTrue(fileString.contains("2024-12-20"));
        assertTrue(fileString.contains("2024-12-22"));
        assertTrue(fileString.contains("1000"));
        assertTrue(fileString.contains("1700"));
    }

    @Test
    void toFileString_withoutTime() throws DateTimeParseException {
        Event event = new Event("Task", "2024-12-20", "2024-12-22");
        String fileString = event.toFileString();

        assertTrue(fileString.contains("E"));
        assertTrue(fileString.contains("2024-12-20"));
        assertTrue(fileString.contains("2024-12-22"));
    }

    @Test
    void toString_formattingWithTime() throws DateTimeParseException {
        Event event = new Event("Conference", "2024-12-20 1000", "2024-12-22 1700");
        String str = event.toString();

        assertTrue(str.contains("Conference"));
        assertTrue(str.contains("from:"));
        assertTrue(str.contains("to:"));
        assertTrue(str.contains("10am"));
        assertTrue(str.contains("5pm"));
    }

    @Test
    void toString_formattingWithoutTime() throws DateTimeParseException {
        Event event = new Event("Conference", "2024-12-20", "2024-12-22");
        String str = event.toString();

        assertTrue(str.contains("Conference"));
        assertTrue(str.contains("from:"));
        assertTrue(str.contains("to:"));
    }
}
