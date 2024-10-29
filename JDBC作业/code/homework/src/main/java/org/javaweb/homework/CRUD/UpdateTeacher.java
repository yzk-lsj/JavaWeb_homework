package org.javaweb.homework.CRUD;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * UpdateTeacher类用于更新教师信息
 */
public class UpdateTeacher {

    /**
     * 更新教师的教授课程
     *
     * @param id     教师ID
     * @param course 教师新的教授课程
     */
    public static void updateTeacher(int id, String course) {
        String sql = "update teacher set course = ? where id = ?";
        try (Connection conn = TeacherDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            pstmt.setString(1, course);
            pstmt.setInt(2, id);

            // 执行更新操作
            pstmt.executeUpdate();
            System.out.println("教师信息更新成功.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
