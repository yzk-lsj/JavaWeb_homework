package org.javaweb.homework.BatchInsert;

import org.javaweb.homework.DAO.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * TeacherBatchInsert 类负责批量插入教师数据。
 * 使用事务控制和批量插入功能，优化数据库写入性能。
 */
public class TeacherBatchInsert {

    // 计算机专业涉及的课程
    private static final String[] COURSES = {
            "计算机网络", "操作系统", "数据结构", "数据库系统", "软件工程",
            "编译原理", "人工智能", "算法设计与分析", "信息安全", "机器学习"
    };

    /**
     * 主方法用于调用批量插入教师数据的方法。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        batchInsertTeachers(500);
    }

    /**
     * 批量插入教师数据的方法。
     * 每插入 100 条数据提交一次事务，插入总数为 totalEntries。
     *
     * @param totalEntries 要插入的教师数量
     */
    public static void batchInsertTeachers(int totalEntries) {
        // 声明数据库连接和 PreparedStatement 对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        Random random = new Random();

        try {
            conn = TeacherDAO.getConnection(); // 获取数据库连接
            conn.setAutoCommit(false); // 开启事务模式

            // SQL 插入语句
            String sql = "INSERT INTO teacher (id, name, course, birthday) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // 循环插入指定数量的教师记录
            for (int i = 1; i <= totalEntries; i++) {
                pstmt.setInt(1, i); // 设置教师ID
                pstmt.setString(2, "教师" + i); // 设置教师姓名
                pstmt.setString(3, COURSES[random.nextInt(COURSES.length)]); // 随机选择课程
                pstmt.setDate(4, java.sql.Date.valueOf(generateRandomBirthday())); // 随机生成生日

                pstmt.addBatch(); // 添加到批处理

                // 每 100 条数据批量执行一次插入并提交事务
                if (i % 100 == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                }
            }
            pstmt.executeBatch(); // 插入剩余的数据
            conn.commit(); // 提交事务
            System.out.println("批量插入成功");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // 出错时回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 随机生成一个生日日期。
     * 生日范围为 1960-1989 年，每月最多 28 天。
     *
     * @return 生成的生日日期字符串，格式为 YYYY-MM-DD
     */
    private static String generateRandomBirthday() {
        int year = 1960 + new Random().nextInt(30); // 1960-1989年
        int month = 1 + new Random().nextInt(12); // 1-12月
        int day = 1 + new Random().nextInt(28); // 1-28日
        return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
    }
}
