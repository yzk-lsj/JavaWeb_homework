package org.example.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 转发到注册页面
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 检查用户名和密码是否非空
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            try {
                // 检查用户名是否已存在
                Connection conn = DBConnection.getConnection();
                String checkSql = "SELECT * FROM users WHERE username = ?";
                PreparedStatement checkStatement = conn.prepareStatement(checkSql);
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // 用户名已存在
                    request.setAttribute("error", "Username already exists.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                } else {
                    // 插入新用户到数据库
                    String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
                    PreparedStatement insertStatement = conn.prepareStatement(insertSql);
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, password);
                    insertStatement.executeUpdate();

                    conn.close();

                    // 注册成功，重定向到登录页面
                    response.sendRedirect("login.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Registration failed, please try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } else {
            // 输入不能为空
            request.setAttribute("error", "Username and password cannot be empty.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
