package org.example.filter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/filter"; // 连接到名为 filter 的数据库
    private static final String USER = "root"; // 数据库用户名
    private static final String PASSWORD = "123456"; // 数据库密码

    /**
     * 获取数据库连接
     * @return Connection 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载 MySQL 驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
