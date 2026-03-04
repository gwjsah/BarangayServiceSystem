package Models;

import Models.Enums.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {

    private String id;
    private String serviceRequestId;
    private String residentName;
    private String date;
    private String time;
    private String location;
    private ScheduleStatus status;

    public Schedule() { }

    public Schedule(String id, String serviceRequestId, String residentName, String date, String time, String location) {
        this.id = id;
        this.serviceRequestId = serviceRequestId;
        this.residentName = residentName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = ScheduleStatus.pending;
    }

    public void confirm() {
        status = ScheduleStatus.confirmed;
        System.out.println("Schedule confirmed: " + date + " at " + time + ", " + location);
    }

    public void markCompleted() {
        status = ScheduleStatus.completed;
        System.out.println("Document released to " + residentName + ".");
    }

    public void cancel(String reason) {
        status = ScheduleStatus.cancelled;
        System.out.println("Schedule cancelled. Reason: " + reason);
    }

    public void reschedule(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
        this.status = ScheduleStatus.rescheduled;
        System.out.println("Rescheduled to " + newDate + " at " + newTime + ".");
    }

    public void printSlip() {
        System.out.println("Schedule ID  : " + id);
        System.out.println("Request ID   : " + serviceRequestId);
        System.out.println("Resident     : " + residentName);
        System.out.println("Date         : " + date);
        System.out.println("Time         : " + time);
        System.out.println("Location     : " + location);
        System.out.println("Status       : " + status);
    }

    public String getId()       { return id; }
    public String getServiceRequestId() { return serviceRequestId; }
    public String getResidentName()     { return residentName; }
    public String getDate()             { return date; }
    public String getTime()             { return time; }
    public String getLocation()         { return location; }
    public ScheduleStatus getStatus()           { return status; }

    public void setDate(String date)     { this.date = date; }
    public void setTime(String time)     { this.time = time; }
    public void setStatus(ScheduleStatus status) { this.status = status; }
}