//package temp;
//
//import Models.Enums.UserType;
//import Services.BackendService;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import java.util.Scanner;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class Staff extends Account {
//    private static final Scanner sc = new Scanner(System.in);
//
//    private String name;
//    private UserType userType;
//
//    public Staff() { }
//
//    public String getName() { return name; }
//
//    public void setName(String name) { this.name = name; }
//
//    public UserType getUserType() { return userType; }
//
//    public void setUserType(UserType userType) { this.userType = userType; }
//
//    public static Staff loginPage() {
//        try {
//            System.out.println("=== Barangay Service System temp.Staff Login ===");
//            System.out.print("Enter Email: ");
//            String email = sc.nextLine();
//
//            System.out.print("Enter ID: ");
//            String id = sc.nextLine();
//
//            // Attempt login
//            Account account = BackendService.Auth.loginAccount(id, email, UserType.staff);
//            if (account instanceof Staff staff) {
//                System.out.println("Login success: " + staff.getName() + " (" + staff.getEmail() + ")");
//                return staff;
//            } else {
//                System.out.println("Invalid login: Not a temp.Staff account.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//}

//    public void makeDecision(ServiceRequest request) {
//        System.out.println("\n--- Approval Decision ---");
//        System.out.println("1) Approve");
//        System.out.println("2) Reject");
//        System.out.println("0) Cancel");
//        System.out.print("Select Option: ");
//        int option = sc.nextInt();
//        sc.nextLine();
//
//        if (option == 0) {
//            return;
//        }
//
//        try {
//            ApprovalDecision decision = new ApprovalDecision(java.util.UUID.randomUUID().toString(), request.getId(), this.getName());
//
//            System.out.print("Enter remarks: ");
//            String remarks = sc.nextLine();
//
//            if (option == 1) {
//                decision.approve(remarks);
//                request.setStatus(RequestStatus.approved);
//            } else if (option == 2) {
//                decision.reject(remarks);
//                request.setStatus(RequestStatus.rejected);
//            } else {
//                System.out.println("Invalid option.");
//                return;
//            }
//
//            BackendService.saveApprovalDecision(decision);
//
//            BackendService.updateServiceRequest(request);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void updateReport(ServiceRequest request) {
//        System.out.println("\n--- Update Request Status ---");
//        System.out.println("Current Status: " + request.getStatus());
//        System.out.println("Select new status:");
//        System.out.println("1) Approved\n2) Rejected\n3) Paid\n4) Scheduled\n5) Completed");
//
//        System.out.print("Select Option: ");
//        int option = sc.nextInt();
//        sc.nextLine();
//
//        switch (option) {
//            case 1:
//                request.setStatus(RequestStatus.approved);
//                break;
//            case 2:
//                request.setStatus(RequestStatus.rejected);
//                break;
//            case 3:
//                request.setStatus(RequestStatus.paid);
//                break;
//            case 4:
//                if (request.getStatus().equals(RequestStatus.paid)) {
//                    request.setStatus(RequestStatus.scheduled);
//                    break;
//                }
//                System.out.println("Request must be paid mark it as scheduled.");
//                break;
//            case 5:
//                if (request.getStatus().equals(RequestStatus.paid)) {
//                    request.setStatus(RequestStatus.completed);
//                    break;
//                }
//                System.out.println("Request must be paid for it to be marked as completed");
//                break;
//            default:
//                System.out.println("Invalid option. Try again.");
//        }
//        try {
//            BackendService.updateServiceRequest(request);
//        } catch (IOException | InterruptedException ex) {
//            throw new RuntimeException(ex);
//        }
//    }