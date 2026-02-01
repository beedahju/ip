package ladis;

import java.io.IOException;

import ladis.command.Command;
import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.util.Parser;

/**
 * Main entry point and controller for the Ladis task management application.
 * Manages the main application loop, coordinates between UI, Parser, Storage, and TaskList.
 */
public class Ladis {
    private final Storage storage;
    private TaskList tasks;
    private final UI ui;
    private final Parser parser;

    /**
     * Constructs a Ladis application instance with the specified data file path.
     *
     * @param filePath The path to the file where task data is stored.
     */
    public Ladis(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new UI();
        this.parser = new Parser();
        this.tasks = new TaskList();
    }

    /**
     * Runs the main application loop.
     * Loads tasks from storage, displays welcome message, and continuously processes user commands
     * until an exit command is received.
     */
    public void run() {
        ui.showWelcome();

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Oops! Something went wrong when loading from disk!");
        }

        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                Command command = parser.parse(userInput);
                isExit = command.execute(tasks, ui, storage);
            } catch (LadisException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    /**
     * Entry point for the Ladis application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Ladis("./data/ladis.txt").run();
    }
}
