package ladis.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import ladis.exception.DateTimeParseException;

public class DateTimeParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses a date string in format yyyy-MM-dd (e.g., 2019-10-15 or 2019-10-2)
     * @throws DateTimeParseException if date format is invalid
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        try {
            return LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (Exception e1) {
            try {
                String[] parts = dateStr.split("-");
                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    return LocalDate.of(year, month, day);
                }
            } catch (NumberFormatException e2) {
                // Fall through
            }
        }
        throw new DateTimeParseException("Invalid date format. Use yyyy-MM-dd (e.g., 2019-10-15 or 2019-10-2)");
    }

    /**
     * Parses a time string in 24-hour format only (HHmm)
     * Examples: 1800 (6pm), 0900 (9am), 1400 (2pm)
     * 
     * @throws DateTimeParseException if time is not in 24-hour format
     */
    public static LocalTime parseTime(String timeStr) throws DateTimeParseException {
        try {
            timeStr = timeStr.trim();
            if (timeStr.length() == 4 && timeStr.matches("\\d{4}")) {
                int hours = Integer.parseInt(timeStr.substring(0, 2));
                int minutes = Integer.parseInt(timeStr.substring(2, 4));
                return LocalTime.of(hours, minutes);
            }
        } catch (NumberFormatException e) {
            // Fall through to throw exception
        }
        throw new DateTimeParseException("Invalid time format. Use 24-hour format (HHmm), e.g., 1800 for 6pm, 0900 for 9am");
    }

    /**
     * Formats a time to 12-hour format (e.g., 6pm, 3:30pm, 12am)
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return "";
        }
        
        int hour = time.getHour();
        int minute = time.getMinute();
        
        if (minute == 0) {
            if (hour < 12) {
                return hour == 0 ? "12am" : hour + "am";
            } else {
                return hour == 12 ? "12pm" : (hour - 12) + "pm";
            }
        } else {
            if (hour < 12) {
                return hour + ":" + String.format("%02d", minute) + "am";
            } else {
                int displayHour = hour == 12 ? 12 : hour - 12;
                return displayHour + ":" + String.format("%02d", minute) + "pm";
            }
        }
    }

    /**
     * Converts LocalTime to 24-hour HHmm format for storage
     */
    public static String serializeTime(LocalTime time) {
        if (time == null) {
            return "";
        }
        return String.format("%02d%02d", time.getHour(), time.getMinute());
    }

    /**
     * Converts LocalDate to yyyy-MM-dd format for storage
     */
    public static String serializeDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    /**
     * Formats LocalDate to readable format (e.g., Oct 15 2019)
     */
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}
