package org.example.xssreflect;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/xssServlet")
public class XSSServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 获取用户输入
        String name = request.getParameter("name");

        // 输出内容到页面
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 不安全的直接输出，存在XSS漏洞
        out.println("<html><body>");
        out.println("您输入的名字是：" + name);
        out.println("</body></html>");
    }
}
