public class RequestStatusHistory {

    private String historyId;
    private String requestId;
    private String previousStatus;
    private String newStatus;
    private String remarks;
    private String changedBy;

    public RequestStatusHistory(String historyId, String requestId, String previousStatus, String newStatus, String remarks, String changedBy) {
        this.historyId = historyId;
        this.requestId = requestId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.remarks = remarks;
        this.changedBy = changedBy;
    }

    public void displayHistory() {
        System.out.println("History ID: " + historyId);
        System.out.println("Request ID: " + requestId);
        System.out.println("From: " + previousStatus + " → To: " + newStatus);
        System.out.println("Remarks: " + remarks);
        System.out.println("Changed By: " + changedBy);
    }

    public String getHistoryId() {
        return historyId; 
    }
    public void setHistoryId(String historyId) {
        this.historyId = historyId; 
    }

    public String getRequestId() {
        return requestId; 
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId; 
    }

    public String getPreviousStatus() {
        return previousStatus; 
    }
    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus; 
    }

    public String getNewStatus() {
        return newStatus; 
    }
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus; 
    }

    public String getRemarks() {
        return remarks; 
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks; 
    }

    public String getChangedBy() {
        return changedBy; 
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy; 
    }
}