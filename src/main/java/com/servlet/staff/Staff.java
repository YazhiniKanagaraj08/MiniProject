package com.servlet.staff;

public class Staff {
    private String staffID;
    private String staffName;
    private String designation;
    private String departmentID;
    private String specialization1;
    private String specialization2;
    private String specialization3;
    private String specialization4;
    private String emailID;
    private String contactNumber;

    public Staff(String staffID, String staffName, String designation, String departmentID, String specialization1, String specialization2, String specialization3, String specialization4, String emailID, String contactNumber) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.designation = designation;
        this.departmentID = departmentID;
        this.specialization1 = specialization1;
        this.specialization2 = specialization2;
        this.specialization3 = specialization3;
        this.specialization4 = specialization4;
        this.emailID = emailID;
        this.contactNumber = contactNumber;
    }

    // Getters and setters
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getSpecialization1() {
        return specialization1;
    }

    public void setSpecialization1(String specialization1) {
        this.specialization1 = specialization1;
    }

    public String getSpecialization2() {
        return specialization2;
    }

    public void setSpecialization2(String specialization2) {
        this.specialization2 = specialization2;
    }
  
    public String getSpecialization3() {
        return specialization3;
    }

    public void setSpecialization3(String specialization3) {
        this.specialization3 = specialization3;
    }

    public String getSpecialization4() {
        return specialization4;
    }

    public void setSpecialization4(String specialization4) {
        this.specialization4 = specialization4;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

