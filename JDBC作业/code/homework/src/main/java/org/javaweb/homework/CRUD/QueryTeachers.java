package org.javaweb.homework.CRUD;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * QueryTeachers类用于查询教师信息
 */
public class QueryTeachers {

    /**
     * 查询并打印所有教师信息
     */
    public static void queryTeachers() {
        String sql = "select * from teacher";
        try (Connection conn = TeacherDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 遍历结果集并打印教师信息
            while (rs.next()) {
                System.out.println("教师id为: " + rs.getInt("id") + ", 教师名称为: " + rs.getString("name") +
                        ", 教授课程为: " + rs.getString("course") + ", 生日为: " + rs.getDate("birthday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
