package com.servlet.enrollment;

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

@WebServlet("/StaffEnrollmentDetailsServlet")
public class StaffEnrollmentDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set up the connection and statement objects
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Prepare the SQL query with a join between enrollment and user tables
            String sql = "SELECT e.enrollmentID, e.academicYear, e.courseCode, e.staffID FROM enrollment e " +
                         "JOIN user u ON e.staffID = u.Username WHERE u.Username = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, getUsernameFromSession(request)); // Assuming you have a method to get the username from the session

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Generate HTML content for the table rows
            StringBuilder tableRows = new StringBuilder();
            while (rs.next()) {
                tableRows.append("<tr>");
                tableRows.append("<td>").append(rs.getString("enrollmentID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("academicYear")).append("</td>");
                tableRows.append("<td>").append(rs.getString("courseCode")).append("</td>");
                tableRows.append("<td>").append(rs.getString("staffID")).append("</td>");
                tableRows.append("</tr>");
            }

            // Set the response content type to HTML
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Generate the complete HTML content for the page
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Enrollment Details</title>");
            // Include CSS styles
            out.println("<link rel=\"stylesheet\" href=\"enrollmentStyles.css\" />");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<table id=\"enrollmentTable\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Enrollment ID</th>");
            out.println("<th>Academic Year</th>");
            out.println("<th>Course Code</th>");
            out.println("<th>Staff ID</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            // Append the table rows generated from the database records
            out.println(tableRows.toString());
            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection and statement objects
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Method to get the username from the session
    private String getUsernameFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (String) session.getAttribute("username");
        }
        return null;
    }
}
