package org.javaweb.homework.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TeacherDAO类负责提供数据库连接。
 * 包含获取数据库连接的静态方法，供其他CRUD操作类调用。
 */
public class TeacherDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc"; // 修改为你的数据库名
    private static final String USER = "root"; // 数据库用户名
    private static final String PASSWORD = "123456"; // 数据库密码

    /**
     * 获取数据库连接
     *
     * @return 数据库连接对象
     * @throws SQLException 如果连接数据库失败，抛出SQL异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
