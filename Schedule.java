public class Schedule {

    private String scheduleId;
    private String serviceRequestId;
    private String residentName;
    private String date;
    private String time;
    private String location;
    private String status;

    public Schedule(String scheduleId, String serviceRequestId,
                    String residentName, String date, String time, String location) {
        this.scheduleId = scheduleId;
        this.serviceRequestId = serviceRequestId;
        this.residentName = residentName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = "Pending";
    }

    public void confirm() {
        status = "Confirmed";
        System.out.println("Schedule confirmed: " + date + " at " + time + ", " + location);
    }

    public void markCompleted() {
        status = "Completed";
        System.out.println("Document released to " + residentName + ".");
    }

    public void cancel(String reason) {
        status = "Cancelled";
        System.out.println("Schedule cancelled. Reason: " + reason);
    }

    public void reschedule(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
        this.status = "Rescheduled";
        System.out.println("Rescheduled to " + newDate + " at " + newTime + ".");
    }

    public void printSlip() {
        System.out.println("Schedule ID  : " + scheduleId);
        System.out.println("Request ID   : " + serviceRequestId);
        System.out.println("Resident     : " + residentName);
        System.out.println("Date         : " + date);
        System.out.println("Time         : " + time);
        System.out.println("Location     : " + location);
        System.out.println("Status       : " + status);
    }

    public String getScheduleId()       { return scheduleId; }
    public String getServiceRequestId() { return serviceRequestId; }
    public String getResidentName()     { return residentName; }
    public String getDate()             { return date; }
    public String getTime()             { return time; }
    public String getLocation()         { return location; }
    public String getStatus()           { return status; }

    public void setDate(String date)     { this.date = date; }
    public void setTime(String time)     { this.time = time; }
    public void setStatus(String status) { this.status = status; }
}