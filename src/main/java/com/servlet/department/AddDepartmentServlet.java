package com.servlet.department;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/AddDepartmentServlet")
public class AddDepartmentServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departmentID = request.getParameter("departmentID");
        String departmentName = request.getParameter("departmentName");
        String hod = request.getParameter("hod");

        Connection conn = null;
        Statement stmt = null;
        try {
            // Connect to your database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Execute SQL query to insert new department
            stmt = conn.createStatement();
            String sql = "INSERT INTO department (departmentID, departmentName, hod) VALUES ('" + departmentID + "', '" + departmentName + "', '" + hod + "')";
            stmt.executeUpdate(sql);

            // Retrieve the newly added department from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM department WHERE departmentID='" + departmentID + "'");
            if (rs.next()) {
                // Construct HTML for the new course row
                String newRowHtml = "<tr>";
                newRowHtml += "<td>" + rs.getString("departmentID") + "</td>";
                newRowHtml += "<td>" + rs.getString("departmentName") + "</td>";
                newRowHtml += "<td>" + rs.getString("hod") + "</td>";
                newRowHtml += "<td><button onclick=\"openPopup('update','" + rs.getString("departmentID") + "' ,'" + rs.getString("departmentName") + "' , '" + rs.getString("hod") + "')\">Update</button></td>";
                newRowHtml += "<td><button onclick=\"deleteDepartment('" + rs.getString("departmentID") + "')\">Delete</button></td>";
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