package Service;

import Models.*;
import Models.Enums.RequestStatus;
import Models.Enums.UserType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class BackendService {

    private static HttpClient client = HttpClient.newHttpClient();
    private BackendService(){}

    // --- Accounts ---

    public static Account getAccountFromId(String id) throws IOException, InterruptedException {
        if (id == null) throw new IllegalArgumentException("Passed Null ID");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.body().equals("Not Found")) return null;

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(response.body());
        String userTypeStr = node.get("userType").asText();

        if ("resident".equalsIgnoreCase(userTypeStr)) {
            return mapper.treeToValue(node, Resident.class);
        } else if ("staff".equalsIgnoreCase(userTypeStr)) {
            return mapper.treeToValue(node, Staff.class);
        } else {
            return null;
        }
    }

    public static void insertAccount(Resident Resident) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Resident);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) throw new IOException("Failed to create account");
    }

    public static void updateResident(Resident Resident) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Resident);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/" + Resident.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Failed to update account");
    }

    // --- ServiceRequests / Service Requests ---

    public static void insertServiceRequest(ServiceRequest ServiceRequest) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ServiceRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) throw new IOException("Failed to create ServiceRequest");
    }

    public static void updateServiceRequest(ServiceRequest ServiceRequest) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ServiceRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records/" + ServiceRequest.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Failed to update ServiceRequest");
    }

    public static ArrayList<ServiceRequest> getServiceRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch service requests");
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceRequest>>() {});
    }

    public static ServiceRequest getServiceRequest(String title) throws IOException, InterruptedException {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records?title=" + encodedTitle))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<ServiceRequest>> tr = new TypeReference<ArrayList<ServiceRequest>>() {};
        ArrayList<ServiceRequest> ServiceRequests = mapper.readValue(response.body(), tr);

        if (ServiceRequests.isEmpty()) throw new IOException("ServiceRequest not found");
        return ServiceRequests.get(0);
    }

    public static ArrayList<ServiceRequest> getRequestsByResident(String residentId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/records"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            ArrayList<ServiceRequest> allRequests = mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceRequest>>() {});

            ArrayList<ServiceRequest> filtered = allRequests.stream().filter(r -> residentId.equals(r.getResidentId())).collect(Collectors.toCollection(ArrayList::new));

            return filtered;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static ArrayList<ServiceRequest> getRequestsByStatus(RequestStatus status) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/records"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            ArrayList<ServiceRequest> allRequests = mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceRequest>>() {});

            ArrayList<ServiceRequest> filtered = allRequests.stream().filter(r -> status.equals(r.getStatus())).collect(Collectors.toCollection(ArrayList::new));

            return filtered;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static ArrayList<ServiceRequest> getRequestsByStatusAndResident(String residentId, RequestStatus status) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/records"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            ArrayList<ServiceRequest> allRequests = mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceRequest>>() {});

//            ArrayList<ServiceRequest> filtered = allRequests.stream().filter(r -> status.equals(r.getStatus())).collect(Collectors.toCollection(ArrayList::new));

            ArrayList<ServiceRequest> filtered = allRequests.stream().filter(r -> (residentId == null || residentId.equals(r.getResidentId())) && (status == null || status.equals(r.getStatus()))).collect(Collectors.toCollection(ArrayList::new));
            return filtered;


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static void updateRequestStatus(ServiceRequest request, RequestStatus newStatus) {
        try {
            request.setStatus(newStatus);
//            String arrayName = "records";
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);

            HttpRequest requestPatch = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/records/" + request.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(requestPatch, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update status response: " + response.statusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteServiceRequest(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Request deleted successfully.");
            return true;
        } else {
            System.out.println("Failed to delete request.");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response: " + response.body());
            return false;
        }
    }

    public static ArrayList<Requirement> getRequirementsByServiceType(String serviceTypeId) throws IOException, InterruptedException {
        // Fetch all requirements from JSON server
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/requirements"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Requirement> allRequirements = mapper.readValue(response.body(), new TypeReference<ArrayList<Requirement>>() {});

        // Filter by serviceTypeId
        ArrayList<Requirement> filtered = new ArrayList<>();
        for (Requirement r : allRequirements) {
            if (r.getServiceTypeId().equals(serviceTypeId)) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public static ArrayList<ServiceType> getServiceTypes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/serviceTypes"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceType>>() {});
    }

    // --- Approval Decision ---

    public static void saveApprovalDecision(ApprovalDecision decision) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(decision);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/approvalDecisions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            System.out.println("Approval decision saved successfully.");
        } else {
            System.out.println("Failed to save approval decision.");
            System.out.println("Response: " + response.body());
        }
    }

    // --- Request Validation ---

    public static boolean saveRequestValidator(RequestValidator validator) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(validator);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/requestValidator"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            System.out.println("Validation saved successfully.");
            return true;
        } else {
            System.out.println("Failed to save validation.");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response: " + response.body());
            return false;
        }
    }

    public static ArrayList<ServiceRequest> getRequestValidators() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/requestValidator"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch request validations");
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ArrayList<ServiceRequest>>() {});
    }

    public static RequestValidator getValidatorByRequestId(String requestId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/requestValidator?serviceRequestId=" + requestId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<RequestValidator> reqVal = mapper.readValue(response.body(), new TypeReference<ArrayList<RequestValidator>>() {});

        return reqVal.isEmpty() ? null : reqVal.get(0);
    }

    public static void deleteValidatorById(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/requestValidator/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Validator deleted successfully.");
        } else {
            System.out.println("Failed to delete validator.");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response: " + response.body());
        }
    }

    // --- Schedule ---

    public static void saveSchedule(Schedule schedule) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schedule);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/schedules"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static Schedule getScheduleByRequestId(String requestId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/schedules?serviceRequestId=" + requestId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Schedule> schedules =
                mapper.readValue(response.body(), new TypeReference<ArrayList<Schedule>>() {});

        return schedules.isEmpty() ? null : schedules.get(0);
    }

    public static void updateSchedule(Schedule schedule) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schedule);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/schedules/" + schedule.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void deleteScheduleById(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/schedules/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Schedule deleted successfully.");
        } else {
            System.out.println("Failed to delete schedule.");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response: " + response.body());
        }
    }

    // --- Payment Transaction ---

    public static void savePaymentTransaction(PaymentTransaction pT) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(pT);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/paymentTransaction"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static PaymentTransaction getPaymentTransactionByRequestId(String requestId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/paymentTransaction?serviceRequestId=" + requestId))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<PaymentTransaction> paymentTransactions = mapper.readValue(response.body(), new TypeReference<ArrayList<PaymentTransaction>>() {});

        return paymentTransactions.isEmpty() ? null : paymentTransactions.get(0);
    }

    public static void updatePaymentTransaction(PaymentTransaction paymentTransaction) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(paymentTransaction);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/schedules/" + paymentTransaction.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // --- Auth ---
    public static final class Auth {

        // Generic login for both types
        public static Account loginAccount(String id, String email, UserType expectedType)
                throws IOException, InterruptedException {

            Account account = getAccountFromId(id);  // fetch account by ID
            if (account == null) return null;        // account not found
            if (!account.getEmail().equals(email)) return null; // email mismatch

            // Check userType
            UserType type = null;
            if (account instanceof Resident) {
                type = ((Resident) account).getUserType();
            } else if (account instanceof Staff) {
                type = ((Staff) account).getUserType();
            }

            if (type != expectedType) return null; // type mismatch
            return account;
        }

    }

}