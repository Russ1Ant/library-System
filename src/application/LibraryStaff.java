package application;

import org.apache.commons.codec.digest.DigestUtils;

public class LibraryStaff {
	
public LibraryStaff(String staffID, int nationalID, String firstName,String middleName, String lastName, String email,int phone, String password,
			String gender, String department, String workingStatus  ) {

		this.staffID = staffID;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.department = department;
		this.gender = gender;
		this.email = email;
		this.workingStatus = workingStatus;
		this.nationalID = nationalID;
		this.phone = phone;
	}
private String staffID,password,firstName,middleName, lastName,department,gender,email,workingStatus;
private int nationalID,phone;


public String getStaffID() {
	return staffID;
}
public void setStaffID(String staffID) {
	this.staffID = staffID;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
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
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getWorkingStatus() {
	return workingStatus;
}
public void setWorkingStatus(String workingStatus) {
	this.workingStatus = workingStatus;
}
public int getNationalID() {
	return nationalID;
}
public void setNationalID(int nationalID) {
	this.nationalID = nationalID;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}
}
