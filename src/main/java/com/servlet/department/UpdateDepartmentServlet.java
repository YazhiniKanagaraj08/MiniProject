package com.servlet.department;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/UpdateDepartmentServlet")
public class UpdateDepartmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String departmentID = request.getParameter("departmentID");
        String departmentName = request.getParameter("departmentName");
        String hod = request.getParameter("hod");
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            
            // Prepare SQL update query
            String query = "UPDATE department SET departmentName=?, hod=? WHERE departmentID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, departmentName);
            stmt.setString(2, hod);
            stmt.setString(3, departmentID);

            // Execute update query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update successful
                // You may choose to send a success response or redirect to a confirmation page
                response.sendRedirect("department.jsp");
            } else {
                // No rows updated, handle error or send failure response
                // For simplicity, we're redirecting to an error page
                response.sendRedirect("department.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception, maybe redirect to an error page
            response.sendRedirect("department.jsp");
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
