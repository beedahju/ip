import java.io.IOException;

public class Ladis {
    private final Storage storage;
    private TaskList tasks;
    private final UI ui;
    private final Parser parser;

    public Ladis(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new UI();
        this.parser = new Parser();
        this.tasks = new TaskList();
    }

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
            } catch (LadisExeception e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    public static void main(String[] args) {
        new Ladis("./data/ladis.txt").run();
    }
}