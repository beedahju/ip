import java.util.Scanner;

public class Ladis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] user_inputs = new String[100]; 
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

            user_inputs[input_count] = user_input;
            input_count++;

            System.out.println("____________________________________________________________");
            System.out.println("    " + "added: " + user_input);
            System.out.println("____________________________________________________________");
        }
        sc.close();
    }
}
