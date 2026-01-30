package ladis.task;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void constructor_withTimes() throws DateTimeParseException {
        Event event = new Event("Meeting", "2024-12-25 0900", "2024-12-25 1700");
        
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDate.of(2024, 12, 25), event.getStartDate());
        assertEquals(LocalDate.of(2024, 12, 25), event.getEndDate());
        assertEquals(LocalTime.of(9, 0), event.getStartTime());
        assertEquals(LocalTime.of(17, 0), event.getEndTime());
    }

    @Test
    void constructor_partialTimes() throws DateTimeParseException {
        Event event = new Event("Event", "2024-12-20 0900", "2024-12-22");
        
        assertEquals(LocalTime.of(9, 0), event.getStartTime());
        assertNull(event.getEndTime());
    }

    @Test
    void constructor_withLocalDateAndTime() {
        LocalDate start = LocalDate.of(2024, 12, 20);
        LocalDate end = LocalDate.of(2024, 12, 22);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        Event event = new Event("Vacation", start, end, startTime, endTime);
        
        assertEquals("Vacation", event.getDescription());
        assertEquals(start, event.getStartDate());
        assertEquals(end, event.getEndDate());
        assertEquals(startTime, event.getStartTime());
        assertEquals(endTime, event.getEndTime());
    }

    @Test
    void getTaskType_returnsEvent() throws DateTimeParseException {
        Event event = new Event("Event", "2024-12-20", "2024-12-22");
        assertEquals(TaskType.EVENT, event.getTaskType());
    }

    @Test
    void toString_withTimes() throws DateTimeParseException {
        Event event = new Event("Conference", "2024-12-20 0900", "2024-12-22 1700");
        String str = event.toString();
        
        assertTrue(str.contains("Conference"));
        assertTrue(str.contains("from:"));
        assertTrue(str.contains("to:"));
    }

    @Test
    void toString_withoutTimes() throws DateTimeParseException {
        Event event = new Event("Holiday", "2024-12-20", "2024-12-25");
        String str = event.toString();
        
        assertTrue(str.contains("Holiday"));
        assertTrue(str.contains("from:"));
        assertTrue(str.contains("to:"));
    }

    @Test
    void sameStartAndEndDate() throws DateTimeParseException {
        Event event = new Event("One day event", "2024-12-25", "2024-12-25");
        
        assertEquals(event.getStartDate(), event.getEndDate());
    }

    @Test
    void multiDayEvent() throws DateTimeParseException {
        Event event = new Event("Week long event", "2024-12-20", "2024-12-27");
        
        assertTrue(event.getStartDate().isBefore(event.getEndDate()));
    }

    @Test
    void toFileString_withTimes() throws DateTimeParseException {
        Event event = new Event("Meeting", "2024-12-25 0900", "2024-12-25 1700");
        String fileString = event.toFileString();
        
        assertTrue(fileString.contains("E"));
        assertTrue(fileString.contains("2024-12-25"));
        assertTrue(fileString.contains("0900"));
        assertTrue(fileString.contains("1700"));
    }
}