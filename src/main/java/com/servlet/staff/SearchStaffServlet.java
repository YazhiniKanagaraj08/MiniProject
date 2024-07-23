package com.servlet.staff;
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

@WebServlet("/SearchStaffServlet")
public class SearchStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String staffID = request.getParameter("staffID");
        String staffName = request.getParameter("staffName");
        String designation = request.getParameter("designation");
        String departmentID = request.getParameter("departmentID");
        String specialization1 = request.getParameter("specialization1");
        String specialization2 = request.getParameter("specialization2");
        String specialization3 = request.getParameter("specialization3");
        String specialization4 = request.getParameter("specialization4");
        String emailID = request.getParameter("emailID");
        String contactNumber = request.getParameter("contactNumber");

        // Perform search operation based on the provided parameters
        List<Staff> staffs = getStaffsFromDatabase(staffID, staffName, designation, departmentID, specialization1, specialization2, specialization3, specialization4, emailID, contactNumber);

        // Prepare the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output search results as HTML table rows
        for (Staff staff : staffs) {
            out.println("<tr>");
            out.println("<td>" + staff.getStaffID() + "</td>");
            out.println("<td>" + staff.getStaffName() + "</td>");
            out.println("<td>" + staff.getDesignation() + "</td>");
            out.println("<td>" + staff.getDepartmentID() + "</td>");
            out.println("<td>" + staff.getSpecialization1() + "</td>");
            out.println("<td>" + staff.getSpecialization2() + "</td>");
            out.println("<td>" + staff.getSpecialization3() + "</td>");
            out.println("<td>" + staff.getSpecialization4() + "</td>");
            out.println("<td>" + staff.getEmailID() + "</td>");
            out.println("<td>" + staff.getContactNumber() + "</td>");
            out.println("</tr>");
        }
        out.close();
    }

    // Dummy method to retrieve courses from a database
    private List<Staff> getStaffsFromDatabase(String staffID, String staffName, String designation, String departmentID, String specialization1, String specialization2, String specialization3, String specialization4, String emailID, String contactNumber) {
        List<Staff> staffs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            String query = "SELECT * FROM staff WHERE staffID LIKE ? OR staffName LIKE ? OR designation LIKE ? OR departmentID LIKE ? OR specialization1 LIKE ? OR specialization2 LIKE ? OR specialization3 LIKE ? OR specialization4 LIKE ? OR emailID LIKE ? OR contactNumber LIKE ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + (staffID != null ? staffID : "") + "%");
            stmt.setString(2, "%" + (staffName != null ? staffName : "") + "%");
            stmt.setString(3, "%" + (designation != null ? designation : "") + "%");
            stmt.setString(4, "%" + (departmentID != null ? departmentID : "") + "%");
            stmt.setString(5, "%" + (specialization1 != null ? specialization1 : "") + "%");
            stmt.setString(6, "%" + (specialization2 != null ? specialization2 : "") + "%");
            stmt.setString(7, "%" + (specialization3 != null ? specialization3 : "") + "%");
            stmt.setString(8, "%" + (specialization4 != null ? specialization4 : "") + "%");
            stmt.setString(9, "%" + (emailID != null ? emailID : "") + "%");
            stmt.setString(10, "%" + (contactNumber != null ? contactNumber : "") + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String staffID1 = rs.getString("staffID");
                String staffName1 = rs.getString("staffName");
                String designation1 = rs.getString("designation");
                String departmentID1 = rs.getString("departmentID");
                String specialization11 = rs.getString("specialization1");
                String specialization21 = rs.getString("specialization2");
                String specialization31 = rs.getString("specialization3");
                String specialization41 = rs.getString("specialization4");
                String emailID1 = rs.getString("emailID");
                String contactNumber1 = rs.getString("contactNumber");
                // Create Course object and add to the list
                Staff staff = new Staff(staffID1, staffName1, designation1, departmentID1, specialization11, specialization21, specialization31, specialization41, emailID1, contactNumber1);

                staffs.add(staff);
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

        return staffs;
    }
}