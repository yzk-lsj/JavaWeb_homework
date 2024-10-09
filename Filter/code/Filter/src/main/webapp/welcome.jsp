<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
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
        .welcome-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }
        .welcome-container h2 {
            font-size: 24px;
            margin-bottom: 30px;
            color: #333;
        }
        .welcome-container p {
            font-size: 18px;
            color: #666;
            margin-bottom: 30px;
        }
        .welcome-container a {
            display: inline-block;
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .welcome-container a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="welcome-container">
    <h2>Welcome, <%= session.getAttribute("user") %>!</h2>
    <p>We're glad to have you here. Feel free to explore and make the most of your session.</p>
    <a href="logout_confirm.jsp">Logout</a>
</div>
</body>
</html>
