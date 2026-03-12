package Models;

import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff extends Account{
    private static final Scanner sc = new Scanner(System.in);

    public Staff(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("contactNumber") String contactNumber,
            @JsonProperty("userType") UserType userType
    ) {
        super(id, email, name, contactNumber, userType);
    }

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
