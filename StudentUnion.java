import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Stores the main simulation state: stats, weekly challenges, and save/load logic.
 */
class StudentUnion {
    private static final String SAVE_FILE = "student_union_save.txt";

    private int week = 1;
    private int budget = 1000;
    private int happiness = 60;
    private int reputation = 50;
    private int complaints = 10;
    private int totalAttendance;
    private int totalSpent;
    private final int maxWeeks = 10;

    public void showWeeklyStatus() {
        // Displays current weekly statistics before the player chooses an action
        System.out.println();
        System.out.println("==============================================");
        System.out.println("                WEEK " + week);
        System.out.println("==============================================");
        System.out.println("Budget: GBP " + budget);
        System.out.println("Student Happiness: " + happiness + "/100");
        System.out.println("Union Reputation: " + reputation + "/100");
        System.out.println("Complaints: " + complaints);
        System.out.println("Total Event Attendance: " + totalAttendance);
        System.out.println("Weeks Remaining: " + (maxWeeks - week + 1));
        System.out.println("----------------------------------------------");
        showRiskWarnings();
        System.out.println("==============================================");
    }

    public void showRiskWarnings() {
        // Warns the player when important stats are close to failure
        boolean warningShown = false;

        if (budget <= 200) {
            System.out.println("Warning: Budget is getting low.");
            warningShown = true;
        }
        if (happiness <= 30) {
            System.out.println("Warning: Student happiness is low.");
            warningShown = true;
        }
        if (complaints >= 45) {
            System.out.println("Warning: Complaints are getting high.");
            warningShown = true;
        }
        if (reputation < 40) {
            System.out.println("Warning: Reputation is weak.");
            warningShown = true;
        }
        if (!warningShown) {
            System.out.println("Status: Stable");
        }
    }

    public void showFullReport(Event[] events, Society[] societies) {
        // Displays all current union statistics and optional activity details
        System.out.println();
        System.out.println("--------------- UNION REPORT ----------------");
        System.out.println("Current Week: " + Math.min(week, maxWeeks));
        System.out.println("Budget: GBP " + budget);
        System.out.println("Student Happiness: " + happiness + "/100");
        System.out.println("Union Reputation: " + reputation + "/100");
        System.out.println("Complaints: " + complaints);
        System.out.println("Total Attendance: " + totalAttendance);
        System.out.println("Total Money Spent: GBP " + totalSpent);

        if (events != null) {
            System.out.println();
            System.out.println("Available Events:");
            for (Event event : events) {
                System.out.println("- " + event.getActivityDetails());
            }
        }

        if (societies != null) {
            System.out.println();
            System.out.println("Available Societies:");
            for (Society society : societies) {
                System.out.println("- " + society.getActivityDetails());
            }
        }
        System.out.println("---------------------------------------------");
    }

    public void weeklyChallenge(Random random) {
        // Applies a different story challenge depending on the current week
        System.out.println();
        System.out.println("Weekly Student Union Challenge:");

        if (week == 1) {
            System.out.println("Freshers Week Madness!");
            System.out.println("New students expect a strong first impression.");
            changeHappiness(5);
            changeReputation(4);
            changeComplaints(2);
            System.out.println("Happiness +5, Reputation +4, Complaints +2.");
        } else if (week == 2) {
            System.out.println("Society Sign-Up Rush!");
            System.out.println("Societies are competing for student attention.");
            changeHappiness(3);
            changeComplaints(3);
            System.out.println("Happiness +3, Complaints +3.");
        } else if (week == 3) {
            System.out.println("Coffee Machine Disaster!");
            System.out.println("The coffee machine broke during the morning rush.");
            changeHappiness(-4);
            changeComplaints(7);
            System.out.println("Happiness -4, Complaints +7.");
        } else if (week == 4) {
            System.out.println("Meme War on Campus!");
            if (random.nextInt(2) == 0) {
                System.out.println("The memes made the union more popular.");
                changeReputation(8);
                changeHappiness(4);
                System.out.println("Reputation +8, Happiness +4.");
            } else {
                System.out.println("The memes embarrassed the union.");
                changeReputation(-6);
                changeComplaints(4);
                System.out.println("Reputation -6, Complaints +4.");
            }
        } else if (week == 5) {
            System.out.println("Budget Inspection Week!");
            if (budget >= 500) {
                System.out.println("The finance team is impressed.");
                changeReputation(6);
                System.out.println("Reputation +6.");
            } else {
                System.out.println("The finance team is worried about spending.");
                changeReputation(-5);
                changeComplaints(3);
                System.out.println("Reputation -5, Complaints +3.");
            }
        } else if (week == 6) {
            System.out.println("Student Protest!");
            if (happiness >= 65) {
                System.out.println("The protest stays small.");
                changeComplaints(2);
                System.out.println("Complaints +2.");
            } else {
                System.out.println("The protest damages the union's image.");
                changeComplaints(10);
                changeReputation(-7);
                System.out.println("Complaints +10, Reputation -7.");
            }
        } else if (week == 7) {
            System.out.println("Surprise Local Sponsorship!");
            if (reputation >= 60) {
                System.out.println("A sponsor gives the union GBP 150.");
                budget += 150;
                changeReputation(3);
                System.out.println("Budget +GBP 150, Reputation +3.");
            } else {
                System.out.println("The sponsor refuses due to weak reputation.");
                changeReputation(-2);
                System.out.println("Reputation -2.");
            }
        } else if (week == 8) {
            System.out.println("Exam Stress Week!");
            if (complaints <= 30) {
                System.out.println("The union supports students successfully.");
                changeHappiness(6);
                changeReputation(5);
                System.out.println("Happiness +6, Reputation +5.");
            } else {
                System.out.println("Students feel unsupported.");
                changeHappiness(-6);
                changeComplaints(5);
                System.out.println("Happiness -6, Complaints +5.");
            }
        } else if (week == 9) {
            System.out.println("Union Election Rumours!");
            if (happiness >= 70 && reputation >= 65) {
                System.out.println("Students support your leadership.");
                changeReputation(8);
                changeHappiness(4);
                System.out.println("Reputation +8, Happiness +4.");
            } else {
                System.out.println("Students question your leadership.");
                changeReputation(-6);
                changeComplaints(6);
                System.out.println("Reputation -6, Complaints +6.");
            }
        } else if (week == 10) {
            System.out.println("Final Big Union Week!");
            if (happiness >= 75 && reputation >= 70 && budget >= 200) {
                System.out.println("The semester ends successfully.");
                changeHappiness(5);
                changeReputation(5);
                System.out.println("Happiness +5, Reputation +5.");
            } else {
                System.out.println("Students notice weaknesses in the union.");
                changeComplaints(4);
                System.out.println("Complaints +4.");
            }
        }
    }

    public boolean canAfford(int amount) {
        // Checks whether the union has enough money
        return budget >= amount;
    }

    public void spendMoney(int amount) {
        // Removes money and records total spending
        budget -= amount;
        totalSpent += amount;
    }

    public void changeHappiness(int amount) {
        // Keeps happiness between 0 and 100
        happiness = Math.max(0, Math.min(100, happiness + amount));
    }

    public void changeReputation(int amount) {
        // Keeps reputation between 0 and 100
        reputation = Math.max(0, Math.min(100, reputation + amount));
    }

    public void changeComplaints(int amount) {
        // Prevents complaints from becoming negative
        complaints = Math.max(0, complaints + amount);
    }

    public void addAttendance(int amount) {
        totalAttendance += amount;
    }

    public void nextWeek() {
        week++;
    }

    public boolean isGameOver() {
        // Checks all game-ending conditions
        return week > maxWeeks || budget <= 0 || happiness <= 0 || complaints >= 60;
    }

    public void saveGame() {
        // Saves progress when the player manually chooses save and exit
        System.out.println(writeSaveFile() ? "Progress saved to " + SAVE_FILE : "Error: Could not save the game.");
    }

    public void autoSaveGame() {
        // Automatically saves after each completed week
        if (!writeSaveFile()) {
            System.out.println("Warning: The game could not auto-save.");
        }
    }

    private boolean writeSaveFile() {
        // Writes the current game data into a readable text save file
        try (PrintWriter writer = new PrintWriter(SAVE_FILE)) {
            writer.println("========================================");
            writer.println("        STUDENT UNION SAVE DATA");
            writer.println("========================================");
            writer.printf("%-20s : %d%n", "Week", week); // %-20s left-aligns the label in 20 spaces
            writer.printf("%-20s : GBP %d%n", "Budget", budget);
            writer.printf("%-20s : %d%n", "Student Happiness", happiness);
            writer.printf("%-20s : %d%n", "Union Reputation", reputation);
            writer.printf("%-20s : %d%n", "Complaints", complaints);
            writer.printf("%-20s : %d%n", "Total Attendance", totalAttendance);
            writer.printf("%-20s : GBP %d%n", "Total Money Spent", totalSpent);
            writer.println("======================================");
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static StudentUnion loadGame() {
        // Loads saved values from the text file if it exists and is valid
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }

        try (Scanner reader = new Scanner(file)) {
            StudentUnion loadedUnion = new StudentUnion();
            reader.nextLine();
            reader.nextLine();
            reader.nextLine();

            loadedUnion.week = extractNumber(reader.nextLine());
            loadedUnion.budget = extractNumber(reader.nextLine());
            loadedUnion.happiness = extractNumber(reader.nextLine());
            loadedUnion.reputation = extractNumber(reader.nextLine());
            loadedUnion.complaints = extractNumber(reader.nextLine());
            loadedUnion.totalAttendance = extractNumber(reader.nextLine());
            loadedUnion.totalSpent = extractNumber(reader.nextLine());
            return loadedUnion;
        } catch (Exception e) {
            return null;
        }
    }

    private static int extractNumber(String line) {
        // Extracts the numeric value after the colon in a save file row
        return Integer.parseInt(line.substring(line.indexOf(':') + 1).replace("GBP", "").trim());
    }

    public static void deleteSaveFile() {
        // Deletes old save data when starting over or finishing the game
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    public int getBudget() {
        return budget;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getReputation() {
        return reputation;
    }

    public int getComplaints() {
        return complaints;
    }
}
