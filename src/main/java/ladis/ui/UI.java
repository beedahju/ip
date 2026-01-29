package ladis.ui;

import java.util.Scanner;
import ladis.task.TaskList;

public class UI {
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        printDivider();
        System.out.println("Hello! I'm Ladis\nWhat can I do for you?");
        printDivider();
    }

    public void showGoodbye() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!\n");
        printDivider();
    }

    public void showList(TaskList tasks) {
        printDivider();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        printDivider();
    }

    public void showTaskAdded(String description, int totalTasks) {
        printDivider();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + description);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDivider();
    }

    public void showTaskMarked(String task) {
        printDivider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + task);
        printDivider();
    }

    public void showTaskUnmarked(String task) {
        printDivider();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        printDivider();
    }

    public void showTaskRemoved(String task, int totalTasks) {
        printDivider();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDivider();
    }

    public void showError(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    public void showWarning(String message) {
        System.out.println("Warning: " + message);
    }

    public String readCommand() {
        return scanner.nextLine().trim().toLowerCase();
    }

    private void printDivider() {
        System.out.println("____________________________________________________________");
    }

    public void close() {
        scanner.close();
    }
}
