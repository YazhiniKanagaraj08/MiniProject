package com.servlet.staff;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/StaffDetailsServlet")
public class StaffDetailsServlet extends HttpServlet {
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff");

            // Generate HTML content for the table rows
            StringBuilder tableRows = new StringBuilder();
            while (rs.next()) {
                tableRows.append("<tr>");
                tableRows.append("<td>").append(rs.getString("staffID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("staffName")).append("</td>");
                tableRows.append("<td>").append(rs.getString("designation")).append("</td>");
                tableRows.append("<td>").append(rs.getString("departmentID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("specialization1")).append("</td>");
                tableRows.append("<td>").append(rs.getString("specialization2")).append("</td>");
                tableRows.append("<td>").append(rs.getString("specialization3")).append("</td>");
                tableRows.append("<td>").append(rs.getString("specialization4")).append("</td>");
                tableRows.append("<td>").append(rs.getString("emailID")).append("</td>");
                tableRows.append("<td>").append(rs.getString("contactNumber")).append("</td>");
                tableRows.append("<td><button onclick=\"openPopup('update', '" + rs.getString("staffID") + "', '" + rs.getString("staffName") + "', '" + rs.getString("designation") + "', '" + rs.getString("departmentID") + "', '" + rs.getString("specialization1") + "','" + rs.getString("specialization2") + "','" + rs.getString("specialization3") + "','" + rs.getString("specialization4") + "', '" + rs.getString("emailID") + "', '" + rs.getString("contactNumber") + "')\">Update</button>");
                tableRows.append("<td><button onclick=\"deleteStaff('" + rs.getString("staffID") + "')\">Delete</button>");
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
            out.println("<title>Staff Details</title>");
            // Include CSS styles
            out.println("<link rel=\"stylesheet\" href=\"staffStyles.css\" />");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<table id=\"staffTable\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Staff ID</th>");
            out.println("<th>Staff Name</th>");
            out.println("<th>Designation</th>");
            out.println("<th>Department ID</th>");
            out.println("<th>Specialization 1</th>");
            out.println("<th>Specialization 2</th>");
            out.println("<th>Specialization 3</th>");
            out.println("<th>Specialization 4</th>");
            out.println("<th>Email ID</th>");
            out.println("<th>Contact Number</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            // Append the table rows generated from the database records
            out.println(tableRows.toString());
            out.println("</tbody>");
            out.println("</table>");
            // Include JavaScript code
            out.println("<script src=\"staffScript.js\"></script>");
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
