package Models;

import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff extends Account{
    private static final Scanner sc = new Scanner(System.in);

    private String firstName;
    private String lastName;
    private String contactNumber;
    private UserType userType;

    public Staff() { }

    public void displayInfo() {
        System.out.println("temp.Staff ID: " + this.getId());
        System.out.println("Name: " + this.getFirstName() + " " + this.getLastName());
        System.out.println("Contact Number: " + this.getContactNumber());
        System.out.println("Email: " + this.getEmail());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

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
