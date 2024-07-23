package com.servlet.allotment;

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
@WebServlet("/ViewAllotmentDetailsServlet")
public class ViewAllotmentDetailsServlet extends HttpServlet {

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

            // Prepare SQL statement to retrieve all allotment details with staffName
            String sql = "SELECT a.academicYear, a.staffID, s.staffName, COUNT(*) AS num_allotments " +
                         "FROM allotment a " +
                         "INNER JOIN staff s ON a.staffID = s.staffID " +
                         "GROUP BY a.academicYear, a.staffID";  // Group by academicYear and staffID to get unique combinations

            pstmt = conn.prepareStatement(sql);

            // Execute the query
            rs = pstmt.executeQuery();

            // Construct HTML response for allotment details
            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<table border=\"1\"><tr><th>Academic Year</th><th>Staff ID</th><th>Staff Name</th><th>Number of Allotments</th></tr>");

            // Process the result set and add rows to the HTML response
            while (rs.next()) {
                String academicYear = rs.getString("academicYear");
                String staffID = rs.getString("staffID");
                String staffName = rs.getString("staffName");
                int numAllotments = rs.getInt("num_allotments");

                htmlResponse.append("<tr>");
                htmlResponse.append("<td>").append(academicYear).append("</td>");
                htmlResponse.append("<td>").append(staffID).append("</td>");
                htmlResponse.append("<td>").append(staffName).append("</td>");
                htmlResponse.append("<td>").append(numAllotments).append("</td>");
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
