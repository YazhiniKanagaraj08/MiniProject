package com.servlet.allotment;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CourseCodeServletAllotment")
public class CourseCodeServletAllotment extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String academicYear = request.getParameter("academicYear");
        // Assuming you have a database connection already set up
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load your JDBC driver and establish a connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffquest", "root", "yazhiniopk_2002");

            String sql = "SELECT courseCode FROM courses WHERE courseCode NOT IN (SELECT courseCode FROM allotment WHERE academicYear = ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, academicYear); // Set the academic year value
            rs = stmt.executeQuery();

            // Store course codes in an ArrayList
            ArrayList<String> courseCodes = new ArrayList<>();
            while (rs.next()) {
                courseCodes.add(rs.getString("courseCode"));
            }

            // Generate the HTML dropdown options based on retrieved course codes
            StringBuilder options = new StringBuilder();
            for (String code : courseCodes) {
                options.append("<option value=\"").append(code).append("\">").append(code).append("</option>");
            }

            // Send the dropdown options back to the client
            out.println(options.toString());

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