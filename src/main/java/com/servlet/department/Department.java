package com.servlet.department;
public class Department {
    private String departmentID;
    private String departmentName;
    private String hod;

    public Department(String departmentID, String departmentName, String hod) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.hod = hod;
    }

    // Getters and setters
    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String gethod() {
        return hod;
    }

    public void sethod(String hod) {
        this.hod = hod;
    }
}
