package com.servlet.allotment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/AddAllotmentServlet")
public class AddAllotmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String allotmentID = request.getParameter("allotmentID");
        String academicYear = request.getParameter("academicYear");
        String courseCode = request.getParameter("courseCode");

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            // Prepare SQL statement to insert allotment
            String sql = "INSERT INTO allotment (allotmentID, academicYear, courseCode) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, allotmentID);
            pstmt.setString(2, academicYear);
            pstmt.setString(3, courseCode);

            // Execute the prepared statement to insert the allotment
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Construct HTML for the new enrollment row
                String newRowHtml = "<tr>";
                newRowHtml += "<td>" + allotmentID + "</td>";
                newRowHtml += "<td>" + academicYear + "</td>";
                newRowHtml += "<td>" + courseCode + "</td>";
                newRowHtml += "<td><button onclick=\"allotStaff('" + allotmentID + "', '" + academicYear + "', '" + courseCode +"')\">Allot</button>";
                String staffID = null;
				newRowHtml += "<td><button onclick=\"mailStaff()\">Allot</button>";
                newRowHtml += "<td><button onclick=\"deleteAllotment('" + allotmentID + "')\">Delete</button></td>";
                newRowHtml += "</tr>";

                // Send the new allotment row HTML back to the client
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
