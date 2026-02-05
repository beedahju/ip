package ladis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ladis.exception.DateTimeParseException;

public class DateTimeParserTest {

    @Test
    void parseDate_standardFormat() throws DateTimeParseException {
        LocalDate date = DateTimeParser.parseDate("2024-12-25");
        assertEquals(LocalDate.of(2024, 12, 25), date);
    }

    @Test
    void parseDate_withoutLeadingZeros() throws DateTimeParseException {
        LocalDate date = DateTimeParser.parseDate("2024-1-5");
        assertEquals(LocalDate.of(2024, 1, 5), date);
    }

    @Test
    void parseDate_withLeadingZeros() throws DateTimeParseException {
        LocalDate date = DateTimeParser.parseDate("2024-01-05");
        assertEquals(LocalDate.of(2024, 1, 5), date);
    }

    @Test
    void parseDate_mixedZeros() throws DateTimeParseException {
        LocalDate date = DateTimeParser.parseDate("2024-01-5");
        assertEquals(LocalDate.of(2024, 1, 5), date);
    }

    @Test
    void parseDate_invalidFormat_slashes() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDate("25/12/2024");
        });
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    void parseDate_invalidFormat_text() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDate("Dec 25, 2024");
        });
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    void parseDate_leapYearValid() throws DateTimeParseException {
        LocalDate date = DateTimeParser.parseDate("2024-02-29");
        assertEquals(LocalDate.of(2024, 2, 29), date);
    }

    @Test
    void parseTime_standard() throws DateTimeParseException {
        LocalTime time = DateTimeParser.parseTime("1800");
        assertEquals(LocalTime.of(18, 0), time);
    }

    @Test
    void parseTime_morning() throws DateTimeParseException {
        LocalTime time = DateTimeParser.parseTime("0900");
        assertEquals(LocalTime.of(9, 0), time);
    }

    @Test
    void parseTime_midnight() throws DateTimeParseException {
        LocalTime time = DateTimeParser.parseTime("0000");
        assertEquals(LocalTime.of(0, 0), time);
    }

    @Test
    void parseTime_endOfDay() throws DateTimeParseException {
        LocalTime time = DateTimeParser.parseTime("2359");
        assertEquals(LocalTime.of(23, 59), time);
    }

    @Test
    void parseTime_withSpaces() throws DateTimeParseException {
        LocalTime time = DateTimeParser.parseTime("  1800  ");
        assertEquals(LocalTime.of(18, 0), time);
    }

    @Test
    void parseTime_invalidFormat_threeDigits() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseTime("180");
        });
        assertTrue(exception.getMessage().contains("Invalid time format"));
    }

    @Test
    void parseTime_invalidFormat_fiveDigits() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseTime("18000");
        });
        assertTrue(exception.getMessage().contains("Invalid time format"));
    }

    @Test
    void parseTime_invalidFormat_colonSeparated() {
        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseTime("18:00");
        });
        assertTrue(exception.getMessage().contains("Invalid time format"));
    }

    @Test
    void formatTime_morning() {
        LocalTime time = LocalTime.of(9, 0);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("9am", formatted);
    }

    @Test
    void formatTime_afternoon() {
        LocalTime time = LocalTime.of(14, 30);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("2:30pm", formatted);
    }

    @Test
    void formatTime_evening() {
        LocalTime time = LocalTime.of(18, 0);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("6pm", formatted);
    }

    @Test
    void formatTime_noon() {
        LocalTime time = LocalTime.of(12, 0);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("12pm", formatted);
    }

    @Test
    void formatTime_midnight() {
        LocalTime time = LocalTime.of(0, 0);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("12am", formatted);
    }

    @Test
    void formatTime_withMinutes() {
        LocalTime time = LocalTime.of(3, 45);
        String formatted = DateTimeParser.formatTime(time);
        assertEquals("3:45am", formatted);
    }

    @Test
    void formatTime_null() {
        String formatted = DateTimeParser.formatTime(null);
        assertEquals("", formatted);
    }

    @Test
    void serializeTime_standard() {
        LocalTime time = LocalTime.of(18, 0);
        String serialized = DateTimeParser.serializeTime(time);
        assertEquals("1800", serialized);
    }

    @Test
    void serializeTime_withMinutes() {
        LocalTime time = LocalTime.of(14, 30);
        String serialized = DateTimeParser.serializeTime(time);
        assertEquals("1430", serialized);
    }

    @Test
    void serializeTime_midnight() {
        LocalTime time = LocalTime.of(0, 0);
        String serialized = DateTimeParser.serializeTime(time);
        assertEquals("0000", serialized);
    }

    @Test
    void serializeTime_null() {
        String serialized = DateTimeParser.serializeTime(null);
        assertEquals("", serialized);
    }

    @Test
    void serializeDate_standard() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        String serialized = DateTimeParser.serializeDate(date);
        assertEquals("2024-12-25", serialized);
    }

    @Test
    void serializeDate_singleDigitMonth() {
        LocalDate date = LocalDate.of(2024, 1, 5);
        String serialized = DateTimeParser.serializeDate(date);
        assertEquals("2024-01-05", serialized);
    }

    @Test
    void serializeDate_newYear() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        String serialized = DateTimeParser.serializeDate(date);
        assertEquals("2024-01-01", serialized);
    }
}
