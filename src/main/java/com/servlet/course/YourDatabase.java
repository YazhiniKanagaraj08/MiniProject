package com.servlet.course;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YourDatabase {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/staffquest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yazhiniopk_2002";

    public static List<Course> getCourses(String courseCode, String courseTitle, String type, String credits) {
        List<Course> courses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String query = "SELECT * FROM courses WHERE courseCode LIKE ? AND courseTitle LIKE ? AND type LIKE ? AND credits LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + courseCode + "%");
            stmt.setString(2, "%" + courseTitle + "%");
            stmt.setString(3, "%" + type + "%");
            stmt.setString(4, "%" + credits + "%");
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
            e.printStackTrace();
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
