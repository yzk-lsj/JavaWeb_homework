package org.javaweb.homework.ScrollableResultSet;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TeacherScrollableResultSet 类用于展示可滚动结果集的使用，通过查询教师表，
 * 打印出结果集中倒数第二条数据的内容。
 */
public class TeacherScrollableResultSet {

    /**
     * viewSecondLastRecord 方法用于查询 teacher 表并输出结果集中的倒数第二条数据。
     * 若记录数不足两条，则提示记录数不足。
     */
    public static void viewSecondLastRecord() {
        // 声明数据库连接、Statement 和 ResultSet 对象
        try (Connection connection = TeacherDAO.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher")) {

            // 检查结果集中是否有足够的记录来定位倒数第二条记录
            if (resultSet.last() && resultSet.getRow() > 1) {
                resultSet.absolute(resultSet.getRow() - 1); // 定位到倒数第二条记录

                // 获取并打印倒数第二条数据的内容
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                String birthday = resultSet.getString("birthday");

                System.out.println("倒数第二条记录：");
                System.out.println("教师id: " + id + ", 教师姓名: " + name + ", 教授课程: " + course + ", 生日: " + birthday);
            } else {
                System.out.println("记录数不足，无法查看倒数第二条数据。");
            }
        } catch (SQLException e) {
            // 捕获 SQL 异常，并输出堆栈信息
            e.printStackTrace();
        }
    }
}
