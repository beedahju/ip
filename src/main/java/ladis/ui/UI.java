package ladis.ui;

import java.util.Scanner;

import ladis.task.TaskList;

/**
 * User Interface class that handles all user interactions.
 * Displays messages to the user and reads user input from the console.
 */
public class UI {
    private final Scanner scanner;

    /**
     * Constructs a UI instance with a Scanner for reading user input.
     */
    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        printDivider();
        System.out.println("Hello! I'm Ladis\nWhat can I do for you?");
        printDivider();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!\n");
        printDivider();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks The task list to display.
     */
    public void showList(TaskList tasks) {
        printDivider();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        printDivider();
    }

    /**
     * Displays a message confirming a task has been added.
     *
     * @param description The description of the added task.
     * @param totalTasks The total number of tasks after addition.
     */
    public void showTaskAdded(String description, int totalTasks) {
        printDivider();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + description);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDivider();
    }

    /**
     * Displays a message confirming a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(String task) {
        printDivider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + task);
        printDivider();
    }

    /**
     * Displays a message confirming a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(String task) {
        printDivider();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        printDivider();
    }

    /**
     * Displays a message confirming a task has been removed.
     *
     * @param task The task that was removed.
     * @param totalTasks The total number of tasks after removal.
     */
    public void showTaskRemoved(String task, int totalTasks) {
        printDivider();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDivider();
    }

    /**
     * Displays a message confirming a task has been archived.
     *
     * @param task The task that was archived.
     * @param totalTasks The total number of tasks after archiving.
     */
    public void showTaskArchived(String task, int totalTasks) {
        printDivider();
        System.out.println("Archived! I've moved this task to the archive:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printDivider();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * Displays a warning message.
     *
     * @param message The warning message to display.
     */
    public void showWarning(String message) {
        System.out.println("Warning: " + message);
    }

    /**
     * Displays a general message with dividers.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * Reads a command line from user input.
     *
     * @return The user input as a string, trimmed and converted to lowercase.
     */
    public String readCommand() {
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Prints a divider line for formatting console output.
     */
    private void printDivider() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Closes the scanner to release resources.
     */
    public void close() {
        scanner.close();
    }
}
