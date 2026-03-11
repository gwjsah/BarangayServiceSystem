public class QueueTicket {

    private String ticketId;
    private String queueNumber;
    private String currentStage;
    private String status;

    public QueueTicket(String ticketId, String queueNumber) {
        this.ticketId = ticketId;
        this.queueNumber = queueNumber;
        this.currentStage = "Submitted";
        this.status = "Waiting";
    }

    public void advanceStage(String newStage) {
        this.currentStage = newStage;
    }

    public void markDone() {
        this.status = "Done";
    }

    public void displayInfo() {
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Queue Number: " + queueNumber);
        System.out.println("Stage: " + currentStage);
        System.out.println("Status: " + status);
    }

public String getTicketId() {
        return ticketId;
    }
    
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public String getQueueNumber() {
        return queueNumber;
    }
    
    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getCurrentStage() {
        return currentStage;
    }
    
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}