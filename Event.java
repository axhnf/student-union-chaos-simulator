import java.util.Random;

/**
 * A student event that costs money and affects union stats.
 * Higher-risk events are more likely to go wrong and create complaints.
 */
class Event extends UnionActivity {
    private final int happinessBoost;
    private final int riskLevel;
    private int timesRun;

    public Event(String name, int cost, int happinessBoost, int riskLevel) {
        super(name, cost, "Event");
        this.happinessBoost = happinessBoost;
        this.riskLevel = riskLevel;
    }

    @Override
    public String getActivityDetails() {
        return getName() + " | Cost: GBP " + getCost() + " | Happiness: +" + happinessBoost
                + " | Risk: " + riskLevel + "/10";
    }

    @Override
    public void performActivity(StudentUnion union, Random random) {
        System.out.println("You chose to run: " + getName());

        // If the event is unaffordable, students react negatively
        if (!union.canAfford(getCost())) {
            System.out.println("Not enough budget to run this event.");
            union.changeHappiness(-4);
            union.changeComplaints(5);
            System.out.println("Happiness -4, Complaints +5.");
            return;
        }

        // Successful events increase attendance, happiness, and reputation
        union.spendMoney(getCost());
        int attendance = random.nextInt(71) + 30 + happinessBoost;
        int eventOutcome = random.nextInt(10) + 1;
        timesRun++;
        union.addAttendance(attendance);
        union.changeHappiness(happinessBoost);
        union.changeReputation(happinessBoost / 2);

        System.out.println("Event completed!");
        System.out.println("Attendance: " + attendance + " students");
        System.out.println("Budget -GBP " + getCost() + ", Happiness +" + happinessBoost
                + ", Reputation +" + (happinessBoost / 2) + ".");

        // Riskier events have a higher chance of complaints
        if (eventOutcome <= riskLevel) {
            int complaintIncrease = random.nextInt(5) + 2;
            union.changeComplaints(complaintIncrease);
            union.changeReputation(-2);
            System.out.println("Something went wrong during the event.");
            System.out.println("Complaints +" + complaintIncrease + ", Reputation -2.");
        } else {
            union.changeComplaints(-2);
            System.out.println("The event went smoothly. Complaints -2.");
        }
    }

    public int getTimesRun() {
        return timesRun;
    }
}
