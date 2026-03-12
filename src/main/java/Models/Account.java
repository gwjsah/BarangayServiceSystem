package Models;

import Models.Enums.UserType;
import Services.BackendService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Account {

    private String email;
    private final String id;
    private final String name;
    private String contactNumber;
    private final UserType userType;

    @JsonCreator
    public Account(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("contactNumber") String contactNumber,
            @JsonProperty("userType") UserType userType
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
        this.userType = userType;
    }


    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getContactNumber() { return contactNumber; }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public UserType getUserType() { return userType; }

    public void displayRequestStatusHistory(String requestId) throws IOException, InterruptedException {
        ArrayList<StatusHistory> statusHistories = BackendService.getStatusHistoriesByRequestId(requestId);

        if (statusHistories.isEmpty()) {
            System.out.println("No status history found for this request");
        } else {
            System.out.println("\n--- Request Status History ---");
        }

        for (StatusHistory sH : statusHistories) {
            sH.displayHistory();
            System.out.println();
        }
    }
}
