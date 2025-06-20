import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseTracker {

    static Scanner scan = new Scanner(System.in);
    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            showMenu();
        }
    }

    public static void showMenu() {
        System.out.println("\nExpense Tracker");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Show Total Spent");
        System.out.println("4. Filter by Category");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1: addExpense(); break;
            case 2: viewExpenses(); break;
            case 3: showTotal(); break;
            case 4: filterByCategory(); break;
            case 5: 
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void addExpense() {
        System.out.print("Enter amount: ");
        double amount = scan.nextDouble();
        scan.nextLine();

        System.out.print("Enter category: ");
        String category = scan.nextLine();

        System.out.print("Enter description: ");
        String description = scan.nextLine();

        Expense newExpense = new Expense(amount, category, description);
        expenses.add(newExpense);
        System.out.println("Expense added!");
    }

    public static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("All Expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.print((i + 1) + ". ");
            expenses.get(i).printExpense();
        }
    }

    public static void showTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.printf("Total Spent: ₱%.2f\n", total);
    }

    public static void filterByCategory() {
        System.out.print("Enter category to filter: ");
        String filter = scan.nextLine().toLowerCase();

        boolean found = false;
        for (Expense e : expenses) {
            if (e.category.toLowerCase().equals(filter)) {
                e.printExpense();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found for that category.");
        }
    }
}
