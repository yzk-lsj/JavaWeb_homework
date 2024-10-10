<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
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
        .register-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }
        .register-container h2 {
            font-size: 24px;
            margin-bottom: 30px;
            color: #333;
        }
        .register-container label {
            font-size: 14px;
            color: #666;
            display: block;
            margin-bottom: 10px;
            text-align: left;
        }
        .register-container input[type="text"],
        .register-container input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 16px;
        }
        .register-container input[type="submit"] {
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
        .register-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        .register-container .error {
            color: red;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Register a New Account</h2>
    <form action="register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Register">
    </form>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
</div>
</body>
</html>
