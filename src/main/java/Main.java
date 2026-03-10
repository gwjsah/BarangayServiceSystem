import java.util.InputMismatchException;
import java.util.Scanner;

import Models.Resident;
import Models.Staff;
import Services.ResidentService;
import Services.StaffService;

public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        while(true) {
            int choice = -1;
            System.out.println("\n--- Main Menu ---");
            System.out.println("Are you a Resident or Staff?");
            System.out.println("1) Resident");
            System.out.println("2) Staff");
            System.out.println("0) Exit");

            try {
                System.out.print("Select Option: ");
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // discard the bad input
            }

            switch (choice) {
                case 1 -> {
                    Resident resident = Resident.loginPage();
                    if (resident != null) {
                        ResidentService residentService = new ResidentService(resident);
                        residentService.mainMenu();
                    }
                }
                case 2 -> {
                    Staff staff = Staff.loginPage();
                    if (staff != null) {
                        StaffService staffService = new StaffService(staff);
                        staffService.mainMenu();
                    }
                }
                case 0 -> System.exit(0);
            }
        }
    }
}