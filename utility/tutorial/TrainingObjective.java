package utility.tutorial;

public class TrainingObjective {
    public enum State { PENDING, EXACT, EXCEEDED }

    private final String itemName;
    private final int targetAmount;
    private int currentAmount = 0;

    public TrainingObjective(String itemName, int targetAmount) {
        this.itemName = itemName;
        this.targetAmount = targetAmount;
    }

    public String getItemName() { return itemName; }
    public int getTargetAmount() { return targetAmount; }

    public int getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public State getState() {
        if (currentAmount < targetAmount) return State.PENDING;
        if (currentAmount == targetAmount) return State.EXACT;
        return State.EXCEEDED;
    }
}