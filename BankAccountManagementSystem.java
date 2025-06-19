import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.File;

public class BankAccountManagementSystem {

    // scanner object to read input from the console
    static final Scanner scan = new Scanner(System.in);

    static double balance = 1000.00;
    
    public static void main(String[] args) {

        // call the login right away
        login();

    }

    public static String timeStamp(){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "Timestamp: " + currentTime.format(formatter);
    }

    // login method
    public static void login(){
        // variables1
        String CORRECT_PIN = "1234";
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter your PIN: ");
            String inputPin = scan.nextLine();

            if (inputPin.equals(CORRECT_PIN)) {
                System.out.println("Login successful!");
                displayMenu();
                return;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. You have " + (3 - attempts) + " attempts left.");
            }
        }
    }

    public static void displayMenu() {
        while (true) {
            System.out.println("\nWelcome to the Bank Account Management System");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Please select an option: ");
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    System.out.println("Exiting the system. Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static void checkBalance() {
        // Placeholder for balance checking logic
        System.out.println("Your current balance is. $" + balance);
        System.out.println(timeStamp());
        displayMenu();
    }

    public static void depositMoney() {
        System.out.print("Enter amount to deposit: ");
        double amount = scan.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient funds. You cannot deposit more than your current balance.");
        } else {
            amount -= balance;
            System.out.println("You have successfully deposited $" + amount + ".");
        }

        scan.nextLine();
        System.out.println(timeStamp());
        displayMenu();
    }

    public static void withdrawMoney() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scan.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("You have withdrawn $" + amount);
        }

        scan.nextLine();
        System.out.println(timeStamp());
        displayMenu();
    }
}
