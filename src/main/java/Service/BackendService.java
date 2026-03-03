package Service;

import Models.Resident;
import Models.ServiceRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public final class BackendService {

    private static HttpClient client = HttpClient.newHttpClient();
    private BackendService(){}

    // --- Accounts ---

    public static Resident getAccountFromId(String id) throws IOException, InterruptedException {
        if (id == null) throw new IllegalArgumentException("Passed Null ID");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.body().equals("Not Found")) return null;

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), Resident.class);
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

    public static void updateAccount(Resident Resident) throws IOException, InterruptedException {
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
                .uri(URI.create("http://localhost:3000/records/" + ServiceRequest.getRequestId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Failed to update ServiceRequest");
    }

    public static ArrayList<ServiceRequest> getServiceRequests() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<ServiceRequest>> tr = new TypeReference<ArrayList<ServiceRequest>>() {};
        return mapper.readValue(response.body(), tr);
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

    public static void deleteServiceRequest(String title) throws IOException, InterruptedException {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/records?title=" + encodedTitle))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<ServiceRequest>> tr = new TypeReference<ArrayList<ServiceRequest>>() {};
        ArrayList<ServiceRequest> ServiceRequests = mapper.readValue(response.body(), tr);

        for (ServiceRequest r : ServiceRequests) {
            HttpRequest delReq = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/records/" + r.getRequestId()))
                    .DELETE()
                    .build();
            client.send(delReq, HttpResponse.BodyHandlers.ofString());
        }
    }

    // --- Auth ---
    public static final class Auth {
        public static Resident loginAccount(String id, String email) throws IOException, InterruptedException {
            Resident Resident = getAccountFromId(id);
            if (Resident == null) return null;
            if (!Resident.getEmail().equals(email)) return null;
            return Resident;
        }
    }
}