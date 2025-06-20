public class Expense {
    double amount;
    String category;
    String description;

    // Constructor
    public Expense(double amount, String category, String description) {
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Display format
    public void printExpense() {
        System.out.println("₱" + amount + " | " + category + " | " + description);
    }
}
