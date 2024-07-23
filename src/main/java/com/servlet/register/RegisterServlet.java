package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String db_username = "root";
            String db_password = "yazhiniopk_2002";
            String host = "jdbc:mysql://localhost:3306/staffquest";

            String username = request.getParameter("Username");
            String password = request.getParameter("Password");
            String userType = request.getParameter("UserType"); // Get the selected user type

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection(host, db_username, db_password);
            PreparedStatement ps = cn.prepareStatement("INSERT INTO user(Username, Password, UserType) VALUES(?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, userType);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("index.jsp");
            } else {
                out.println("Failed to register user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
