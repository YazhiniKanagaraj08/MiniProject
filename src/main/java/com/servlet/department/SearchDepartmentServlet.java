package com.servlet.department;
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

@WebServlet("/SearchDepartmentServlet")
public class SearchDepartmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve search query parameters from the request
        String departmentID = request.getParameter("departmentID");
        String departmentName = request.getParameter("departmentName");
        String hod = request.getParameter("hod");

        // Perform search operation based on the provided parameters
        List<Department> departments = getDepartmentsFromDatabase(departmentID, departmentName, hod);

        // Prepare the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Output search results as HTML table rows
        for (Department department : departments) {
            out.println("<tr>");
            out.println("<td>" + department.getDepartmentID() + "</td>");
            out.println("<td>" + department.getDepartmentName() + "</td>");
            out.println("<td>" + department.gethod() + "</td>");
       
            // Add more columns if needed
            out.println("</tr>");
        }
        out.close();
    }

    // Dummy method to retrieve departments from a database
    private List<Department> getDepartmentsFromDatabase(String departmentID, String departmentName, String hod) {
        List<Department> departments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");
            String query ="SELECT * FROM department WHERE departmentID LIKE ? OR departmentName LIKE ? OR hod LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + (departmentID != null ? departmentID : "") + "%");
            stmt.setString(2, "%" + (departmentName != null ? departmentName : "") + "%");
            stmt.setString(3, "%" + (hod != null ? hod : "") + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("departmentID");
                String name = rs.getString("departmentName");
                String hod1 = rs.getString("hod");
                // Create Course object and add to the list
                Department department = new Department(id, name, hod1);
                departments.add(department);
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

        return departments;
    }
}
