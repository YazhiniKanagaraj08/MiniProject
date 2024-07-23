package com.servlet.enrollment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet("/StaffAssignServlet")
public class StaffAssignServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static int lastAssignedIndex = -1; // Track the last assigned index for round-robin allocation

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enrollmentID = request.getParameter("enrollmentID");
        String academicYear = request.getParameter("academicYear");
        String courseCode = request.getParameter("courseCode");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Retrieve course title from courses table based on the course code
            String courseTitle = getCourseTitle(conn, courseCode);

            // Check if the course title is not null or empty
            if (courseTitle != null && !courseTitle.isEmpty()) {
                // Retrieve staff IDs from staff table based on course title and academic year
                ArrayList<String> staffIDs = getStaffIDs(conn, courseTitle, academicYear);

                // Check if there are staff IDs available for assignment
                if (!staffIDs.isEmpty()) {
                    // Retrieve the previously assigned staff for the current year and course code
                    String previousStaffID = getPreviousStaffID(conn, enrollmentID, academicYear, courseCode);

                    // Get the next staff ID in the round-robin fashion
                    String newStaffID = getNextStaffID(staffIDs, previousStaffID);

                    // Check if a valid staff ID is obtained
                    if (newStaffID != null) {
                        // Assign the next staff ID in the round-robin fashion
                        assignStaffToEnrollment(conn, enrollmentID, newStaffID);
                        System.out.println("Assigned staff ID: " + newStaffID); // Debugging statement

                        // Update the last assigned index for round-robin allocation
                        lastAssignedIndex = staffIDs.indexOf(newStaffID);
                    } else {
                        System.out.println("No suitable staff ID found!"); // Debugging statement
                    }

                    // Construct HTML response indicating successful assignment or error
                    String responseMessage = newStaffID != null ? "Staff assigned successfully!" : "Error assigning staff!";
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println(responseMessage);
                } else {
                    // Construct HTML response if no staff is available for assignment
                    String noStaffResponse = "No staff available for assignment!";
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println(noStaffResponse);
                }
            } else {
                // Construct HTML response if course title is not found
                String courseNotFoundResponse = "Course title not found for the provided course code!";
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println(courseNotFoundResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Helper method to retrieve course title from courses table based on course code
    private String getCourseTitle(Connection conn, String courseCode) throws SQLException {
        String courseTitle = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT courseTitle FROM courses WHERE courseCode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                courseTitle = rs.getString("courseTitle");
                System.out.println("Course title retrieved: " + courseTitle); // Debugging statement
            } else {
                System.out.println("No course title found for course code: " + courseCode); // Debugging statement for no result
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return courseTitle;
    }

    // Helper method to retrieve staff IDs from staff table based on course title and academic year
    private ArrayList<String> getStaffIDs(Connection conn, String courseTitle, String academicYear) throws SQLException {
        ArrayList<String> staffIDs = new ArrayList<>();
        ArrayList<String> staffIDs1 = new ArrayList<>();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        int acaYear = Integer.parseInt(academicYear)-1;

        try {
            String sql = "SELECT staffID FROM staff WHERE (specialization1 = ? OR specialization2 = ? OR specialization3 = ? OR specialization4 = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseTitle);
            pstmt.setString(2, courseTitle);
            pstmt.setString(3, courseTitle);
            pstmt.setString(4, courseTitle);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                staffIDs.add(rs.getString("staffID"));
            }
            String listString = staffIDs.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", "));
            String sql1 = "SELECT staffID FROM enrollment WHERE academicYear = ? AND staffID in ("+listString+")";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1,String.valueOf(acaYear));

            rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                staffIDs1.add(rs1.getString("staffID"));
            }
            staffIDs.removeAll(staffIDs1);
            System.out.println("Staff IDs retrieved from staff table: " + staffIDs); // Debugging statement
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return staffIDs;
    }

    // Helper method to retrieve previous staff ID assigned for the current year and course code
    private String getPreviousStaffID(Connection conn, String enrollmentID, String academicYear, String courseCode) throws SQLException {
        String previousStaffID = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT staffID FROM enrollment WHERE enrollmentID = ? AND academicYear = ? AND courseCode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, enrollmentID);
            pstmt.setString(2, academicYear);
            pstmt.setString(3, courseCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                previousStaffID = rs.getString("staffID");
                System.out.println("Previous staff ID retrieved: " + previousStaffID); // Debugging statement
            } else {
                System.out.println("No previous staff ID found for enrollment ID: " + enrollmentID); // Debugging statement for no result
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return previousStaffID;
    }

    // Helper method to retrieve the next staff ID in round-robin fashion
    private String getNextStaffID(ArrayList<String> staffIDs, String previousStaffID) {
        if (staffIDs.isEmpty()) {
            return null; // Return null if no staff IDs are available
        }

        int currentIndex = lastAssignedIndex;
        int nextIndex = (currentIndex + 1) % staffIDs.size();

        // Handle edge case where the previousStaffID is not found in staffIDs
        if (!staffIDs.contains(previousStaffID)) {
            lastAssignedIndex = -1;
        }

        // Iterate until we find a valid next staff ID
        while (nextIndex != lastAssignedIndex) {
            String nextStaffID = staffIDs.get(nextIndex);
            if (!nextStaffID.equals(previousStaffID)) {
                lastAssignedIndex = nextIndex;
                return nextStaffID;
            }
            nextIndex = (nextIndex + 1) % staffIDs.size();
        }

        return null; // Return null if no suitable staff ID is found
    }

    // Helper method to assign staff to enrollment
    private void assignStaffToEnrollment(Connection conn, String enrollmentID, String newStaffID) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE enrollment SET staffID = ? WHERE enrollmentID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStaffID);
            pstmt.setString(2, enrollmentID);
            pstmt.executeUpdate();

            System.out.println("Staff assigned successfully!"); // Debugging statement
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
}
