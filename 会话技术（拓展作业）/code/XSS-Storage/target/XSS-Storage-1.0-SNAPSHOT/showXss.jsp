<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-XSS-Protection" content="0">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>显示存储的XSS</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .container {
      background-color: #ffffff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      max-width: 400px;
      width: 100%;
      text-align: center;
    }

    h2 {
      color: #333333;
      margin-bottom: 20px;
      font-size: 24px;
    }

    .data-display {
      background-color: #f8f9fa;
      padding: 15px;
      border-radius: 4px;
      border: 1px solid #ced4da;
      font-size: 18px;
      color: #495057;
    }

    .footer {
      margin-top: 20px;
      font-size: 14px;
      color: #888888;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>存储的数据：</h2>
  <div class="data-display">
    <%
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");  // 加载MySQL驱动
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");  // 数据库连接

        // 查询xss表中的所有记录
        String sql = "SELECT id, name FROM xss";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // 显示查询结果
        while (rs.next()) {
          // 直接输出内容，避免HTML转义
          out.print("ID: " + rs.getInt("id") + "<br>");
          out.print("Name: " + rs.getString("name") + "<br><br>");  // 输出插入的 XSS 数据
        }

        conn.close();  // 关闭数据库连接
      } catch (Exception e) {
        e.printStackTrace();
      }
    %>
  </div>



  <div class="footer">
    <p>这是一个展示存储型XSS的数据页面</p>
  </div>
</div>
</body>
</html>
