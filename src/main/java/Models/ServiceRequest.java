package Models;

import Models.Enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceRequest {
    private String serviceName;
    private String serviceTypeId;
    private String residentName;
    private String residentId;
    private String purpose;
    private String id;
    private String dateCreated;
    private RequestStatus status;
    private double fee;
    private boolean isValid;

    public ServiceRequest() { }

    public ServiceRequest(String serviceName, String serviceTypeId, String residentName, String residentId, String purpose, String dateCreated, double fee) {
        this.serviceName = serviceName;
        this.serviceTypeId = serviceTypeId;
        this.residentName = residentName;
        this.residentId = residentId;
        this.purpose = purpose;
        this.id = UUID.randomUUID().toString();
        this.dateCreated = dateCreated;
        this.status = RequestStatus.pending;
        this.fee = fee;
        this.isValid = false;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceTypeId='" + serviceTypeId + '\'' +
                ", residentName='" + residentName + '\'' +
                ", residentId='" + residentId + '\'' +
                ", purpose='" + purpose + '\'' +
                ", id='" + id + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", status=" + status +
                ", fee=" + fee +
                '}';
    }

    public String getServiceTypeId() { return serviceTypeId; }

    public void setServiceTypeId(String serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public String getResidentId() { return residentId; }

    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getDateCreated() { return dateCreated; }

    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public RequestStatus getStatus() { return status; }

    public void setStatus(RequestStatus status) { this.status = status; }

    public String getResidentName() { return residentName; }

    public double getFee() { return fee; }

    public void setFee(double fee) { this.fee = fee; }

    public String getServiceName() { return serviceName; }

    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public boolean isValid() { return isValid; }

    public void setValid(boolean valid) { isValid = valid; }
}
