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
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
        }
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
            String taskType = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = switch (taskType) {
                case "T" -> new Todo(description);
                case "D" -> {
                    String deadlineDateStr = parts[3];
                    yield new Deadline(description, deadlineDateStr);
                }
                case "E" -> {
                    String startDateStr = parts[3];
                    String endDateStr = parts[4];
                    yield new Event(description, startDateStr, endDateStr);
                }
                default -> null;
            };

            if (task != null && isDone) {
                task.mark();
            }

            return task;
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            return null;
        }
    }
}
