package Models;

import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends  Account{
    private static final Scanner sc = new Scanner(System.in);

    private String address;
    private boolean isSenior;
    protected ArrayList<String> requestIdList = new ArrayList<>();

    public Resident(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("contactNumber") String contactNumber,
            @JsonProperty("userType") UserType userType,
            @JsonProperty("address") String address,
            @JsonProperty("isSenior") boolean isSenior
    ) {
        super(id, email, name, contactNumber, userType);
        this.address = address;
        this.isSenior = isSenior;
    }

    public void displayInfo() {
        System.out.println("Resident ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Contact Number: " + this.getContactNumber());
    }

    // -- Resident Address ---

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
