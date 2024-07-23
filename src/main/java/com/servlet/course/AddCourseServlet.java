package com.servlet.course;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseCode = request.getParameter("courseCode");
        String courseTitle = request.getParameter("courseTitle");
        String type = request.getParameter("type");
        String credits = request.getParameter("credits");
        String syllabus = request.getParameter("syllabus");

        Connection conn = null;
        Statement stmt = null;
        try {
            // Connect to your database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Execute SQL query to insert new course
            stmt = conn.createStatement();
            String sql = "INSERT INTO courses (courseCode, courseTitle, type, credits, syllabus) VALUES ('" + courseCode + "', '" + courseTitle + "', '" + type + "', '" + credits + "', '" + syllabus + "')";
            stmt.executeUpdate(sql);

            // Retrieve the newly added course from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE courseCode='" + courseCode + "'");
            if (rs.next()) {
                // Construct HTML for the new course row
                String newRowHtml = "<tr>";
                newRowHtml += "<td>" + rs.getString("courseCode") + "</td>";
                newRowHtml += "<td>" + rs.getString("courseTitle") + "</td>";
                newRowHtml += "<td>" + rs.getString("type") + "</td>";
                newRowHtml += "<td>" + rs.getString("credits") + "</td>";
                newRowHtml += "<td>" + rs.getString("syllabus") + "</td>";
                newRowHtml += "<td><button onclick=\"openPopup('update','" + rs.getString("courseCode") + "' ,'" + rs.getString("courseTitle") + "' , '" + rs.getString("type") + "', '" + rs.getString("credits") + "', '" + rs.getString("syllabus") + "')\">Update</button></td>";
                newRowHtml += "<td><button onclick=\"deleteCourse('" + rs.getString("courseCode") + "')\">Delete</button></td>";
                newRowHtml += "</tr>";

                // Send the new course row HTML back to the client
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