package com.servlet.enrollment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/DeleteEnrollmentServlet")
public class DeleteEnrollmentServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enrollmentID = request.getParameter("enrollmentID");

        Connection conn = null;
        Statement stmt = null;
        try {
            // Connect to your database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Execute SQL query to delete the enrollment
            stmt = conn.createStatement();
            String sql = "DELETE FROM enrollment WHERE enrollmentID='" + enrollmentID + "'";
            int rowsAffected = stmt.executeUpdate(sql);

            // Send appropriate response back to the client
            if (rowsAffected > 0) {
                // Deletion successful
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // No rows were affected, i.e., enrollment not found
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