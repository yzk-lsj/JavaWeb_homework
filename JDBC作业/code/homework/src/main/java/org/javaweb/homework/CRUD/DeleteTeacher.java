package org.javaweb.homework.CRUD;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DeleteTeacher类用于删除教师信息
 */
public class DeleteTeacher {

    /**
     * 根据教师ID删除教师信息
     *
     * @param id 教师ID
     */
    public static void deleteTeacher(int id) {
        String sql = "delete from teacher where id = ?";
        try (Connection conn = TeacherDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            pstmt.setInt(1, id);

            // 执行删除操作
            pstmt.executeUpdate();
            System.out.println("教师信息删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
