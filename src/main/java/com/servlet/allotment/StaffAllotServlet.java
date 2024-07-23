package com.servlet.allotment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet("/StaffAllotServlet")
public class StaffAllotServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static int lastAllotedIndex = -1; // Track the last assigned index for round-robin allocation

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String allotmentID = request.getParameter("allotmentID");
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
                // Retrieve staff IDs from staff table based on course title and academic year and the staffID not in Previous Year.
                ArrayList<String> staffIDs = getStaffIDs(conn, courseTitle, academicYear);

                // Check if there are staff IDs available for assignment
                if (!staffIDs.isEmpty()) {
                    // Retrieve the previously assigned staff for the current year and course code
                    String previousStaffID = getPreviousStaffID(conn, allotmentID, academicYear, courseCode);

                 // Get the next staff ID in the round-robin fashion
                    String newStaffID = getNextStaffID(conn, staffIDs, previousStaffID, academicYear, courseCode);

                    // Check if a valid staff ID is obtained
                    if (newStaffID != null) {
                        // Assign the next staff ID in the round-robin fashion
                        allotStaffToAllotment(conn, allotmentID, newStaffID);
                        System.out.println("Alloted staff ID: " + newStaffID); // Debugging statement

                        // Update the last assigned index for round-robin allocation
                        lastAllotedIndex = staffIDs.indexOf(newStaffID);
                    } else {
                        System.out.println("No suitable staff ID found!"); // Debugging statement
                    }

                    // Construct HTML response indicating successful assignment or error
                    String responseMessage = newStaffID != null ? "Staff alloted successfully!" : "Error alloting staff!";
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println(responseMessage);
                } else {
                    // Construct HTML response if no staff is available for allotment
                    String noStaffResponse = "No staff available for allotment!";
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
        ArrayList<String> staffIDs = new ArrayList<>(); //staff IDs matching the course title
        ArrayList<String> staffIDs1 = new ArrayList<>(); //staff IDs that were assigned the previous academic year
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
            String listString = staffIDs.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")); //list to string
            String sql1 = "SELECT staffID FROM allotment WHERE academicYear = ? AND staffID in ("+listString+")"; 
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1,String.valueOf(acaYear));

            rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                staffIDs1.add(rs1.getString("staffID"));
            }
            staffIDs.removeAll(staffIDs1);
            System.out.println("Staff IDs retrieved from staff table: " + staffIDs); 
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return staffIDs;
    }

    // Helper method to retrieve previous staff ID assigned based on the current year and course code
    private String getPreviousStaffID(Connection conn, String allotmentID, String academicYear, String courseCode) throws SQLException {
        String previousStaffID = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT staffID FROM allotment WHERE allotmentID = ? AND academicYear = ? AND courseCode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, allotmentID);
            pstmt.setString(2, academicYear);
            pstmt.setString(3, courseCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                previousStaffID = rs.getString("staffID");
                //System.out.println("Previous staff ID retrieved: " + previousStaffID); 
            } else {
                System.out.println("No previous staff ID found for allotment ID: " + allotmentID); 
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return previousStaffID;
    }

    // Helper method to retrieve the next staff ID considering no duplicate assignment
    private String getNextStaffID(Connection conn, ArrayList<String> staffIDs, String previousStaffID, String academicYear, String courseCode) throws SQLException {
        if (staffIDs.isEmpty()) {
            return null; 
        }

        int currentIndex = lastAllotedIndex;
        int nextIndex = (currentIndex + 1) % staffIDs.size();

         //Handle case where the previousStaffID is not found in staffIDs
        if (!staffIDs.contains(previousStaffID)) {
            lastAllotedIndex = -1;
        }

        // Iterate until we find a valid next staff ID
        while (nextIndex != lastAllotedIndex) {
            String nextStaffID = staffIDs.get(nextIndex);
            if (!isDuplicateAssignment(conn, nextStaffID, academicYear, courseCode) && !isStaffIDInEnrollment(conn, nextStaffID, academicYear, courseCode)) {
                lastAllotedIndex = nextIndex;
                return nextStaffID;
            }
            nextIndex = (nextIndex + 1) % staffIDs.size();
        }

        return null; // Return null if no suitable staff ID is found
    }

    // Helper method to check if the staff ID is already assigned in the enrollment table for the given courseCode and academicYear
    private boolean isStaffIDInEnrollment(Connection conn, String staffID, String academicYear, String courseCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM enrollment WHERE staffID = ? AND academicYear = ? AND courseCode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, staffID);
            pstmt.setString(2, academicYear);
            pstmt.setString(3, courseCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Return true if the staff ID is found in the enrollment table, false otherwise
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return false;
    }


// Helper method to check for duplicate assignment of staff
private boolean isDuplicateAssignment(Connection conn, String staffID, String academicYear, String courseCode) throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT COUNT(*) FROM allotment WHERE staffID = ? AND academicYear = ? AND courseCode = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, staffID);
        pstmt.setString(2, academicYear);
        pstmt.setString(3, courseCode);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // Return true if there is a duplicate assignment, false otherwise
        }
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
    }

    return false;
}

    // Helper method to assign staff to allotment
    private void allotStaffToAllotment(Connection conn, String allotmentID, String newStaffID) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE allotment SET staffID = ? WHERE allotmentID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStaffID);
            pstmt.setString(2, allotmentID);
            pstmt.executeUpdate();

            System.out.println("Staff alloted successfully!"); // Debugging statement
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
}
