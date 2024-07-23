package com.servlet.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/staffLatestEnrollmentDetails")
public class StaffLatestEnrollmentDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/staffquest";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "yazhiniopk_2002";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Get username from session
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            // Query to get the latest allotment details for the logged-in staff
            String sql = "SELECT * FROM enrollment WHERE staffID = (SELECT staffID FROM user WHERE Username = ?) " +
                    "AND academicYear = (SELECT MAX(academicYear) FROM enrollment WHERE staffID = ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username); // Set the username for the first parameter
            pstmt.setString(2, username); // Set the username again for the second parameter

            rs = pstmt.executeQuery();

            // Display the enrollment table details
            out.println("<html><head><title>Enrollment Details</title></head><body>");
            out.println("<h1>Latest Enrollment Details</h1>");
            out.println("<table border='1'><tr><th>Enrollment ID</th><th>Academic Year</th><th>Course Code</th><th>Staff ID</th></tr>");
            if (rs.next()) { 
                out.println("<tr><td>" + rs.getString("enrollmentID") + "</td><td>" + rs.getString("academicYear")
                        + "</td><td>" + rs.getString("courseCode") + "</td><td>" + rs.getString("staffID") + "</td></tr>");
            } else {
                out.println("<tr><td colspan='4'>No enrollment found for the logged-in staff.</td></tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
