package temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApprovalDecision {

    private String decisionId;
    private String serviceRequestId;
    private String staffName;
    private String decision;   // "Approved" or "Rejected"
    private String remarks;

    public ApprovalDecision() { }

    public ApprovalDecision(String decisionId, String serviceRequestId, String staffName) {
        this.decisionId = decisionId;
        this.serviceRequestId = serviceRequestId;
        this.staffName = staffName;
        this.decision = "Pending";
    }

    public void approve(String remarks) {
        this.decision = "Approved";
        this.remarks = remarks;
        System.out.println("Request " + serviceRequestId + " approved by " + staffName + ".");
    }

    public void reject(String remarks) {
        this.decision = "Rejected";
        this.remarks = remarks;
        System.out.println("Request " + serviceRequestId + " rejected. Reason: " + remarks);
    }

    public void printSummary() {
        System.out.println("Decision ID  : " + decisionId);
        System.out.println("Request ID   : " + serviceRequestId);
        System.out.println("Staff        : " + staffName);
        System.out.println("Decision     : " + decision);
        System.out.println("Remarks      : " + remarks);
    }

    public String getDecisionId()       { return decisionId; }
    public String getServiceRequestId() { return serviceRequestId; }
    public String getStaffName()        { return staffName; }
    public String getDecision()         { return decision; }
    public String getRemarks()          { return remarks; }
}