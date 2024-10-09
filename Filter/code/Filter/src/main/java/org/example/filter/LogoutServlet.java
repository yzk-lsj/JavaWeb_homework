package org.example.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LogoutServlet 处理用户的登出请求，销毁用户的会话并重定向到登出成功页面。
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String username = (String) session.getAttribute("user");
            LocalDateTime logoutTime = LocalDateTime.now();

            // 记录用户登出时间
            System.out.println("User " + username + " logged out at " + logoutTime);

            session.invalidate(); // 使 session 失效
        }

        // 确保重定向到正确的登出成功页面路径
        // 改用 forward 来渲染 JSP 页面
        request.getRequestDispatcher("/logout_success.jsp").forward(request, response);
   }
}
