package com.servlet.allotment;
public class Allotment {
    private String allotmentID;
    private String academicYear;
    private String courseCode;
    private String staffID;

    public Allotment(String allotmentID, String academicYear, String courseCode, String staffID) {
        this.allotmentID = allotmentID;
        this.academicYear = academicYear;
        this.courseCode = courseCode;
	    this.staffID = staffID;
    }

	// Getters and setters
    public String getAllotmentID() {
        return allotmentID;
    }

    public void setAllotmentID(String allotmentID) {
        this.allotmentID = allotmentID;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

}
