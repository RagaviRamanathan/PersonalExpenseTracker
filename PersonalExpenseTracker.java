import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

// Expense categories
enum ExpenseCategory {
    FOOD,
    TRANSPORT,
    SHOPPING,
    BILLS,
    ENTERTAINMENT,
    HEALTH,
    EDUCATION,
    OTHER
}

// Expense class
class Expense {

    private int id;
    private String description;
    private double amount;
    private ExpenseCategory category;
    private LocalDate date;

    public Expense(int id, String description, double amount,
                   ExpenseCategory category, LocalDate date) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Description: " + description +
               " | Amount: ₹" + amount +
               " | Category: " + category +
               " | Date: " + date;
    }
}

// Main class
public class PersonalExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== PERSONAL EXPENSE TRACKER =====");
            System.out.println("1. Add Expense");
            System.out.println("2. Delete Expense");
            System.out.println("3. Display All Expenses");
            System.out.println("4. Calculate Total Expense");
            System.out.println("5. Find Highest Expense");
            System.out.println("6. Category-wise Expenses");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    deleteExpense();
                    break;

                case 3:
                    displayExpenses();
                    break;

                case 4:
                    calculateTotal();
                    break;

                case 5:
                    findHighestExpense();
                    break;

                case 6:
                    categoryWiseExpenses();
                    break;

                case 7:
                    System.out.println("Thank you for using Personal Expense Tracker!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Add expense
    static void addExpense() {

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("\nChoose Category:");

        ExpenseCategory[] categories = ExpenseCategory.values();

        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }

        System.out.print("Enter category number: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        if (categoryChoice < 1 || categoryChoice > categories.length) {
            System.out.println("Invalid category!");
            return;
        }

        ExpenseCategory category =
                ExpenseCategory.values()[categoryChoice - 1];

        // Get today's date
        LocalDate date = LocalDate.now();

        int id = expenses.size() + 1;

        Expense expense = new Expense(
                id,
                description,
                amount,
                category,
                date
        );

        expenses.add(expense);

        System.out.println("Expense added successfully!");
    }

    // Delete expense
    static void deleteExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }

        displayExpenses();

        System.out.print("Enter expense ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean removed =
                expenses.removeIf(expense -> expense.getId() == id);

        if (removed) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense ID not found.");
        }
    }

    // Display all expenses
    static void displayExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n===== ALL EXPENSES =====");

        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    // Calculate total expense using Streams
    static void calculateTotal() {

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        System.out.println("Total Expense: ₹" + total);
    }

    // Find highest expense using Streams
    static void findHighestExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        Expense highest = expenses.stream()
                .max((e1, e2) ->
                        Double.compare(
                                e1.getAmount(),
                                e2.getAmount()
                        )
                )
                .orElse(null);

        System.out.println("\n===== HIGHEST EXPENSE =====");
        System.out.println(highest);
    }

    // Calculate category-wise expenses using HashMap
    static void categoryWiseExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        HashMap<ExpenseCategory, Double> categoryTotals =
                new HashMap<>();

        for (Expense expense : expenses) {

            categoryTotals.put(
                    expense.getCategory(),
                    categoryTotals.getOrDefault(
                            expense.getCategory(),
                            0.0
                    ) + expense.getAmount()
            );
        }

        System.out.println("\n===== CATEGORY-WISE EXPENSES =====");

        for (Map.Entry<ExpenseCategory, Double> entry
                : categoryTotals.entrySet()) {

            System.out.println(
                    entry.getKey() + " : ₹" + entry.getValue()
            );
        }
    }
}

