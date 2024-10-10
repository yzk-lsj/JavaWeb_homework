<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(120deg, #84fab0, #8fd3f4);
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }
        .login-container h2 {
            font-size: 24px;
            margin-bottom: 30px;
            color: #333;
        }
        .login-container label {
            font-size: 14px;
            color: #666;
            display: block;
            margin-bottom: 10px;
            text-align: left;
        }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 16px;
        }
        .login-container input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .login-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        .login-container .error {
            color: red;
            margin-bottom: 20px;
        }
        .login-container p {
            font-size: 14px;
            color: #666;
        }
        .login-container a {
            color: #4CAF50;
            text-decoration: none;
        }
        .login-container a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login to Your Account</h2>
    <form action="login" method="post">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Login">
    </form>

    <!-- 显示错误信息 -->
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <p>Don't have an account? <a href="register.jsp">Register here</a></p>
</div>
</body>
</html>
