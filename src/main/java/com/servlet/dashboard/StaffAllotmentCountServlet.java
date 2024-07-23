package com.servlet.dashboard;

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
import javax.servlet.http.HttpSession;

@WebServlet("/staffAllotmentCount")
public class StaffAllotmentCountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            // Get username from session
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            // Query to get the total count of allotments for the logged-in staff
            String query = "SELECT COUNT(*) AS total FROM allotment WHERE staffID = (SELECT staffID FROM user WHERE Username = ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username); // Set the username as parameter
            rs = stmt.executeQuery();

            int allotmentCount = 0;
            if (rs.next()) {
                allotmentCount = rs.getInt("total");
            }

            // Set response content type
            response.setContentType("text/html");

            // Send the allotment count as response
            PrintWriter out = response.getWriter();
            out.print(allotmentCount);
            System.out.println("Allotment count for " + username + ": " + allotmentCount);
            out.flush();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

