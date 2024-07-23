package com.servlet.staff;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/DeleteStaffServlet")
public class DeleteStaffServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffID = request.getParameter("staffID");

        Connection conn = null;
        Statement stmt = null;
        try {
            // Connect to your database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Execute SQL query to delete the staff
            stmt = conn.createStatement();
            String sql = "DELETE FROM staff WHERE staffID='" + staffID + "'";
            int rowsAffected = stmt.executeUpdate(sql);

            // Send appropriate response back to the client
            if (rowsAffected > 0) {
                // Deletion successful
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // No rows were affected, i.e., staff not found
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // Send error response back to client
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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