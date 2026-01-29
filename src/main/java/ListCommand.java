public class ListCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks);
        return false;
    }
}
