package ladis.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ladis.exception.DateTimeParseException;
import ladis.task.Deadline;
import ladis.task.Event;
import ladis.task.Task;
import ladis.task.Todo;

/**
 * Handles persistence of tasks to and from disk.
 * Manages loading tasks from a file and saving tasks to a file in a text format.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are persisted.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all tasks from the storage file.
     * Creates the file and parent directories if they do not exist.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        assert tasks != null : "Task list should be initialized";
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        assert tasks != null : "Loaded task list should not be null";
        return tasks;
    }

    /**
     * Saves all tasks to the storage file.
     * Creates the file and parent directories if they do not exist.
     *
     * @param tasks The ArrayList of tasks to save.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "Task list to save should not be null";
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            tasks.stream()
                    .map(Task::toFileString)
                    .forEach(line -> {
                        try {
                            writer.write(line + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        assert file.exists() : "File should exist after saving";
    }

    /**
     * Parses a single line from the storage file into a Task object.
     * Format: taskType | isDone | description [| additionalInfo...]
     *
     * @param line The line from the file to parse.
     * @return A Task object, or null if the line cannot be parsed.
     */
    private Task parseTask(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                return null;
            }
            assert parts.length >= 3 : "Parsed task should have at least 3 parts";
            String taskType = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            assert !description.isEmpty() : "Task description should not be empty";

            Task task = switch (taskType) {
            case "T" -> new Todo(description);
            case "D" -> {
                if (parts.length < 4) {
                    yield null;
                }
                assert parts.length >= 4 : "Deadline task should have deadline information";
                String deadlineDateStr = parts[3];
                yield new Deadline(description, deadlineDateStr);
            }
            case "E" -> {
                if (parts.length < 5) {
                    yield null;
                }
                assert parts.length >= 5 : "Event task should have start and end date information";
                String startDateStr = parts[3];
                String endDateStr = parts[4];
                yield new Event(description, startDateStr, endDateStr);
            }
            default -> null;
            };

            if (task != null) {
                assert task.getDescription().equals(description) : "Task description should match parsed description";
                if (isDone) {
                    task.mark();
                    assert task.isDone() : "Task should be marked as done after mark()";
                }
            }

            return task;
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            return null;
        }
    }
}
