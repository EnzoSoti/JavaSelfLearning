import java.util.ArrayList;

public class StudentGradeManagementSystem {

    // global Scanner object for user input
    static java.util.Scanner scan = new java.util.Scanner(System.in);

    // global ArrayList to store grades
    static ArrayList <Double> grades = new ArrayList<>();

    public static void main(String[] args) {

        
        while (true) {
            menuSystem();
        }
        
    }


    public static void menuSystem() {
        System.out.println("\nWelcome to the Student Grade Management System");
        System.out.println("1. Add grade");
        System.out.println("2. Show all grades");
        System.out.println("3. Show average");
        System.out.println("4. Show Highest and Lowest");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        int choice = scan.nextInt();
        scan.nextLine(); 

        switch (choice) {
            case 1:
                addGrade();
                break;
            case 2:
                showAllGrades();
                break;
            case 3:
                showAverage();
                break;
            case 4:
                showHighestLowest();
                break;
            case 5:
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                menuSystem();
                break;    
        }

    }

    public static void addGrade () {
        while (true) {
            System.out.print("Enter a grade: ");
            double grade = scan.nextDouble();
            scan.nextLine();
            grades.add(grade);
            System.out.println("Grade added successfully!");
            checkGrade(grade);

            System.out.print("Add another grade? (y/n): ");
            String input = scan.nextLine().trim().toLowerCase();
            if (!input.equals("y")) {
                break;
            }
        }
    }

    public static void checkGrade(double grade) {
        if (grade >= 90 && grade <= 100) {
            System.out.println("Excellent");
        } else if (grade >= 80 && grade < 90) {
            System.out.println("Very Good");
        } else if (grade >= 70 && grade < 80) {
            System.out.println("Good");
        } else if (grade >= 60 && grade < 70) {
            System.out.println("Needs Improvement");
        } else if (grade >= 0 && grade < 60) {
            System.out.println("Poor");
        } else {
            System.out.println("Invalid grade. Please enter a grade between 0 and 100.");
        }
    }

    public static void showAllGrades() {
        System.out.println("Grades Entered");
        for (int i = 0; i < grades.size(); i++) {
            System.out.println((i + 1) + ": " + grades.get(i));
        }
    }

    public static void showAverage() {
        if (grades.isEmpty()) {
            System.out.println("No grades available to calculate average.");
            return;
        }

        double sum = 0;

        for (double grade : grades) {
            sum += grade;
        }

        double average = sum / grades.size();

        System.out.println("Average Grade: " + average);

    }

    public static void showHighestLowest() {
        if (grades.isEmpty()) {
            System.out.println("No grades available to find highest and lowest.");
            return;
        }

        double highest = grades.get(0);
        double lowest = grades.get(0);

        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
            if (grade < lowest) {
                lowest = grade;
            }
        }

        System.out.println("Highest Grade: " + highest);
        System.out.println("Lowest Grade: " + lowest);
    }

}
