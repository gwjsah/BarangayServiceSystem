package Models;

import Models.Enums.RequestStatus;
import Models.Enums.UserType;
import Service.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.util.ArrayList;
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

    public void mainMenu() {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- Staff Dashboard ---");
            System.out.println("1) View All Requests");
            System.out.println("2) View Pending Requests");
            System.out.println("3) View Requests by Status");
            System.out.println("0) Logout");

            System.out.print("Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    displayAllReports();
                    break;
                case 2:
                    displayAllReportsByStatus(RequestStatus.pending);
                    break;
                case 3:
                    displayStatusOptions();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    loggedIn = false;  // break menu loop and go back to loginPage
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void displayAllReports() {
        try {
            while (true) {
                ArrayList<ServiceRequest> requests = BackendService.getServiceRequest();

                if (requests.isEmpty()) {
                    System.out.println("No service requests found.");
                    return;
                }

                System.out.println("\n--- All Service Requests ---");
                System.out.println("   Resident Name - Request Name - Status - Date Reported");
                int i = 1;
                for (ServiceRequest s : requests) {
                    System.out.println(i++ + ") " + s.getResidentName() + " - " + s.getServiceName() + " - " + s.getStatus() + " - " + s.getDateCreated());
                }

                System.out.print("Select request number to view details, or 0 to return: ");
                int option = sc.nextInt();
                sc.nextLine();


                if (option > 0 && option <= requests.size()) {
                    ServiceRequest sr = requests.get(option - 1);
                    viewRequestDetails(sr);
                } else if (option == 0) {
                    return;
                } else {
                    System.out.println("Invalid selection.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayStatusOptions() {
        while (true) {
            System.out.println("\n--- Filter Requests ---");
            System.out.println("1) Pending\n2) Approved\n3) Rejected\n4) Paid\n5) Scheduled\n6) Completed\n0) Return");
            System.out.print("Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            RequestStatus status = null;

            if (option == 0) {
                return;
            }

            switch (option) {
                case 1:
                    status = RequestStatus.pending;
                    break;
                case 2:
                    status = RequestStatus.approved;
                    break;
                case 3:
                    status = RequestStatus.rejected;
                    break;
                case 4:
                    status = RequestStatus.paid;
                    break;
                case 5:
                    status = RequestStatus.scheduled;
                    break;
                case 6:
                    status = RequestStatus.completed;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

            displayAllReportsByStatus(status);
        }
    }

    public void displayAllReportsByStatus(RequestStatus status) {
        try {
            while (true) {
                ArrayList<ServiceRequest> requests = BackendService.getRequestsByStatus(status);

                if (requests.isEmpty()) {
                    System.out.println("No service requests found.");
                    return;
                }


                System.out.println("\n--- All " + status + " requests ---");
                System.out.println("   Resident Name - Request Name - Status - Date Reported");
                int i = 1;
                for (ServiceRequest s : requests) {
                    if (s.getStatus().equals(status)) {
                        System.out.println(i++ + ") " + s.getResidentName() + " - " + s.getServiceName() + " - " + s.getStatus() + " - " + s.getDateCreated());
                    }
                }

                System.out.print("Select request number to view details, or 0 to return: ");
                int option = sc.nextInt();
                sc.nextLine();


                if (option > 0 && option <= requests.size()) {
                    ServiceRequest sr = requests.get(option - 1);
                    viewRequestDetails(sr);
                } else if (option == 0) {
                    return;
                } else {
                    System.out.println("Invalid selection.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewRequestDetails(ServiceRequest request) {
        System.out.println("\n--- Request Details ---");
        System.out.println("Resident Name: " + request.getResidentName());
        System.out.println("Service: "  + request.getServiceName());
        System.out.println("Purpose: "  + request.getPurpose());
        System.out.println("Date Created: " + request.getDateCreated());
        System.out.println("Status: " + request.getStatus());
        System.out.println("Fee: " + request.getFee());

        System.out.print("\n1) Make Approval Decision\n2) Back to List\nSelect option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                makeDecision(request);
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid option. Try again.");
        }

//        if (option == 1) return;
//        System.out.println("Invalid option. Try again.");
    }

    public void makeDecision(ServiceRequest request) {
        System.out.println("\n--- Approval Decision ---");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        System.out.println("0) Cancel");
        System.out.print("Select Option: ");
        int option = sc.nextInt();
        sc.nextLine();

        if (option == 0) {
            return;
        }

        try {
            ApprovalDecision decision = new ApprovalDecision(java.util.UUID.randomUUID().toString(), request.getId(), this.getName());

            System.out.print("Enter remarks: ");
            String remarks = sc.nextLine();

            if (option == 1) {
                decision.approve(remarks);
                request.setStatus(RequestStatus.approved);
            } else if (option == 2) {
                decision.reject(remarks);
                request.setStatus(RequestStatus.rejected);
            } else {
                System.out.println("Invalid option.");
                return;
            }

            BackendService.saveApprovalDecision(decision);

            BackendService.updateServiceRequest(request);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateReport(ServiceRequest request) {
        System.out.println("\n--- Update Request Status ---");
        System.out.println("Current Status: " + request.getStatus());
        System.out.println("Select new status:");
        System.out.println("1) Approved\n2) Rejected\n3) Paid\n4) Scheduled\n5) Completed");

        System.out.print("Select Option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                request.setStatus(RequestStatus.approved);
                break;
            case 2:
                request.setStatus(RequestStatus.rejected);
                break;
            case 3:
                request.setStatus(RequestStatus.paid);
                break;
            case 4:
                if (request.getStatus().equals(RequestStatus.paid)) {
                    request.setStatus(RequestStatus.scheduled);
                    break;
                }
                System.out.println("Request must be paid mark it as scheduled.");
                break;
            case 5:
                if (request.getStatus().equals(RequestStatus.paid)) {
                    request.setStatus(RequestStatus.completed);
                    break;
                }
                System.out.println("Request must be paid for it to be marked as completed");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
        try {
            BackendService.updateServiceRequest(request);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
//approved, rejected, paid, scheduled, completed