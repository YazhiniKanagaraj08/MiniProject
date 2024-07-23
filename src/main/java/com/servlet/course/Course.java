package com.servlet.course;

public class Course {
    private String courseCode;
    private String courseTitle;
    private String type;
    private int credits;
    private String syllabus;

    public Course(String courseCode, String courseTitle, String type, int credits) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.type = type;
        this.credits = credits;
    }

    // Getters and setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}

