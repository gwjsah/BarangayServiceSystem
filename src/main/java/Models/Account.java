package Models;

import Service.BackendService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Account {

    private String email;
    private String id;

    public Account() { }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void displayRequestStatusHistory(String requestId) throws IOException, InterruptedException {
        ArrayList<StatusHistory> statusHistories = BackendService.getStatusHistoriesByRequestId(requestId);

        for (StatusHistory sH : statusHistories) {

        }
    }
}
