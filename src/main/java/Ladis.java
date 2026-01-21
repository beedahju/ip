import java.util.Scanner;

public class Ladis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Ladis\nWhat can I do for you?");
        System.out.println("____________________________________________________________");
        
        while (true) {
            String user_input = sc.nextLine().trim().toLowerCase();

            if (user_input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!\n");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println("    " + user_input);
            System.out.println("____________________________________________________________");
        }
        sc.close();
    }
}
