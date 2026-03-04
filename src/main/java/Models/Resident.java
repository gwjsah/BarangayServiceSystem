package Models;

import Models.Enums.UserType;
import Service.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Scanner;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends Account {
    private static final Scanner sc = new Scanner(System.in);

    private String name;
    private String address;
    private String contactNumber;
    private UserType userType;
    protected final ArrayList<String> requestIdList = new ArrayList<>();

    public Resident() { }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

    public ArrayList<String> getRequestIdList() { return requestIdList; }

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
            e.printStackTrace();
        }
        return null;
    }

    public void mainMenu() {
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
                    System.out.println("Listing requests for " + this.getName());
                    // TODO: call BackendService.getReports() and filter by user.getId()
                    displayReportsByResident(this);
                    break;
                case 2:
                    System.out.println("Submitting new request...");
                    // TODO: implement submitting ServiceRequest
                    submitRequest(this);

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

    public void displayReportsByResident(Resident user) {

        System.out.println("Logged in ID: " + user.getId());
        try {
            ArrayList<ServiceRequest> requests = BackendService.getRequestsByResident(user.getId());

            if (requests.isEmpty()) {
                System.out.println("No service requests found.");
                return;
            }

            System.out.println("Your Requests:");
            for (ServiceRequest s : requests) {
                System.out.println(s.getServiceName() + " - " + s.getFee() + " - " + s.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitRequest(Resident user) {
        try {
            String serviceName = "";
            double serviceFee = 0;

            // display all available services
            ArrayList<ServiceType> services = BackendService.getServiceTypes();
            System.out.println("Available Services:");
            for (ServiceType s : services) {
                System.out.println(s.getServiceTypeId() + ": " + s.getName() + " (Fee: " + s.getBaseFee() + ")");
            }

            // Ask resident to select a service
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Service ID to request: ");
            String selectedServiceId = sc.nextLine();

            for (ServiceType s : services) {
                if (selectedServiceId.equals(s.getServiceTypeId())) {
                    serviceName = s.getName();
                    serviceFee = s.getBaseFee();
                }
            }

            // Display all needed requirements for service
            ArrayList<Requirement> requirements = BackendService.getRequirementsByServiceType(selectedServiceId);
            System.out.println("Requirements for this service:");
            for (Requirement r : requirements) {
                System.out.println("- " + r.getName() + (r.isRequired() ? " (Required)" : ""));
            }

            // Ask for the purpose of request
            System.out.print("Enter purpose of your request: ");
            String purpose = sc.nextLine();

            // Create a ServiceReqest object
            ServiceRequest req = new ServiceRequest(serviceName, selectedServiceId, user.getName(), user.getId(), purpose, java.time.LocalDate.now().toString(), serviceFee);

            // Add request to backend records
            BackendService.insertServiceRequest(req);

            // Add the id to resident's list
            user.getRequestIdList().add(req.getId());
            BackendService.updateAccount(user);

            System.out.println("Request submitted successfully! Your request ID: " + req.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
