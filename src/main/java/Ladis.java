import java.util.ArrayList;
import java.util.Scanner;

public class Ladis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> user_inputs = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Ladis\nWhat can I do for you?");
        System.out.println("____________________________________________________________");
        
        while (true) {
            try{
                String user_input = sc.nextLine().trim().toLowerCase();

                if (user_input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!\n");
                    System.out.println("____________________________________________________________");
                    break;
                }

                if (user_input.equals("list")) {
                    System.out.println("____________________________________________________________");
                    for (int i = 0; i < user_inputs.size(); i++) {
                        System.out.println((i + 1) + ". " + user_inputs.get(i));
                    }
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (user_input.startsWith("mark")) {
                    try{
                        int index = Integer.parseInt(user_input.substring(5)) - 1;
                        if (index < 0 || index >= user_inputs.size()) {
                            throw new LadisExeception("Very funny. Now give me a valid task number.");
                        }
                        user_inputs.get(index).mark();
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("    " + user_inputs.get(index));
                        continue;
                    } catch (NumberFormatException e) {
                        throw new LadisExeception("Very funny. Now give me a valid task number.");
                    }
                }

                if (user_input.startsWith("unmark")) {
                    try{
                        int index = Integer.parseInt(user_input.substring(7)) - 1;
                        if (index < 0 || index >= user_inputs.size()) {
                            throw new LadisExeception("Very funny. Now give me a valid task number.");
                        }
                        user_inputs.get(index).unmark();
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("    " + user_inputs.get(index));
                        continue;
                    } catch (NumberFormatException e) {
                        throw new LadisExeception("Very funny. Now give me a valid task number.");
                    }
                }

                if (user_input.startsWith("todo")) {
                    if (user_input.length() == 4) {
                        throw new LadisExeception("Please don't be funny and give me a proper description.");
                    }
                    String description = user_input.substring(5);
                    user_inputs.add(new Todo(description));

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [T][ ] " + description);
                    System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (user_input.startsWith("deadline")) {
                    if (!user_input.contains(" /by ")) {
                        throw new LadisExeception("So, when is the deadline?");
                    }

                    String[] parts = user_input.substring(9).split(" /by ");
                    user_inputs.add(new Deadline(parts[0], parts[1]));
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [D][ ] " + parts[0] + " (by: " + parts[1] + ")");
                    System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (user_input.startsWith("event")) {
                    if (!user_input.contains(" /from ") || !user_input.contains(" /to ")) {
                        throw new LadisExeception("Interesting event. When does it start and end?");
                    }
                    String[] parts = user_input.substring(6).split(" /from | /to ");
                    user_inputs.add(new Event(parts[0], parts[1], parts[2]));
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [E][ ] " + parts[0] +
                            " (from: " + parts[1] + " to: " + parts[2] + ")");
                    System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

                if (user_input.startsWith("delete")) {
                    try {
                        int index = Integer.parseInt(user_input.substring(7)) - 1;

                        if (index < 0 || index >= user_inputs.size()) {
                            throw new LadisExeception("Very funny. Now give me a valid task number.");
                        }

                        Task removed = user_inputs.remove(index);
                        System.out.println("____________________________________________________________");
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removed);
                        System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        continue;

                    } catch (NumberFormatException e) {
                        throw new LadisExeception("Please provide a valid task number.");
                    }
            }       
                throw new LadisExeception("I'm sorry, but I don't know what that means :-(");
            }
                catch(LadisExeception e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(e.getMessage());
                    System.out.println("____________________________________________________________");
                }
        }
        sc.close();
    }
}