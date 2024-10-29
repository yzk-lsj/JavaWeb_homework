package org.javaweb.homework;

import org.javaweb.homework.BatchInsert.TeacherBatchInsert;
import org.javaweb.homework.CRUD.DeleteTeacher;
import org.javaweb.homework.CRUD.InsertTeacher;
import org.javaweb.homework.CRUD.QueryTeachers;
import org.javaweb.homework.CRUD.UpdateTeacher;
import org.javaweb.homework.ScrollableResultSet.TeacherScrollableResultSet;

/**
 * 主类，用于执行教师信息的CRUD操作
 */
public class Main {

    /**
     * 主方法，依次执行插入、更新、查询和删除教师信息
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 插入教师
        InsertTeacher.insertTeacher(1, "小顺", "数学", "1980-05-20");

        // 更新教师课程
        UpdateTeacher.updateTeacher(1, "Javaweb");

        // 查询所有教师
        QueryTeachers.queryTeachers();

        // 删除教师
        DeleteTeacher.deleteTeacher(1);

        //插入500个教师，每插入100条数据提交一次。
        System.out.println("开始批量插入教师数据...");
        TeacherBatchInsert.batchInsertTeachers(500);
        System.out.println("批量插入完成！");

        //可滚动的结果集练习，只查看结果集中倒数第2条数据。
        System.out.println("开始查看倒数第二条数据...");

        // 调用 TeacherScrollableResultSet 中的方法查看倒数第二条记录
        TeacherScrollableResultSet.viewSecondLastRecord();
    }
}
