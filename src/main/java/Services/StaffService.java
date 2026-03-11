package Services;

import Models.*;
import Models.Enums.RequestStatus;

import java.util.*;

public final class StaffService {
    private static final Scanner sc = new Scanner(System.in);
    private Staff staff;

    public StaffService(Staff staff) {
        this.staff = staff;
    }

    public void mainMenu() {
        boolean loggedIn = true;

        while (loggedIn) {
            int option = -1;

            System.out.println("\n--- Staff Dashboard ---");
            System.out.println("1) View All Requests");
            System.out.println("2) View Pending Requests (for validation)");
            System.out.println("3) View Requests by Status");
            System.out.println("4) Assign Schedule (for Paid requests)");
            System.out.println("5) Mark Completed");
            System.out.println("6) Delete Request");
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
                    displayAllReports();
                    break;
                case 2:
                    displayAllReportsByStatus(RequestStatus.pending);
                    break;
                case 3:
                    displayStatusOptions();
                    break;
                case 4:
                    selectRequestToSchedule();
                    break;
                case 5:
                    assignCompleteStatus();
                    break;
                case 6:
                    deleteRequest();
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
                int option = -1;

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

                try {
                    option = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                }


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

    public void displayAllReportsByStatus(RequestStatus status) {
        try {
            while (true) {
                int option = -1;

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

                try {
                    option = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                }


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
        while(true) {
            int option = -1;

            System.out.println("\n--- Request Details ---");
            System.out.println("Resident Name: " + request.getResidentName());
            System.out.println("Service: " + request.getServiceName());
            System.out.println("Purpose: " + request.getPurpose());
            System.out.println("Date Created: " + request.getDateCreated());
            System.out.println("Status: " + request.getStatus());
            System.out.println("Fee: " + request.getFee());

            if (request.getStatus().equals(RequestStatus.pending)) {
                System.out.println("1) Validate Request");
                System.out.println("0) Back");
            } else {
                System.out.println("0) Back");
            }
            System.out.print("Enter option: ");

            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }

            switch (option) {
                case 1:
                    validateRequirements(request);
                    if (request.isValid()) return;
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void validateRequirements(ServiceRequest request) {
        try {
            ArrayList<Requirement> requestRequirements = BackendService.getRequirementsByServiceType(request.getServiceTypeId());

            RequestValidator validator = new RequestValidator(request.getId());

            System.out.println("\n--- Requirement Validation ---");
            System.out.println("Required Documents:");

            int i = 1;
            List<String> requiredNames = new ArrayList<>();
            for (Requirement req : requestRequirements) {
                System.out.println(i++ + ") " + req.getName());
                requiredNames.add(req.getName());
            }

            System.out.print("\nAre all documents submitted? (Y/N): ");
            char input = sc.next().charAt(0);
            sc.nextLine();

            List<String> submitted = new ArrayList<>();

            if (Character.toLowerCase(input) == 'y') {
                submitted.addAll(requiredNames);
            } else {
                System.out.println("Enter submitted document names (type 'done'): ");
                while (true) {
                    String doc = sc.nextLine();
                    if (doc.equalsIgnoreCase("done")) break;
                    submitted.add(doc);
                }
            }

            validator.checkCompleteness(submitted, requiredNames);

            System.out.print("Are submitted documents valid? (Y/N): ");
            input = sc.next().charAt(0);
            sc.nextLine();

            boolean verified = Character.toLowerCase(input) == 'y';

            System.out.print("Enter validation remarks: ");
            String remarks = sc.nextLine();

            validator.validateDocuments(verified, remarks);

            validator.printSummary();

            if (!validator.isComplete()) {
                System.out.println("\nMissing requirements detected.");
                request.setStatus(RequestStatus.rejected);
                BackendService.updateServiceRequest(request);
                System.out.println("Request automatically rejected.");

                StatusHistory sH = new StatusHistory(request.getId(), RequestStatus.pending, RequestStatus.rejected, "Request rejected.", staff.getName(), java.time.LocalDate.now().toString());
                BackendService.saveStatusHistory(sH);
            } else {
                request.setValid(true);
                request.setStatus(RequestStatus.approved);
                BackendService.updateServiceRequest(request);

                StatusHistory sH = new StatusHistory(request.getId(), RequestStatus.pending, RequestStatus.approved, "Request approved.", staff.getName(), java.time.LocalDate.now().toString());
                BackendService.saveStatusHistory(sH);
            }

            BackendService.saveRequestValidator(validator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayStatusOptions() {
        while (true) {
            int option = -1;

            System.out.println("\n--- Filter Requests ---");
            System.out.println("1) Pending\n2) Approved\n3) Rejected\n4) Paid\n5) Scheduled\n6) Completed\n0) Return");
            System.out.print("Select an option: ");

            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }

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

    public void selectRequestToSchedule() {
        try {
            while (true) {
                int option = -1;

                ArrayList<ServiceRequest> requests = BackendService.getRequestsByStatus(RequestStatus.paid);

                if (requests.isEmpty()) {
                    System.out.println("No paid service requests found.");
                    return;
                }

                System.out.println("\n--- Assign Schedule ---");
                System.out.println("Showing PAID requests only");

                System.out.println("   Resident Name - Request Name - Status - Date Reported");
                int i = 1;
                for (ServiceRequest s : requests) {
                    System.out.println(i++ + ") " + s.getResidentName() + " - " + s.getServiceName() + " - " + s.getStatus() + " - " + s.getDateCreated());
                }

                System.out.print("Select request number to schedule, or 0 to return: ");

                try {
                    option = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                }

                if (option > 0 && option <= requests.size()) {
                    ServiceRequest sr = requests.get(option - 1);
                    assignSchedule(sr);
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

    public void assignSchedule(ServiceRequest request) {
        try {
            System.out.println("\n--- Assign Schedule ---");

            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Enter time (HH:MM): ");
            String time = sc.nextLine();

            System.out.print("Enter location: ");
            String location = sc.nextLine();

            Schedule schedule = new Schedule(UUID.randomUUID().toString(), request.getId(), request.getResidentName(), date, time, location, request.getServiceName());

            schedule.confirm();

            BackendService.saveSchedule(schedule);

            request.setStatus(RequestStatus.scheduled);
            BackendService.updateServiceRequest(request);

            System.out.println("Schedule assigned successfully!");

            StatusHistory sH = new StatusHistory(request.getId(), RequestStatus.paid, RequestStatus.scheduled, "Request scheduled.", staff.getName(), java.time.LocalDate.now().toString());
            BackendService.saveStatusHistory(sH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignCompleteStatus() {
        try {
            while (true) {
                int option = -1;

                ArrayList<ServiceRequest> requests = BackendService.getRequestsByStatus(RequestStatus.scheduled);

                if (requests.isEmpty()) {
                    System.out.println("No scheduled service requests found.");
                    return;
                }

                System.out.println("\n--- Mark Request as Completed ---");
                System.out.println("Showing SCHEDULED requests only");

                System.out.println("\n   Resident Name - Request Name - Status");
                int i = 1;
                for (ServiceRequest s : requests) {
                    System.out.println(i++ + ") " + s.getResidentName() + " - " + s.getServiceName() + " - " + s.getStatus());
                }

                System.out.print("Select request number to mark as completed, or 0 to return: ");

                try {
                    option = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                }

                if (option > 0 && option <= requests.size()) {
                    ServiceRequest sr = requests.get(option - 1);
                    System.out.println("Confirm completion for:");
                    System.out.print(sr.getServiceName() + " - " + sr.getResidentName() + "? (Y/N): ");

                    char input = sc.next().charAt(0);
                    sc.nextLine();

                    if (Character.toLowerCase(input) == 'y') {
                        sr.setStatus(RequestStatus.completed);

                        BackendService.updateServiceRequest(sr);

                        Schedule sched = BackendService.getScheduleByRequestId(sr.getId());

                        if (sched != null) {
                            sched.markCompleted();
                            BackendService.updateSchedule(sched);
                        }

                        System.out.println("Request marked as Completed.");

                        StatusHistory sH = new StatusHistory(sr.getId(), RequestStatus.scheduled, RequestStatus.completed, "Request completed.", staff.getName(), java.time.LocalDate.now().toString());
                        BackendService.saveStatusHistory(sH);
                    } else if (Character.toLowerCase(input) == 'n') {
                        System.out.println("Completion cancelled. Returning to Menu.");
                        return;
                    } else {
                        System.out.println("Invalid selection.");
                    }

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

    public void deleteRequest() {
        try {
            while (true) {
                int option = -1;

                ArrayList<ServiceRequest> requests = new ArrayList<>();

                requests.addAll(BackendService.getRequestsByStatus(RequestStatus.completed));
                requests.addAll(BackendService.getRequestsByStatus(RequestStatus.rejected));

                if (requests.isEmpty()) {
                    System.out.println("No scheduled service requests found.");
                    return;
                }

                System.out.println("\n--- Delete Service Request ---");
                System.out.println("Showing REJECTED and COMPLETED requests only");

                System.out.println("\n   Resident Name - Request Name - Status");
                int i = 1;
                for (ServiceRequest s : requests) {
                    System.out.println(i++ + ") " + s.getResidentName() + " - " + s.getServiceName() + " - " + s.getStatus());
                }

                System.out.print("Select request number to delete, or 0 to return: ");

                try {
                    option = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.nextLine();
                }

                if (option > 0 && option <= requests.size()) {
                    System.out.println("You are about to permanently delete:\n");
                    ServiceRequest sr = requests.get(option - 1);
                    System.out.println(sr.getServiceName() + " - " + sr.getResidentName() + " - " + sr.getStatus());
                    System.out.print("\nThis action cannot be undone.\n" + "Confirm deletion? (Y/N): ");

                    char input = sc.next().charAt(0);
                    sc.nextLine();

                    if (Character.toLowerCase(input) == 'y') {
                        // Delete refId from resident's list
                        Resident resident = (Resident) BackendService.getAccountFromId(sr.getResidentId());

                        if (resident != null) {
                            resident.getRequestIdList().remove(sr.getId());
                            BackendService.updateResident(resident);
                        }

                        // Delete Schedule
                        Schedule sched = BackendService.getScheduleByRequestId(sr.getId());
                        if (sched != null) {
                            BackendService.deleteScheduleById(sched.getId());
                        }

                        // Delete Validation
                        RequestValidator rV = BackendService.getValidatorByRequestId(sr.getId());
                        if (rV != null) {
                            BackendService.deleteValidatorById(rV.getId());
                        }

                        // Delete Request
                        BackendService.deleteServiceRequest(sr.getId());

                        System.out.println("Returning to Delete Menu...");
                    } else if (Character.toLowerCase(input) == 'n') {
                        System.out.println("Deletion cancelled.\nReturning to menu...");
                        return;
                    } else {
                        System.out.println("Invalid selection.");
                    }

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
}


