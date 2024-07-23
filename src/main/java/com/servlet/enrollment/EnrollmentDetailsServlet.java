package com.servlet.enrollment;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/EnrollmentDetailsServlet")
public class EnrollmentDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set up the connection and statement objects
        Connection conn = null;
        Statement stmt = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            stmt = conn.createStatement();

            // Retrieve existing enrollment records from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM enrollment");

            // Generate HTML content for the table rows
            StringBuilder tableRows = new StringBuilder();
            while (rs.next()) {
                tableRows.append("<tr>");
                tableRows.append("<td>").append(rs.getString("enrollmentID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("academicYear")).append("</td>");
                tableRows.append("<td>").append(rs.getString("courseCode")).append("</td>");
                tableRows.append("<td>").append(rs.getString("staffID")).append("</td>");

                // Add Assign button in the actions column
                tableRows.append("<td>");
                //tableRows.append("<button onclick=\"openPopup('update', '" + rs.getString("enrollmentID") + "', '" + rs.getString("academicYear") + "', '" + rs.getString("courseCode") + "', '" + rs.getString("staffID") + "')\">Update</button>");
                tableRows.append("<button onclick=\"assignStaff('" + rs.getString("enrollmentID") + "', '" + rs.getString("academicYear") + "', '" + rs.getString("courseCode") + "')\">Assign</button>");
                tableRows.append("<button onclick=\"deleteEnrollment('" + rs.getString("enrollmentID") + "')\">Delete</button>");
                tableRows.append("</td>");
                
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
            out.println("<th>Actions</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            // Append the table rows generated from the database records
            out.println(tableRows.toString());
            out.println("</tbody>");
            out.println("</table>");
            // Include JavaScript code
            out.println("<script src=\"enrollmentScript.js\"></script>");
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
}
