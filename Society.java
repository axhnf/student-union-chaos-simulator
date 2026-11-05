import java.util.Random;

/**
 * A student society that can receive funding.
 * Funding raises its activity level; failing to fund it lowers that level.
 */
class Society extends UnionActivity {
    private final int happinessBoost;
    private int activityLevel = 50;
    private int fundingReceived;

    public Society(String name, int cost, int happinessBoost) {
        super(name, cost, "Society");
        this.happinessBoost = happinessBoost;
    }

    @Override
    public String getActivityDetails() {
        return getName() + " | Funding: GBP " + getCost() + " | Happiness: +" + happinessBoost
                + " | Activity: " + activityLevel + "/100";
    }

    @Override
    public void performActivity(StudentUnion union, Random random) {
        System.out.println("You chose to fund: " + getName());

        // If the union cannot afford funding, society activity drops
        if (!union.canAfford(getCost())) {
            System.out.println("Not enough budget to fund this society.");
            activityLevel = Math.max(0, activityLevel - 5);
            union.changeHappiness(-3);
            union.changeComplaints(3);
            System.out.println("Activity -5, Happiness -3, Complaints +3.");
            return;
        }

        // Funding improves society activity and union reputation
        union.spendMoney(getCost());
        int activityIncrease = random.nextInt(11) + 5;
        activityLevel = Math.min(100, activityLevel + activityIncrease);
        fundingReceived += getCost();

        union.changeHappiness(happinessBoost);
        union.changeComplaints(-3);
        union.changeReputation(3);

        System.out.println(getName() + " received GBP " + getCost() + " funding.");
        System.out.println("Activity +" + activityIncrease + ", Happiness +" + happinessBoost
                + ", Complaints -3, Reputation +3.");
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getFundingReceived() {
        return fundingReceived;
    }
}
