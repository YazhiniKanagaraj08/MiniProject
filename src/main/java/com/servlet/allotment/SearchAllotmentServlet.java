package com.servlet.allotment;
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

@WebServlet("/SearchAllotmentServlet")
public class SearchAllotmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String allotmentID = request.getParameter("allotmentID");
        String academicYear = request.getParameter("academicYear");
        String courseCode = request.getParameter("courseCode");
        String staffID = request.getParameter("staffID");

        // Perform search operation based on the provided parameters
        List<Allotment> allotments = getAllotmentsFromDatabase(allotmentID, academicYear, courseCode, staffID);

        // Prepare the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output search results as HTML table rows
        for (Allotment allotment : allotments) {
            out.println("<tr>");
            out.println("<td>" + allotment.getAllotmentID() + "</td>");
            out.println("<td>" + allotment.getAcademicYear() + "</td>");
	        out.println("<td>" + allotment.getCourseCode() + "</td>");
	        out.println("<td>" + allotment.getStaffID() + "</td>");
            out.println("</tr>");
        }
        out.close();
    }

    // Dummy method to retrieve enrollments from a database
    private List<Allotment> getAllotmentsFromDatabase(String allotmentID, String academicYear, String courseCode, String staffID) {
        List<Allotment> allotments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            String query = "SELECT * FROM allotment WHERE allotmentID LIKE ? OR academicYear LIKE ? OR courseCode LIKE ? OR staffID LIKE ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + (allotmentID != null ? allotmentID : "") + "%");
            stmt.setString(2, "%" + (academicYear != null ? academicYear : "") + "%");
            stmt.setString(3, "%" + (courseCode != null ? courseCode : "") + "%");
            stmt.setString(4, "%" + (staffID != null ? staffID : "") + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String allotmentID1 = rs.getString("allotmentID");
                String academicYear1 = rs.getString("academicYear");
                String courseCode1 = rs.getString("courseCode");
                String staffID1 = rs.getString("staffID");
           
                // Create Allotment object and add to the list
                Allotment allotment = new Allotment(allotmentID1, academicYear1, courseCode1, staffID1);

                allotments.add(allotment);
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

        return allotments;
    }
}