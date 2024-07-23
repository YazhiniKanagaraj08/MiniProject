package com.servlet.course;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/ViewStaffAvailableDetailsServlet")
public class ViewStaffAvailableDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish connection to MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            String sql = "SELECT c.courseCode, c.courseTitle, COUNT(s.staffID) AS num_staffs " +
                    "FROM courses c " +
                    "INNER JOIN staff s ON (s.specialization1 = c.courseTitle OR s.specialization2 = c.courseTitle OR s.specialization3 = c.courseTitle OR s.specialization4 = c.courseTitle) " +
                    "GROUP BY c.courseCode, c.courseTitle";

            pstmt = conn.prepareStatement(sql);

            // Execute the query
            rs = pstmt.executeQuery();

            // Construct HTML response for allotment details
            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<table border=\"1\"><tr><th>Course Code</th><th>Course Title</th><th>Number of Staffs</th></tr>");

            // Process the result set and add rows to the HTML response
            while (rs.next()) {
                String courseCode = rs.getString("courseCode");
                String courseTitle = rs.getString("courseTitle");
                int numStaffs = rs.getInt("num_staffs");

                htmlResponse.append("<tr>");
                htmlResponse.append("<td>").append(courseCode).append("</td>");
                htmlResponse.append("<td>").append(courseTitle).append("</td>");
                htmlResponse.append("<td>").append(numStaffs).append("</td>");
                htmlResponse.append("</tr>");
            }

            htmlResponse.append("</table>");

            // Send the HTML response directly to the HttpServletResponse
            response.getWriter().write(htmlResponse.toString());
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle or log exceptions appropriately
        } finally {
            // Close resources in the reverse order of their creation to avoid resource leaks
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
