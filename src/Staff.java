public class Staff {

    private String staffId;
    private String firstName;
    private String lastName;
    private String position;
    private String contactNumber;
    private String email;

    public Staff(String staffId, String firstName, String lastName, String position, String contactNumber, String email) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public void displayInfo() {
        System.out.println("Staff ID: " + staffId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Position: " + position);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email: " + email);
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
