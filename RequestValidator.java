import java.util.ArrayList;
import java.util.List;

public class RequestValidator {

    private String serviceRequestId;
    private boolean isComplete;
    private boolean isValid;
    private String remarks;
    private List<String> missingRequirements;

    public RequestValidator(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
        this.isComplete = false;
        this.isValid = false;
        this.missingRequirements = new ArrayList<>();
    }

    public void checkCompleteness(List<String> submitted, List<String> required) {
        missingRequirements.clear();
        for (String req : required) {
            if (!submitted.contains(req)) {
                missingRequirements.add(req);
            }
        }
        isComplete = missingRequirements.isEmpty();
    }

    public void validateDocuments(boolean verified, String remarks) {
        this.isValid = verified;
        this.remarks = remarks;
    }

    public void printSummary() {
        System.out.println("Request ID   : " + serviceRequestId);
        System.out.println("Complete     : " + isComplete);
        System.out.println("Valid        : " + isValid);
        System.out.println("Missing      : " + missingRequirements);
        System.out.println("Remarks      : " + remarks);
    }

    public String getServiceRequestId() { return serviceRequestId; }
    public boolean isComplete()         { return isComplete; }
    public boolean isValid()            { return isValid; }
    public String getRemarks()          { return remarks; }
    public List<String> getMissingRequirements() { return missingRequirements; }
}