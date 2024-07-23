package com.servlet.register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String db_username = "root";
            String db_password = "yazhiniopk_2002";
            String host = "jdbc:mysql://localhost:3306/staffquest";

            String username = request.getParameter("LoginUsername");
            String password = request.getParameter("LoginPassword");
            String userType = request.getParameter("submitbtn"); // Get the type of login (admin or staff)

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection(host, db_username, db_password);

            String sql = "SELECT Username, Password, UserType FROM user WHERE Username=? AND Password=? AND UserType=?";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, userType); // Bind the user type to the query

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username); // Set the username in the session
                        if ("admin".equals(userType)) {
                            response.sendRedirect("dashboard.jsp");
                        } else if ("staff".equals(userType)) {
                            response.sendRedirect("staffDashboard.jsp");
                        }
                    } else {
                        String message = "Invalid username or password";
                        request.setAttribute("msg", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
