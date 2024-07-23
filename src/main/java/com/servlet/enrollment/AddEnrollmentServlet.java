package com.servlet.enrollment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
//import java.util.Base64;

@WebServlet("/AddEnrollmentServlet")
public class AddEnrollmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enrollmentID = request.getParameter("enrollmentID");
        String academicYear = request.getParameter("academicYear");
        String courseCode = request.getParameter("courseCode");

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Prepare SQL statement
            String sql = "INSERT INTO enrollment (enrollmentID, academicYear, courseCode) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, enrollmentID);
            pstmt.setString(2, academicYear);
            pstmt.setString(3, courseCode);

            // Execute the prepared statement to insert the enrollment
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Construct HTML for the new enrollment row
                String newRowHtml = "<tr>";
                newRowHtml += "<td>" + enrollmentID + "</td>";
                newRowHtml += "<td>" + academicYear + "</td>";
                newRowHtml += "<td>" + courseCode + "</td>";
                //newRowHtml += "<td><button onclick=\"openPopup('update','" + enrollmentID + "' ,'" + academicYear + "' , '" + courseCode + "')\">Update</button></td>";
                newRowHtml += "<td><button onclick=\"assignStaff('" + enrollmentID + "', '" + academicYear + "', '" + courseCode +"')\">Assign</button>";
                newRowHtml += "<td><button onclick=\"deleteEnrollment('" + enrollmentID + "')\">Delete</button></td>";
                newRowHtml += "</tr>";

                // Send the new enrollment row HTML back to the client
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println(newRowHtml);
            }

            pstmt.close(); // Close the PreparedStatement
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
