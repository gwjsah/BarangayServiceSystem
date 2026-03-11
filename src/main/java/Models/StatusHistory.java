package Models;

import Models.Enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusHistory {

    private String id;
    private String serviceRequestId;
    private RequestStatus previousStatus;
    private RequestStatus newStatus;
    private String remarks;
    private String updatedBy;
    private String dateUpdated;

    public StatusHistory() { }

    public StatusHistory(String requestId, RequestStatus previousStatus, RequestStatus newStatus, String remarks, String changedBy, String dateUpdated) {
        this.id = UUID.randomUUID().toString();
        this.serviceRequestId = requestId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.remarks = remarks;
        this.updatedBy = changedBy;
        this.dateUpdated = dateUpdated;
    }

    public void displayHistory() {
        System.out.println("Request ID: " + serviceRequestId);
        System.out.println("From: " + previousStatus + " → To: " + newStatus);
        System.out.println("Remarks: " + remarks);
        System.out.println("Updated By: " + updatedBy);
        System.out.println("Date Updated: " + dateUpdated);
    }

    public String getId() {
        return id;
    }
    public void setHistoryId(String historyId) {
        this.id = historyId;
    }

    public String getRequestId() {
        return serviceRequestId;
    }
    public void setRequestId(String requestId) {
        this.serviceRequestId = requestId;
    }

    public RequestStatus getPreviousStatus() {
        return previousStatus; 
    }
    public void setPreviousStatus(RequestStatus previousStatus) {
        this.previousStatus = previousStatus; 
    }

    public RequestStatus getNewStatus() {
        return newStatus; 
    }
    public void setNewStatus(RequestStatus newStatus) {
        this.newStatus = newStatus; 
    }

    public String getRemarks() {
        return remarks; 
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks; 
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDateChanged() { return dateUpdated; }
    public void setDateChanged(String dateUpdated) { this.dateUpdated = dateUpdated; }
}