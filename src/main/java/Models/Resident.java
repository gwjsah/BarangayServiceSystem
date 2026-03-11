package Models;

import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends  Account{
    private static final Scanner sc = new Scanner(System.in);

    private String firstName;
    private String lastName;
    private String address;
    private String contactNumber;
    private UserType userType;
    private boolean isSenior;
    protected ArrayList<String> requestIdList = new ArrayList<>();

    public Resident() { }

    public void displayInfo() {
        System.out.println("Resident ID: " + this.getId());
        System.out.println("Name: " + this.getFirstName() + " " + this.getLastName());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Contact Number: " + this.getContactNumber());
    }

    // -- Resident Name ---

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

    // -- Resident Address ---

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // -- Resident Contact Number ---

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // -- Resident Name ---

    public String getName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    // -- Resident UserType ---

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

    // -- Resident Senior ---

    public boolean isSenior() { return isSenior; }

    public void setSenior(boolean senior) { isSenior = senior; }

    // -- Resident RefID List ---

    public ArrayList<String> getRequestIdList() { return requestIdList; }

    public void setRequestIdList(ArrayList<String> requestIdList) { this.requestIdList = requestIdList; }

    // --- Resident Features ---

    public static Resident loginPage() {
        try {
            System.out.println("=== Barangay Service System Resident Login ===");
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter ID: ");
            String id = sc.nextLine();

            // Attempt login
            Account account = BackendService.Auth.loginAccount(id, email, UserType.resident);
            if (account instanceof Resident resident) {
                System.out.println("Login success: " + resident.getName() + " (" + resident.getEmail() + ")");
                return resident;
            } else {
                System.out.println("Invalid login: Not a Resident account.");
            }
        } catch (Exception e) {
            System.out.println("Invalid login: Not a Resident account.");
        }

        return null;
    }
}
