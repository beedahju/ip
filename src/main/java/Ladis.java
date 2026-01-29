import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ladis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> user_inputs = new ArrayList<>();
        Storage storage = new Storage("./data/ladis.txt");

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Ladis\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        try {
            user_inputs = storage.load();
        } catch (IOException e) {
            System.out.println("Oops! Something went wrong when loading from disk!");
        }
        
        while (true) {
            try{
                String user_input = sc.nextLine().trim().toLowerCase();
                String command = user_input.split(" ")[0];

                switch (command) {
                    case "bye" -> {
                        System.out.println("____________________________________________________________");
                        System.out.println("Bye. Hope to see you again soon!\n");
                        System.out.println("____________________________________________________________");
                        return;
                    }

                    case "list" -> {
                        System.out.println("____________________________________________________________");
                        for (int i = 0; i < user_inputs.size(); i++) {
                            System.out.println((i + 1) + ". " + user_inputs.get(i));
                        }
                        System.out.println("____________________________________________________________");
                    }

                    case "mark" -> {
                        try{
                            int index = Integer.parseInt(user_input.substring(5)) - 1;
                            if (index < 0 || index >= user_inputs.size()) {
                                throw new LadisExeception("Very funny. Now give me a valid task number.");
                            }
                            user_inputs.get(index).mark();
                            System.out.println("____________________________________________________________");
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("    " + user_inputs.get(index));
                            
                            try {
                                storage.save(user_inputs);
                            } catch (IOException e) {
                                System.out.println("Warning: Could not save task to disk.");
                            }
                        } catch (NumberFormatException e) {
                            throw new LadisExeception("Very funny. Now give me a valid task number.");
                        }
                    }

                    case "unmark" -> {
                        try{
                            int index = Integer.parseInt(user_input.substring(7)) - 1;
                            if (index < 0 || index >= user_inputs.size()) {
                                throw new LadisExeception("Very funny. Now give me a valid task number.");
                            }
                            user_inputs.get(index).unmark();
                            System.out.println("____________________________________________________________");
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("    " + user_inputs.get(index));
                            
                            try {
                                storage.save(user_inputs);
                            } catch (IOException e) {
                                System.out.println("Oops! Something went wrong when saving to disk!");
                            }
                        } catch (NumberFormatException e) {
                            throw new LadisExeception("Very funny. Now give me a valid task number.");
                        }
                    }

                    case "todo" -> {
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
                        
                        try {
                            storage.save(user_inputs);
                        } catch (IOException e) {
                            System.out.println("Oops! Something went wrong when saving to disk!");
                        }
                    }

                    case "deadline" -> {
                        if (!user_input.contains(" /by ")) {
                            throw new LadisExeception("So, when is the deadline?");
                        }

                        String[] parts = user_input.substring(9).split(" /by ");
                        try {
                            user_inputs.add(new Deadline(parts[0], parts[1]));
                            System.out.println("____________________________________________________________");
                            System.out.println("Got it. I've added this task:");
                            System.out.println("  [D][ ] " + parts[0] + " (by: " + parts[1] + ")");
                            System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                            System.out.println("____________________________________________________________");
                            
                            try {
                                storage.save(user_inputs);
                            } catch (IOException e) {
                                System.out.println("Oops! Something went wrong when saving to disk!");
                            }
                        } catch (DateTimeParseException e) {
                            throw new LadisExeception(e.getMessage());
                        }
                    }

                    case "event" -> {
                        if (!user_input.contains(" /from ") || !user_input.contains(" /to ")) {
                            throw new LadisExeception("Interesting event. When does it start and end?");
                        }
                        String[] eventParts = user_input.substring(6).split(" /from | /to ");
                        try {
                            user_inputs.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
                            System.out.println("____________________________________________________________");
                            System.out.println("Got it. I've added this task:");
                            System.out.println("  [E][ ] " + eventParts[0] +
                                    " (from: " + eventParts[1] + " to: " + eventParts[2] + ")");
                            System.out.println("Now you have " + user_inputs.size() + " tasks in the list.");
                            System.out.println("____________________________________________________________");
                            
                            try {
                                storage.save(user_inputs);
                            } catch (IOException e) {
                                System.out.println("Oops! Something went wrong when saving to disk!");
                            }
                        } catch (DateTimeParseException e) {
                            throw new LadisExeception(e.getMessage());
                        }
                    }

                    case "delete" -> {
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
                            
                            try {
                                storage.save(user_inputs);
                            } catch (IOException e) {
                                System.out.println("Oops! Something went wrong when saving to disk!");
                            }
                        } catch (NumberFormatException e) {
                            throw new LadisExeception("Please provide a valid task number.");
                        }
                    }

                    default -> throw new LadisExeception("I'm sorry, but I don't know what that means :-(");
                }
            }
                catch(LadisExeception e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(e.getMessage());
                    System.out.println("____________________________________________________________");
                }
        }
    }
}