package studentsPackage;

import java.sql.SQLException;

import fine.Fine;

public class Students {
    //Properties of class students
   private String regNo;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int phoneNo;
    private String gender;
    private String course;
    private String department;
    private String faculty;
    private int yearOfStudy;
    private double balance;
    private String studentStatus;


//Constructor of class students
    public Students(String regNo,String firstName, String middleName, String lastName, String emailAddress, int phoneNo, String gender,String course, String department, String faculty, int yearOfStudy, double d, String studentStatus){

        this.regNo = regNo;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.course = course;
        this.department = department;
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        this.balance = d;
        this.studentStatus = studentStatus;
    }
    public String getRegNo() {
        return regNo;
    }
    public void setRegNo(String regNo) {
        this.regNo = regNo;
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

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
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
    public int getYearOfStudy() {
        return yearOfStudy;
    }
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance() throws ClassNotFoundException, SQLException {
       // this.balance = balance;
    	//this.balance = Fine.computeFine(this.getRegNo());

}
    public String getStudentStatus() {
        return studentStatus;
    }
    public void studentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

}

