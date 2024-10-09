package org.example.listener_demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TestServlet 是一个简单的测试 Servlet，用于验证请求日志记录功能。
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 处理 GET 请求，返回简单的响应信息。
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @throws ServletException 如果请求处理失败
     * @throws IOException      如果发生 I/O 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 返回一个简单的响应，用于确认日志功能
        response.getWriter().println("GET 请求已成功记录。");
    }

    /**
     * 处理 POST 请求，返回简单的响应信息。
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @throws ServletException 如果请求处理失败
     * @throws IOException      如果发生 I/O 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 返回一个简单的响应，用于确认日志功能
        response.getWriter().println("POST 请求已成功记录。");
    }
}
