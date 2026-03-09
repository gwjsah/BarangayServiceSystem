package Models;

import Models.Enums.PaymentMethod;
import Models.Enums.RequestStatus;
import Models.Enums.UserType;
import Service.BackendService;
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

    public void mainMenu() {
        boolean loggedIn = true;

        while (loggedIn) {
            int option = -1;

            System.out.println("\n--- Resident Dashboard ---");
            System.out.println("1) View My Requests");
            System.out.println("2) Submit New Request");
            System.out.println("3) Balance / Pay Requests");
            System.out.println("4) View Scheduled Request");
            System.out.println("0) Logout");

            try {
                System.out.print("Select an option: ");
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }

            switch (option) {
                case 1:
                    System.out.println("Listing requests for " + this.getName());
                    displayReportsByResident();
                    break;
                case 2:
                    System.out.println("Submitting new request...");
                    submitRequest(this);
                    break;
                case 3:
                    displayBalanceMenu();
                    break;
                case 4:
                    viewScheduledRequest();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;
            }
        }
    }

    public void displayReportsByResident() {

//        System.out.println("Logged in ID: " + user.getId());
        try {
            ArrayList<ServiceRequest> requests = BackendService.getRequestsByResident(this.getId());

            if (requests.isEmpty()) {
                System.out.println("No service requests found.");
                return;
            }

            System.out.println("Your Requests:");
            int i = 1;
            for (ServiceRequest s : requests) {
                System.out.println(i++ + ") " + s.getServiceName() + " - " + s.getFee() + " - " + s.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayBalanceMenu() {
        try {
            ArrayList<ServiceRequest> requests = BackendService.getRequestsByStatusAndResident(this.getId(), RequestStatus.approved);

            if (requests.isEmpty()) {
                System.out.println("No approved service requests found.");
                return;
            }


            System.out.println("\n--- Balance Menu ---");
            System.out.println("Request(s) have been approved and are awaiting payment:");
            System.out.println("A) Pay All");

            int i = 1;
            for (ServiceRequest req : requests) {
                System.out.println(i++ + ") " + req.getServiceName() + " - Fee: " + req.getFee());
            }

            System.out.print("Select request number to pay, 'A' to pay all, or 0 to return: ");
            int input = sc.next().charAt(0);
            sc.nextLine();

            if (input == 'A' || input == 'a') {
                payAllMenu(requests);
            } else if (Character.isDigit(input)) {
                int option = Character.getNumericValue(input);

                if (option > 0 && option <= requests.size()) {
                    ServiceRequest sr = requests.get(option - 1);
                    paySingleMenu(sr);
                } else if (option == 0) {
                    System.out.println("Returning to previous menu...");
                } else {
                    System.out.println("Invalid selection! Please choose a valid number.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payAllMenu(ArrayList<ServiceRequest> requests) {
        try {
            PaymentMethod pM;

            System.out.println("\n--- Full Payment ---");

            double fullBalance = 0;

            for (ServiceRequest req : requests) {
                fullBalance += req.getFee();
            }

            System.out.println("Fee: " + fullBalance);
            System.out.print("\nSelect Payment Method\n" +
                    "1) Cash (Pay at Barangay Hall)\n" +
                    "2) GCash\n" +
                    "3) Maya\n" +
                    "4) Credit Card\n" +
                    "0) Cancel Payment\n" +
                    "\n" +
                    "Select option: ");

            int option = sc.nextInt();

            switch (option) {
                case 1 -> pM = PaymentMethod.cash;
                case 2 -> pM = PaymentMethod.gcash;
                case 3 -> pM = PaymentMethod.paymaya;
                case 4 -> pM = PaymentMethod.debit_card;
                case 0 -> {
                    System.out.println("Payment Cancelled. Returning to Main Menu");
                    return;
                }
                default -> {
                    System.out.println("Invalid option.");
                    return;
                }
            }

            PaymentTransaction pT = new PaymentTransaction("", fullBalance, pM);

            System.out.print("Confirm Payment (Y/N): ");
            char input = sc.next().charAt(0);
            sc.nextLine();

            if (Character.toLowerCase(input) == 'y') {
                pT.confirmPayment();

                for (ServiceRequest req : requests) {
                    req.setStatus(RequestStatus.paid);
                    BackendService.updateServiceRequest(req);
                }

                BackendService.savePaymentTransaction(pT);

                System.out.println("Payment Successful! Status updated to Paid");

                System.out.print("Do you want a receipt? (Y/N): ");
                input = sc.next().charAt(0);
                sc.nextLine();

                if (Character.toLowerCase(input) == 'y') {
                    Receipt receipt = new Receipt(pT);
//                    int transactionId, String processedBy, PaymentMethod method, ArrayList<ServiceRequest> requests
                    receipt.printReceipt( "Barangay Office", requests);
                }
            } else {
                pT.cancelPayment();
                BackendService.savePaymentTransaction(pT);

                System.out.println("Payment Cancelled. Returning to Main Menu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paySingleMenu(ServiceRequest request) {
        try {
            PaymentMethod pM;

            System.out.println("\n--- Single Payment ---");
            System.out.println("Request: " + request.getServiceName());
            System.out.println("Status: " + request.getStatus());
            System.out.println("Fee: " + request.getFee());
            System.out.print("\nSelect Payment Method\n" +
                    "1) Cash (Pay at Barangay Hall)\n" +
                    "2) GCash\n" +
                    "3) Maya\n" +
                    "4) Credit Card\n" +
                    "0) Cancel Payment\n" +
                    "\n" +
                    "Select option: ");

            int option = sc.nextInt();

            switch (option) {
                case 1 -> pM = PaymentMethod.cash;
                case 2 -> pM = PaymentMethod.gcash;
                case 3 -> pM = PaymentMethod.paymaya;
                case 4 -> pM = PaymentMethod.debit_card;
                case 0 -> {
                    System.out.println("Payment Cancelled. Returning to Main Menu");
                    return;
                }
                default -> {
                    System.out.println("Invalid option.");
                    return;
                }
            }

            PaymentTransaction pT = new PaymentTransaction(request.getId(), request.getFee(), pM);

            System.out.print("Confirm Payment (Y/N): ");
            char input = sc.next().charAt(0);
            sc.nextLine();

            if (Character.toLowerCase(input) == 'y') {
                pT.confirmPayment();

                request.setStatus(RequestStatus.paid);
                BackendService.updateServiceRequest(request);

                BackendService.savePaymentTransaction(pT);

                System.out.println("Payment Successful! Status updated to Paid");

                System.out.print("Do you want a receipt? (Y/N): ");
                input = sc.next().charAt(0);
                sc.nextLine();

//                int transactionId, String processedBy, PaymentMethod method, ArrayList<ServiceRequest> requests
                if (Character.toLowerCase(input) == 'y') {
                    ArrayList<ServiceRequest> requestList = new ArrayList<>();
                    requestList.add(request);

                    Receipt receipt = new Receipt(pT);
//                    int transactionId, String processedBy, PaymentMethod method, ArrayList<ServiceRequest> requests
                    receipt.printReceipt("Barangay Office", requestList);
                }
            } else {
                pT.cancelPayment();
                BackendService.savePaymentTransaction(pT);

                System.out.println("Payment Cancelled. Returning to Main Menu");
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

            System.out.println("\n--- Submit Request Menu ---");
            System.out.println("Available Services:");
            for (ServiceType s : services) {
                System.out.println(s.getServiceTypeId() + ") " + s.getName() + " (Fee: " + s.getBaseFee() + ")");
            }
            System.out.println("0) Return");

            // Ask resident to select a service
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Service ID to request: ");
            String selectedServiceId = sc.nextLine();

            if (selectedServiceId.equals("0")) return;

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
            BackendService.updateResident(user);

            System.out.println("Request submitted successfully! Your request ID: " + req.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewScheduledRequest() {

        try {
            ArrayList<Schedule> requests = BackendService.getScheduleByResidentName(this.getName());

            if (requests.isEmpty()) {
                System.out.println("No scheduled requests found.");
                return;
            }

            System.out.println("\nScheduled Request:");

            for (Schedule s : requests) {
                s.printSlip();
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
