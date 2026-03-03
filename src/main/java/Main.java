import java.util.Scanner;
import Models.Resident;
import Service.BackendService;
import java.time.LocalDate;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {  // keep looping for login attempts
            Resident user = loginPage();
            if (user != null) {
                mainMenu(user);
            } else {
                System.out.println("Login failed. Try again.\n");
            }
        }
    }

    private static Resident loginPage() {
        try {
            System.out.println("=== Barangay Service System Login ===");
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter ID: ");
            String id = sc.nextLine();

            // Attempt login
            Resident user = BackendService.Auth.loginAccount(id, email);
            if (user != null) {
                System.out.println("Login success: " + user.getName() + " (" + user.getEmail() + ")");
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void mainMenu(Resident user) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View My Requests");
            System.out.println("2. Submit New Request");
            System.out.println("3. Logout");

            System.out.print("Select an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.println("Listing requests for " + user.getName());
                    // TODO: call BackendService.getReports() and filter by user.getId()
                    break;
                case 2:
                    System.out.println("Submitting new request...");
                    // TODO: implement submitting ServiceRequest
                    break;
                case 3:
                    System.out.println("Logging out...");
                    loggedIn = false;  // break menu loop and go back to loginPage
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void submitReport() {

    }
}