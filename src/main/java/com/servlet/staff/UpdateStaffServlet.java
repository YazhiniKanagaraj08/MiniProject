package com.servlet.staff;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/UpdateStaffServlet")
public class UpdateStaffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
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
        PreparedStatement stmt = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            
            // Prepare SQL update query
            String query = "UPDATE staff SET staffName=?, designation=?, departmentID=?, specialization1=?, specialization2=?, specialization3=?, specialization4=?, emailID=?, contactNumber=? WHERE staffID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, staffName);
            stmt.setString(2, designation);
            stmt.setString(3, departmentID);
            stmt.setString(4, specialization1);
            stmt.setString(5, specialization2);
            stmt.setString(6, specialization3);
            stmt.setString(7, specialization4);
            stmt.setString(8, emailID);
            stmt.setString(9, contactNumber);
            stmt.setString(10, staffID);

            // Execute update query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update successful
                response.sendRedirect("staff.jsp");
            } else {
                // No rows updated, handle error or send failure response
                // For simplicity, we're redirecting to an error page
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception, maybe redirect to an error page
            response.sendRedirect("error.jsp");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
