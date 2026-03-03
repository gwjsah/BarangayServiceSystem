package Models;

import Models.Enums.RequestStatus;

import java.util.UUID;

public class ServiceRequest {
    private int serviceTypeId;
    private String residentId;
    private String purpose;
    private String requestId;
    private String dateCreated;
    private RequestStatus status;

    public ServiceRequest(int serviceTypeId, String residentId, String purpose, String dateCreated) {
        this.serviceTypeId = serviceTypeId;
        this.residentId = residentId;
        this.purpose = purpose;
        this.requestId = UUID.randomUUID().toString();
        this.dateCreated = dateCreated;
        this.status = RequestStatus.pending;
    }

    @Override
    public String  toString() {
        return "ServiceRequest{" +
                "serviceTypeId=" + serviceTypeId +
                ", residentId='" + residentId + '\'' +
                ", purpose='" + purpose + '\'' +
                ", requestId='" + requestId + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", status=" + status +
                '}';
    }

    public int getServiceTypeId() { return serviceTypeId; }

    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public String getResidentId() { return residentId; }

    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getDateCreated() { return dateCreated; }

    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public RequestStatus getStatus() { return status; }

    public void setStatus(RequestStatus status) { this.status = status; }

}
