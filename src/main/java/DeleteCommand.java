import java.io.IOException;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisExeception {
        if (index < 0 || index >= tasks.size()) {
            throw new LadisExeception("Very funny. Now give me a valid task number.");
        }
        Task removed = tasks.removeTask(index);
        ui.showTaskRemoved(removed.toString(), tasks.size());
        saveToStorage(tasks, ui, storage);
        return false;
    }

    private void saveToStorage(TaskList tasks, UI ui, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showWarning("Oops! Something went wrong while saving to disk.");
        }
    }
}
