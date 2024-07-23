package com.servlet.enrollment;
public class Enrollment {
    private String enrollmentID;
    private String academicYear;
    private String courseCode;
    private String staffID;

    public Enrollment(String enrollmentID, String academicYear, String courseCode, String staffID) {
        this.enrollmentID = enrollmentID;
        this.academicYear = academicYear;
        this.courseCode = courseCode;
	    this.staffID = staffID;
    }

	// Getters and setters
    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
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
