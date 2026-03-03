package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends Account {
//    private String residentId;
    private String name;
    private String address;
    private String contactNumber;
    protected final ArrayList<String> requestIdList = new ArrayList<>();

    public Resident() {

    }

//    public String getResidentId() { return residentId; }
//
//    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
