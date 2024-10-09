<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout Confirmation</title>
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
        .confirm-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }
        .confirm-container h2 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }
        .confirm-container p {
            font-size: 18px;
            color: #666;
            margin-bottom: 30px;
        }
        .confirm-container .btn {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
            margin: 10px;
        }
        .confirm-container .btn:hover {
            background-color: #45a049;
        }
        .cancel {
            background-color: #f44336;
        }
        .cancel:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<div class="confirm-container">
    <h2>Logout Confirmation</h2>
    <p>Are you sure you want to logout?</p>
    <!-- 确认登出后提交到 LogoutServlet -->
    <form action="logout" method="get">
        <input class="btn" type="submit" value="Yes, Logout">
    </form>
    <a class="btn cancel" href="welcome.jsp">Cancel</a>
</div>
</body>
</html>
