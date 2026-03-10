package Models;

import Models.Enums.RequestStatus;
import Models.Enums.UserType;
import Services.BackendService;
import Services.StaffService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff extends Account {
    private static final Scanner sc = new Scanner(System.in);

    private String name;
    private UserType userType;

    public Staff() { }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

    public static Staff loginPage() {
        try {
            System.out.println("=== Barangay Service System Staff Login ===");
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter ID: ");
            String id = sc.nextLine();

            // Attempt login
            Account account = BackendService.Auth.loginAccount(id, email, UserType.staff);
            if (account instanceof Staff staff) {
                System.out.println("Login success: " + staff.getName() + " (" + staff.getEmail() + ")");
                return staff;
            } else {
                System.out.println("Invalid login: Not a Staff account.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}