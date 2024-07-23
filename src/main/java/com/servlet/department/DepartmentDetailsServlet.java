package com.servlet.department;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/DepartmentDetailsServlet")
public class DepartmentDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set up the connection and statement objects
        Connection conn = null;
        Statement stmt = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            stmt = conn.createStatement();

            // Retrieve existing course records from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM department");

            // Generate HTML content for the table rows
            StringBuilder tableRows = new StringBuilder();
            while (rs.next()) {
                tableRows.append("<tr>");
                tableRows.append("<td>").append(rs.getString("departmentID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("departmentName")).append("</td>");
                tableRows.append("<td>").append(rs.getString("hod")).append("</td>");
                tableRows.append("<td><button onclick=\"openPopup('update', '" + rs.getString("departmentID") + "', '" + rs.getString("departmentName") + "', '" + rs.getString("hod") + "')\">Update</button></td>");
                tableRows.append("<td><button onclick=\"deleteDepartment('" + rs.getString("departmentID") + "')\">Delete</button></td>");
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
            out.println("<title>Department Details</title>");
            // Include CSS styles
            out.println("<link rel=\"stylesheet\" href=\"DepartmentStyles.css\" />");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<table id=\"departmentTable\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Department ID</th>");
            out.println("<th>Department Name</th>");
            out.println("<th>HOD</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            // Append the table rows generated from the database records
            out.println(tableRows.toString());
            out.println("</tbody>");
            out.println("</table>");
            // Include JavaScript code
            out.println("<script src=\"departmentScript.js\"></script>");
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
