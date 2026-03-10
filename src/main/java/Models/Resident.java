package Models;

import Models.Enums.PaymentMethod;
import Models.Enums.RequestStatus;
import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends Account {
    private static final Scanner sc = new Scanner(System.in);

    private String name;
    private String address;
    private String contactNumber;
    private UserType userType;
    protected ArrayList<String> requestIdList = new ArrayList<>();

    public Resident() { }

    // --- Setters and Getters ---

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

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
