package com.servlet.staff;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/AddStaffServlet")
public class AddStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffID = request.getParameter("staffID");
        String staffName = request.getParameter("staffName");
        String designation = request.getParameter("designation");
        String departmentID = request.getParameter("departmentID");
        String specialization1 = request.getParameter("specialization1");
        String specialization2 = request.getParameter("specialization2");
        String specialization3 = request.getParameter("specialization3");
        String specialization4 = request.getParameter("specialization4");
        String emailID = request.getParameter("emailID");
        String contactNumber = request.getParameter("contactNumber");

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");


            // Execute SQL query to insert new staff
            stmt = conn.createStatement();
            String sql = "INSERT INTO staff (staffID, staffName, designation, departmentID, specialization1, specialization2, specialization3, specialization4, emailID, contactNumber) VALUES ('" + staffID + "', '" + staffName + "', '" + designation + "', '" + departmentID + "', '" + specialization1 + "', '" + specialization2 + "', '" + specialization3 + "', '" + specialization4 + "', '" + emailID + "', '" + contactNumber + "')";
            stmt.executeUpdate(sql);

            // Retrieve the newly added staff from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff WHERE staffID='" + staffID + "'");
            if (rs.next()) {
                // Construct HTML for the new course row
                String newRowHtml = "<tr>";
                newRowHtml += "<td>" + rs.getString("staffID") + "</td>";
                newRowHtml += "<td>" + rs.getString("staffName") + "</td>";
                newRowHtml += "<td>" + rs.getString("designation") + "</td>";
                newRowHtml += "<td>" + rs.getString("departmentID") + "</td>";
                newRowHtml += "<td>" + rs.getString("specialization1") + "</td>";
                newRowHtml += "<td>" + rs.getString("specialization2") + "</td>";
                newRowHtml += "<td>" + rs.getString("specialization3") + "</td>";
                newRowHtml += "<td>" + rs.getString("specialization4") + "</td>";
                newRowHtml += "<td>" + rs.getString("emailID") + "</td>";
                newRowHtml += "<td>" + rs.getString("contactNumber") + "</td>";
                newRowHtml += "<td><button onclick=\"openPopup('update','" + rs.getString("staffID") + "' ,'" + rs.getString("staffName") + "' , '" + rs.getString("designation") + "', '" + rs.getString("departmentID") + "', '" + rs.getString("specialization1") + "', '" + rs.getString("specialization2") + "', '" + rs.getString("specialization3") + "', '" + rs.getString("specialization4") + "', '" + rs.getString("emailID") + "', '" + rs.getString("contactNumber") + "')\">Update</button></td>";
                newRowHtml += "<td><button onclick=\"deleteCourse('" + rs.getString("staffID") + "')\">Delete</button></td>";
                newRowHtml += "</tr>";

                // Send the new staff row HTML back to the client
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println(newRowHtml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}