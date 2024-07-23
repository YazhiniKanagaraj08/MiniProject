package com.servlet.course;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/UpdateCourseServlet")
public class UpdateCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String courseCode = request.getParameter("courseCode");
        String courseTitle = request.getParameter("courseTitle");
        String type = request.getParameter("type");
        String creditsStr = request.getParameter("credits");
        String syllabus = request.getParameter("syllabus");

        // Validate and parse credits to an integer
        int credits = 0;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (NumberFormatException e) {
            // Handle invalid credits input
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            
            // Prepare SQL update query
            String query = "UPDATE courses SET courseTitle=?, type=?, credits=?, syllabus=? WHERE courseCode=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseTitle);
            stmt.setString(2, type);
            stmt.setInt(3, credits);
            stmt.setString(4, syllabus);
            stmt.setString(5, courseCode);

            // Execute update query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update successful
                // You may choose to send a success response or redirect to a confirmation page
                response.sendRedirect("course.jsp");
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
