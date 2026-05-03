import java.util.*;

// Subject class
class Subject {

    String name;
    int hoursRequired;
    int daysLeft;

    Subject(String name, int hoursRequired, int daysLeft) {
        this.name = name;
        this.hoursRequired = hoursRequired;
        this.daysLeft = daysLeft;
    }

    void display() {
        System.out.println(name + " | Hours: " + hoursRequired + " | Days Left: " + daysLeft);
    }

    double getPriority() {
        if (daysLeft <= 1) {
            return hoursRequired * 1.5;
        }
        return (double) hoursRequired / daysLeft;
    }
}

public class StudyPlanner {

    // COLORS
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";
    static final String PURPLE = "\u001B[35m";

    static void printPlan(Subject s, double time) {

        if (time < 0.5) {
            System.out.println(PURPLE + "Skip " + s.name + " today (low priority)" + RESET);
        } 
        else if (s.daysLeft <= 1) {
            System.out.println(RED + "!!! URGENT: Study " + s.name + " for "
                    + String.format("%.2f", time) + " hours" + RESET);
        } 
        else {
            System.out.println(BLUE + "Study " + s.name + " for "
                    + String.format("%.2f", time) + " hours" + RESET);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Subject> subjects = new ArrayList<>();

        while (true) {

            System.out.println(CYAN + "\n=== SMART STUDY PLANNER v1.0 ===" + RESET);
            System.out.println(YELLOW + "1. Add Subjects" + RESET);
            System.out.println(YELLOW + "2. View Study Plan" + RESET);
            System.out.println(YELLOW + "3. Clear Subjects" + RESET);
            System.out.println(YELLOW + "4. Exit" + RESET);

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println(RED + "Invalid input! Enter a number." + RESET);
                sc.next();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            // ===== ADD =====
            if (choice == 1) {

                System.out.print("Enter number of subjects: ");
                while (!sc.hasNextInt()) {
                    System.out.print("Invalid input! Enter a number: ");
                    sc.next();
                }

                int n = sc.nextInt();
                sc.nextLine();

                for (int i = 0; i < n; i++) {

                    System.out.println("\nSubject " + (i + 1));

                    System.out.print("Enter subject name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter hours required: ");
                    while (!sc.hasNextInt()) {
                        System.out.print("Invalid input! Enter a number: ");
                        sc.next();
                    }
                    int hrs = sc.nextInt();

                    System.out.print("Enter days left: ");
                    while (!sc.hasNextInt()) {
                        System.out.print("Invalid input! Enter a number: ");
                        sc.next();
                    }
                    int days = sc.nextInt();
                    sc.nextLine();

                    subjects.add(new Subject(name, hrs, days));
                }

                System.out.println(GREEN + "\nSubjects added successfully!" + RESET);
            }

            // ===== VIEW =====
            else if (choice == 2) {

                if (subjects.isEmpty()) {
                    System.out.println("No subjects added yet!");
                    continue;
                }

                subjects.sort((a, b) -> Double.compare(b.getPriority(), a.getPriority()));

                System.out.println(CYAN + "\n--- Your Subjects ---" + RESET);
                for (Subject s : subjects) s.display();

                System.out.println("\n--- Priorities ---");
                for (Subject s : subjects)
                    System.out.println(s.name + " Priority: " + String.format("%.2f", s.getPriority()));

                System.out.print("\nEnter total study hours for today: ");
                int totalHours = sc.nextInt();

                double totalPriority = 0;
                for (Subject s : subjects)
                    totalPriority += s.getPriority();

                ArrayList<Double> allocated = new ArrayList<>();
                double remaining = totalHours;

                System.out.println(CYAN + "\n--- Today's Study Plan ---" + RESET);

                for (int i = 0; i < subjects.size(); i++) {

                    Subject s = subjects.get(i);
                    double time;

                    // 🔥 FIX: limit urgent domination
                    if (s.daysLeft == 0) {
                        double cap = totalHours * 0.5;
                        time = Math.min(s.hoursRequired, cap);
                        time = Math.min(time, remaining);
                    }
                    else if (i == subjects.size() - 1) {
                        time = remaining;
                    }
                    else {
                        time = (s.getPriority() / totalPriority) * totalHours;

                        if (time < 0.5 && remaining >= 0.5)
                            time = 0.5;

                        if (time > remaining)
                            time = remaining;
                    }

                    time = Math.round(time * 100.0) / 100.0;
                    remaining -= time;
                    allocated.add(time);

                    printPlan(s, time);
                }

                System.out.println("\nTotal study hours planned: " + totalHours);

                // ===== ADAPTIVE =====
                System.out.print("\nDid you complete today's plan? (yes/no): ");
                String res = sc.next();

                if (res.equalsIgnoreCase("no")) {

                    for (int i = 0; i < subjects.size(); i++) {
                        subjects.get(i).hoursRequired += Math.round(allocated.get(i));
                    }

                    for (Subject s : subjects)
                        if (s.daysLeft > 0) s.daysLeft--;

                    System.out.println(GREEN + "\n[!] Plan updated for next day" + RESET);

                    double newTotal = 0;
                    for (Subject s : subjects)
                        newTotal += s.getPriority();

                    double newRemaining = totalHours;

                    System.out.println(CYAN + "\n--- Next Day Study Plan ---" + RESET);

                    for (int i = 0; i < subjects.size(); i++) {

                        Subject s = subjects.get(i);
                        double time;

                        if (i == subjects.size() - 1) {
                            time = newRemaining;
                        }
                        else {
                            time = (s.getPriority() / newTotal) * totalHours;

                            if (time < 0.5 && newRemaining >= 0.5)
                                time = 0.5;

                            if (time > newRemaining)
                                time = newRemaining;
                        }

                        time = Math.round(time * 100.0) / 100.0;
                        newRemaining -= time;

                        printPlan(s, time);
                    }
                }
            }

            else if (choice == 3) {
                subjects.clear();
                System.out.println(GREEN + "All subjects cleared!" + RESET);
            }

            else if (choice == 4) {
                System.out.println(GREEN + "Exiting... Goodbye!" + RESET);
                break;
            }

            else {
                System.out.println(RED + "Invalid choice!" + RESET);
            }
        }

        sc.close();
    }
}