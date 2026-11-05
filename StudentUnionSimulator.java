import java.util.Random;
import java.util.Scanner;

/**
 * Entry point for the game. Handles the menus, player input, and the weekly game loop.
 */
public class StudentUnionSimulator {
    // Shared input and random generator used throughout the simulation
    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        StudentUnion union;
        showIntroduction();

        // Opening menu: start, continue, or exit before the game begins
        System.out.println("1. Start new simulation");
        System.out.println("2. Continue previous simulation");
        System.out.println("3. Exit");

        int startChoice = getIntChoice("Choose an option: ", 1, 3);
        if (startChoice == 3) {
            System.out.println("Simulator closed. Goodbye!");
            input.close();
            return;
        }

        // Load a previous save if possible, otherwise start fresh
        if (startChoice == 2) {
            union = StudentUnion.loadGame();
            if (union == null) {
                System.out.println("No valid saved game was found.");
                System.out.println("A new simulation will be started instead.");
                union = new StudentUnion();
            } else {
                System.out.println("Saved game loaded successfully!");
            }
        } else {
            StudentUnion.deleteSaveFile();
            union = new StudentUnion();
            System.out.println("New simulation started.");
        }

        // Create the available event and society options
        Event[] events = createEvents();
        Society[] societies = createSocieties();
        boolean running = true;

        // Main game loop. Each action normally completes one week automatically
        while (running) {
            if (union.isGameOver()) {
                showFinalResult(union);
                StudentUnion.deleteSaveFile();
                break;
            }

            union.showWeeklyStatus();
            System.out.println();
            System.out.println("What would you like to do this week?");
            System.out.println("1. Run a student event");
            System.out.println("2. Fund a society");
            System.out.println("3. Improve union facilities");
            System.out.println("4. Save money this week");
            System.out.println("5. View full union report");
            System.out.println("6. Save and exit");
            System.out.println("7. Exit without saving again");

            int choice = getIntChoice("Choose an option: ", 1, 7);
            System.out.println();

            if (choice == 1) {
                runEventMenu(events, union);
                completeCycle(union);
            } else if (choice == 2) {
                fundSocietyMenu(societies, union);
                completeCycle(union);
            } else if (choice == 3) {
                improveFacilities(union);
                completeCycle(union);
            } else if (choice == 4) {
                saveMoneyWeek(union);
                completeCycle(union);
            } else if (choice == 5) {
                union.showFullReport(events, societies);
            } else if (choice == 6) {
                union.saveGame();
                System.out.println("Game saved. Goodbye, President!");
                running = false;
            } else {
                System.out.println("Simulator closed without saving again.");
                running = false;
            }
        }
        input.close();
    }

    public static void showIntroduction() {
        // Displays the game title, goal, win condition, and lose conditions
        System.out.println("==============================================");
        System.out.println("       STUDENT UNION CHAOS SIMULATOR");
        System.out.println("==============================================");
        System.out.println("You are the new Student Union President.");
        System.out.println("Your mission is to survive 10 weeks and keep the union successful.");
        System.out.println();
        System.out.println("WIN CONDITION:");
        System.out.println("-Finish 10 weeks with Happiness 75+, Reputation 70+, and Budget GBP 200+.");
        System.out.println();
        System.out.println("LOSE CONDITIONS:");
        System.out.println("-Budget reaches GBP 0.");
        System.out.println("-Student Happiness reaches 0.");
        System.out.println("-Complaints reach 60 or more.");
        System.out.println();
        System.out.println("The game automatically saves after every completed week.");
        System.out.println("==============================================");
        System.out.println();
    }

    public static Event[] createEvents() {
        // Creates all event choices available to the player
        return new Event[] {
                new Event("Movie Night", 80, 7, 3),
                new Event("Karaoke Night", 120, 10, 5),
                new Event("Cultural Food Festival", 200, 16, 6),
                new Event("Esports Tournament", 150, 13, 4),
                new Event("Freshers Party", 300, 22, 9)
        };
    }

    public static Society[] createSocieties() {
        // Creates all society choices available to the player
        return new Society[] {
                new Society("Gaming Society", 60, 8),
                new Society("Football Society", 70, 7),
                new Society("Drama Society", 50, 6),
                new Society("International Society", 80, 10),
                new Society("Coding Society", 65, 8)
        };
    }

    public static void runEventMenu(Event[] events, StudentUnion union) {
        // Shows the event list and runs the selected event
        System.out.println("Choose an event to run:");
        System.out.println("Events can improve happiness and reputation,");
        System.out.println("but risky events may create complaints.");
        System.out.println();

        for (int i = 0; i < events.length; i++) {
            System.out.println((i + 1) + ". " + events[i].getActivityDetails());
        }

        int eventChoice = getIntChoice("Choose event: ", 1, events.length);
        events[eventChoice - 1].performActivity(union, random); // Menu starts at 1, array at 0
    }

    public static void fundSocietyMenu(Society[] societies, StudentUnion union) {
        // Shows the society list and funds the selected society
        System.out.println("Choose a society to fund:");
        System.out.println("Funding societies improves happiness");
        System.out.println("and lowers complaints, but costs money.");
        System.out.println();

        for (int i = 0; i < societies.length; i++) {
            System.out.println((i + 1) + ". " + societies[i].getActivityDetails());
        }

        int societyChoice = getIntChoice("Choose society: ", 1, societies.length);
        societies[societyChoice - 1].performActivity(union, random);
    }

    public static void improveFacilities(StudentUnion union) {
        // Allows the player to buy one facility improvement
        System.out.println("Facility improvements:");
        System.out.println("1. Repair coffee machines - GBP 70");
        System.out.println("2. Add more seating - GBP 120");
        System.out.println("3. Upgrade student lounge - GBP 180");

        int choice = getIntChoice("Choose improvement: ", 1, 3);
        int cost = choice == 1 ? 70 : choice == 2 ? 120 : 180;
        if (!union.canAfford(cost)) {
            System.out.println("Not enough budget for this improvement.");
            return;
        }

        union.spendMoney(cost);

        if (choice == 1) {
            union.changeHappiness(4);
            union.changeComplaints(-4);
            union.changeReputation(2);
            System.out.println("The coffee machines were repaired.");
            System.out.println("Happiness +4, Complaints -4, Reputation +2, Budget -GBP 70.");
        } else if (choice == 2) {
            union.changeHappiness(7);
            union.changeComplaints(-6);
            union.changeReputation(4);
            System.out.println("More seating was added to the union building.");
            System.out.println("Happiness +7, Complaints -6, Reputation +4, Budget -GBP 120.");
        } else {
            union.changeHappiness(11);
            union.changeComplaints(-8);
            union.changeReputation(7);
            System.out.println("The student lounge was upgraded.");
            System.out.println("Happiness +11, Complaints -8, Reputation +7, Budget -GBP 180.");
        }
    }

    public static void saveMoneyWeek(StudentUnion union) {
        // Saves budget for the week, but lowers student satisfaction
        System.out.println("You decided not to spend money this week.");
        union.changeHappiness(-5);
        union.changeComplaints(4);
        union.changeReputation(-2);
        System.out.println("Happiness -5, Complaints +4, Reputation -2.");
    }

    public static void completeCycle(StudentUnion union) {
        // Applies the weekly challenge, moves to the next week, and auto-saves
        union.weeklyChallenge(random);
        union.nextWeek();
        union.autoSaveGame();
        System.out.println();
        System.out.println("End of week completed. Progress has been auto saved.");
    }

    public static int getIntChoice(String message, int min, int max) {
        // Validates menu input so the program does not crash on bad input
        while (true) {
            System.out.print(message);
            try {
                int number = Integer.parseInt(input.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static void showFinalResult(StudentUnion union) {
        // Displays the final report and explains the outcome
        System.out.println();
        System.out.println("==============================================");
        System.out.println("               FINAL RESULT");
        System.out.println("==============================================");
        union.showFullReport(null, null);
        System.out.println();

        if (union.getBudget() <= 0) {
            System.out.println("Result: Budget Disaster");
            System.out.println("You lost because the Student Union ran out of money.");
        } else if (union.getHappiness() <= 0) {
            System.out.println("Result: Student Revolt");
            System.out.println("You lost because student happiness reached 0.");
        } else if (union.getComplaints() >= 60) {
            System.out.println("Result: Complaint Crisis");
            System.out.println("You lost because complaints reached 60 or more.");
        } else if (union.getHappiness() >= 75 && union.getBudget() >= 200 && union.getReputation() >= 70) {
            System.out.println("Result: Campus Hero");
            System.out.println("You won by balancing happiness, reputation and budget successfully.");
        } else if (union.getHappiness() >= 65 && union.getBudget() >= 100) {
            System.out.println("Result: Solid Union President");
            System.out.println("You completed the semester with a good overall result.");
        } else if (union.getBudget() > 300 && union.getHappiness() < 50) {
            System.out.println("Result: Boring But Safe");
            System.out.println("You protected the budget, but students were not very happy.");
        } else {
            System.out.println("Result: Chaotic Leadership");
            System.out.println("You survived the semester, but your final performance was mixed.");
        }
        System.out.println("==============================================");
    }
}
