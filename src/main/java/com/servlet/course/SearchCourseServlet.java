package com.servlet.course;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchCourseServlet")
public class SearchCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve search query parameters from the request
        String courseCode = request.getParameter("courseCode");
        String courseTitle = request.getParameter("courseTitle");
        String type = request.getParameter("type");
        String credits = request.getParameter("credits");

        // Perform search operation based on the provided parameters
        List<Course> courses = getCoursesFromDatabase(courseCode, courseTitle, type, credits);

        // Prepare the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output search results as HTML table rows
        for (Course course : courses) {
            out.println("<tr>");
            out.println("<td>" + course.getCourseCode() + "</td>");
            out.println("<td>" + course.getCourseTitle() + "</td>");
            out.println("<td>" + course.getType() + "</td>");
            out.println("<td>" + course.getCredits() + "</td>");
            out.println("</tr>");
        }
        out.close();
    }

    // Dummy method to retrieve courses from a database
    private List<Course> getCoursesFromDatabase(String courseCode, String courseTitle, String type, String credits) {
        List<Course> courses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            String query = "SELECT * FROM courses WHERE courseCode LIKE ? OR courseTitle LIKE ? OR type LIKE ? OR credits LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + (courseCode != null ? courseCode : "") + "%");
            stmt.setString(2, "%" + (courseTitle != null ? courseTitle : "") + "%");
            stmt.setString(3, "%" + (type != null ? type : "") + "%");
            stmt.setString(4, "%" + (credits != null ? credits : "") + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String code = rs.getString("courseCode");
                String title = rs.getString("courseTitle");
                String courseType = rs.getString("type");
                int courseCredits = rs.getInt("credits");
                // Create Course object and add to the list
                Course course = new Course(code, title, courseType, courseCredits);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle this more gracefully in production
        } finally {
            // Close resources in finally block
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courses;
    }
}
