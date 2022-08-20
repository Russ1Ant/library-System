package lecturers;

public class Lecturers {
    //Properties of class lecturers
    private String lecturerID;

    public int getNationalID() {
        return nationalID;
    }

    public void setNationalID(int nationalID) {
        this.nationalID = nationalID;
    }

    private int nationalID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int phoneNo;
    private String gender;
    private String department;
    private String faculty;
    private float balance;
    private String workingStatus;


    //Constructor of class students
    public Lecturers(String lecturerID, int nationalID,String firstName, String middleName, String lastName, String emailAddress, int phoneNo, String gender, String department, String faculty, float balance, String workingStatus){

        this.lecturerID = lecturerID;
        this.nationalID = nationalID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.department = department;
        this.faculty = faculty;
        this.balance = balance;
        this.workingStatus = workingStatus;
    }
    public String getLecturerID() {
        return lecturerID;
    }
    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress= emailAddress;
    }
    public int getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(int phoneNo) {
        this.phoneNo= phoneNo;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender= gender;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public float getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;

    }
    public String getWorkingStatus() {
        return workingStatus;
    }
    public void workingStatus(String lecturerStatus) {
        this.workingStatus = workingStatus;
    }

}


