public class ExitCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showGoodbye();
        return true;
    }
}
