package Models;

import Models.Enums.UserType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Faculty extends Account {

    private String employeeId;
    private String department;
    private String[] assignedSubjects;

    @JsonCreator
    public Faculty(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("contactNumber") String contactNumber,
            @JsonProperty("userType") UserType userType,
            @JsonProperty("employeeId") String employeeId,
            @JsonProperty("department") String department,
            @JsonProperty("assignedSubjects") String[] assignedSubjects
    ) {
        super(id, email, name, contactNumber, userType);
        this.employeeId = employeeId;
        this.department = department;
        this.assignedSubjects = assignedSubjects;
    }

    public String getEmployeeId() { return employeeId; }

    public String getDepartment() { return department; }

    public String[] getAssignedSubjects() { return assignedSubjects; }

    public void displayFacultyInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Department: " + department);

        if (assignedSubjects != null) {
            System.out.println("Assigned Subjects:");
            for (String subject : assignedSubjects) {
                System.out.println("- " + subject);
            }
        }
    }
}