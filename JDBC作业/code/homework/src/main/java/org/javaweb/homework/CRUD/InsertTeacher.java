package org.javaweb.homework.CRUD;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * InsertTeacher类用于插入教师信息到数据库中。
 */
public class InsertTeacher {

    /**
     * 插入教师信息
     *
     * @param id      教师ID
     * @param name    教师姓名
     * @param course  教师教授课程
     * @param birthday 教师生日
     */
    public static void insertTeacher(int id, String name, String course, String birthday) {
        String sql = "insert into teacher (id, name, course, birthday) values (?, ?, ?, ?)";
        try (Connection conn = TeacherDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, course);
            pstmt.setString(4, birthday);

            // 执行插入操作
            pstmt.executeUpdate();
            System.out.println("教师信息插入成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
