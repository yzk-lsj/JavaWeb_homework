package org.example.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 转发到登录页面
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 检查用户名和密码是否非空
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            try {
                // 验证用户是否在数据库中
                Connection conn = DBConnection.getConnection();
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // 用户存在，登录成功
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username);

                    // 设置 session 在 30 分钟后过期
                    session.setMaxInactiveInterval(30 * 60);

                    // 重定向到欢迎页面
                    response.sendRedirect("welcome.jsp");
                } else {
                    // 登录失败，用户名或密码错误
                    request.setAttribute("error", "Invalid username or password.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Login failed, please try again.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            // 输入不能为空
            request.setAttribute("error", "Username and password cannot be empty.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
