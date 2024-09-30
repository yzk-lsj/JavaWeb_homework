package org.example.xssdom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/action3")
public class Action3Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name"); // 获取用户提交的输入

        // 设置响应的内容类型和编码
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 输出 HTML 和脚本，模拟 DOM 型 XSS 漏洞
        out.println("<html>");
        out.println("<head>");
        out.println("<title>DOM型XSS结果</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }");
        out.println(".container { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); text-align: center; }");
        out.println("h2 { color: #333333; font-size: 24px; margin-bottom: 20px; }");
        out.println("input[type='text'] { padding: 10px; width: 90%; font-size: 18px; margin-bottom: 20px; border-radius: 5px; border: 1px solid #ccc; }");
        out.println("input[type='submit'] { background-color: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #218838; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>输入的结果：</h2>");
        out.println("<input id='text' type='text' value=\"" + name + "\" />"); // 显示用户输入
        out.println("<div id='print'></div>");
        out.println("<script type='text/javascript'>");
        out.println("var text = document.getElementById('text');");
        out.println("var print = document.getElementById('print');");
        out.println("print.innerHTML = text.value;"); // 模拟 XSS 攻击
        out.println("if (text.value.includes('<script>')) {"); // 检查输入中是否包含 <script>
        out.println("  var script = document.createElement('script');"); // 创建新的 <script> 元素
        out.println("  script.text = text.value;"); // 将用户输入的内容赋值给 script
        out.println("  document.body.appendChild(script);"); // 将脚本插入到 DOM 中并执行
        out.println("}");
        out.println("</script>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
