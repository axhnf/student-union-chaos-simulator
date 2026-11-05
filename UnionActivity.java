import java.util.Random;

/**
 * Parent class for anything the union can spend money on.
 * Each subclass decides how it is displayed and how it affects the union.
 */
abstract class UnionActivity {
    private final String name;
    private final int cost;
    private final String activityType;

    public UnionActivity(String name, int cost, String activityType) {
        this.name = name;
        this.cost = cost;
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getActivityType() {
        return activityType;
    }

    public abstract String getActivityDetails();

    public abstract void performActivity(StudentUnion union, Random random);
}
