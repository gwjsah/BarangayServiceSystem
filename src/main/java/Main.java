import java.util.Scanner;
import Models.Resident;
import Models.Staff;

public class Main {
    public static void main(String[] args) {
        while(true) {
            System.out.println("Are you a Resident or Staff?");
            System.out.println("1. Resident");
            System.out.println("2. Staff");
            System.out.println("3. Exit");

            System.out.print("Select Option: ");
            int choice = new Scanner(System.in).nextInt();

            switch (choice) {
                case 1 -> {
                    Resident user = Resident.loginPage();
                    if (user != null) {
                        user.mainMenu();  // type is already guaranteed by loginPage
                    }
                }
                case 2 -> {
                    Staff staff = Staff.loginPage();
                    if (staff != null) {
                        staff.mainMenu();
                    }
                }
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }
}