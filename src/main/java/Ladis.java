import java.util.Scanner;

public class Ladis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] user_inputs = new Task[100]; 
        int input_count = 0;

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

            if (user_input.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < input_count; i++) {
                    System.out.println((i + 1) + ". " + user_inputs[i]);
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (user_input.startsWith("mark ")) {
                int index = Integer.parseInt(user_input.substring(5)) - 1;
                user_inputs[index].mark();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("    " + user_inputs[index]);
                continue;
            }

            if (user_input.startsWith("unmark ")) {
                int index = Integer.parseInt(user_input.substring(7)) - 1;
                user_inputs[index].unmark();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("    " + user_inputs[index]);
                continue;
            }

            user_inputs[input_count] = new Task(user_input);
            input_count++;

            System.out.println("____________________________________________________________");
            System.out.println("    " + "added: " + user_input);
            System.out.println("____________________________________________________________");
        }
        
        sc.close();
    }
}
