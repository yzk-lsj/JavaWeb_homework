-- 1. 查询所有学生的信息。
select * from student

-- 2. 查询所有课程的信息。
select * from course

-- 3. 查询所有学生的姓名、学号和班级。
select name,student_id,my_class from student

-- 4. 查询所有教师的姓名和职称。
select name,title from teacher

-- 5. 查询不同课程的平均分数。
select course_id,avg(score) 
from score
group by course_id

-- 6. 查询每个学生的平均分数。
select student_id,avg(score)
from score
group by student_id

-- 7. 查询分数大于85分的学生学号和课程号。
select student_id,course_id
from score
where score > 85

-- 8. 查询每门课程的选课人数。
select course_id,count(course_id)
from score
group by course_id

-- 9. 查询选修了"高等数学"课程的学生姓名和分数。
select s.name, sc.score
from student s
join score sc on s.student_id = sc.student_id
join course c on sc.course_id = c.course_id
where c.course_name = '高等数学';

-- 10. 查询没有选修"大学物理"课程的学生姓名。
select s.name
from student s
where not exists(
	select 1
	from score sc
	join course c on sc.course_id = c.course_id
	where c.course_name = '大学物理'
	and sc.student_id = s.student_id
)

-- 11. 查询C001比C002课程成绩高的学生信息及课程分数。
select s.name, sc1.course_id, sc1.score
from student s
join score sc1 on s.student_id = sc1.student_id
join score sc2 on s.student_id = sc2.student_id
where sc1.course_id = 'C001'
and sc2.course_id = 'C002'
and sc1.score > sc2.score

-- 12.统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比
select 
    c.course_id,
    c.course_name,
    sum(case when sc.score between 85 and 100 then 1 else 0 end) as '100-85',
    sum(case when sc.score between 70 and 84 then 1 else 0 end) as '85-70',
    sum(case when sc.score between 60 and 69 then 1 else 0 end) as '70-60',
    sum(case when sc.score between 0 and 59 then 1 else 0 end) as '60-0',
    round(sum(case when sc.score between 85 and 100 then 1 else 0 end) / count(*) * 100, 2) as '100-85%',
    round(sum(case when sc.score between 70 and 84 then 1 else 0 end) / count(*) * 100, 2) as '85-70%',
    round(sum(case when sc.score between 60 and 69 then 1 else 0 end) / count(*) * 100, 2) as '70-60%',
    round(sum(case when sc.score between 0 and 59 then 1 else 0 end) / count(*) * 100, 2) as '60-0%'
from score sc
join course c on sc.course_id = c.course_id
group by c.course_id, c.course_name

--  13. 查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。
select s.name, sc1.score as C002_score, sc2.score as C004_score
from student s
join score sc1 on s.student_id = sc1.student_id
left join score sc2 on s.student_id = sc2.student_id and sc2.course_id = 'C004'
where sc1.course_id = 'C002'

-- 14. 查询平均分数最高的学生姓名和平均分数。
select s.name,a.avg
from 
(
		select student_id,avg(score) as avg
		from score
		group by student_id
		order by avg desc
		limit 1
)as a
left join student as s
on s.student_id = a.student_id

-- 15. 查询总分最高的前三名学生的姓名和总分。
select s.name,a.sum
from 
(
	select student_id,sum(score) as sum
	from score
	group by student_id
	order by sum
	limit 3
) as a
left join student as s
on s.student_id = a.student_id

-- 16. 查询各科成绩最高分、最低分和平均分。要求如下：
-- 以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
-- 及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
-- 要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
select 
    c.course_id,
    c.course_name,
    max(sc.score) as highest_score,
    min(sc.score) as lowest_score,
    round(avg(sc.score), 2) as average_score,
    sum(case when sc.score >= 60 then 1 else 0 end) / count(*) * 100 as pass_rate,    -- 及格率 (>= 60)
    sum(case when sc.score >= 70 and sc.score < 80 then 1 else 0 end) / count(*) * 100 as middle_rate, -- 中等率 (70-80)
    sum(case when sc.score >= 80 and sc.score < 90 then 1 else 0 end) / count(*) * 100 as good_rate,   -- 优良率 (80-90)
    sum(case when sc.score >= 90 then 1 else 0 end) / count(*) * 100 as excellent_rate, -- 优秀率 (>= 90),
    count(*) as student_count -- 选修人数
from score sc
join course c on sc.course_id = c.course_id
group by c.course_id, c.course_name
order by student_count desc, c.course_id asc

-- 17. 查询男生和女生的人数。
select gender,count(gender)
from student
group by gender

-- 18. 查询年龄最大的学生姓名。
select name
from student
where birth_date in
(
	select min(birth_date)
	from student
)

-- 19. 查询年龄最小的教师姓名。
select name
from teacher
order by birth_date desc
limit 1;

-- 20. 查询学过「张教授」授课的同学的信息。
select student_id,teacher_id
from score as s
left join course as c
on s.course_id = c.course_id
where teacher_id =
(
	select teacher_id
	from teacher
	where name = '张教授'
)

-- 21. 查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。
select s.student_id, s.name
from student s
join score sc on s.student_id = sc.student_id
where sc.course_id in (
    select course_id
    from score
    where student_id = '2021001'
)
and s.student_id != '2021001';

-- 22. 查询每门课程的平均分数，并按平均分数降序排列。
select c.course_id, c.course_name, round(avg(sc.score), 2) as average_score
from score sc
join course c on sc.course_id = c.course_id
group by c.course_id, c.course_name
order by average_score desc

-- 23. 查询学号为"2021001"的学生所有课程的分数。
select c.course_id, c.course_name, sc.score
from score sc
join course c on sc.course_id = c.course_id
where sc.student_id = '2021001'

-- 24. 查询所有学生的姓名、选修的课程名称和分数。
select s.name, c.course_name, sc.score
from student s
join score sc on s.student_id = sc.student_id
join course c on sc.course_id = c.course_id;

-- 25. 查询每个教师所教授课程的平均分数。
select t.name as teacher_name, c.course_name, round(avg(sc.score), 2) as average_score
from teacher t
join course c 
on t.teacher_id = c.teacher_id
join score sc 
on c.course_id = sc.course_id
group by t.name, c.course_name;

-- 26. 查询分数在80到90之间的学生姓名和课程名称。
select s.name, c.course_name, sc.score
from student s
join score sc 
on s.student_id = sc.student_id
join course c 
on sc.course_id = c.course_id
where sc.score between 80 and 90

-- 27. 查询每个班级的平均分数。
select my_class,avg(sc.score) as avg
from student as s
left join score as sc
on s.student_id = sc.student_id
group by my_class

-- 28. 查询没学过"王讲师"老师讲授的任一门课程的学生姓名。
select s.name
from student s
where s.student_id not in (
    select sc.student_id
    from score sc
    join course c 
		on sc.course_id = c.course_id
    join teacher t 
		on c.teacher_id = t.teacher_id
    where t.name = '王讲师'
)

-- 29. 查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。
select s.student_id, s.name, round(avg(sc.score), 2) as avg_score
from student s
join score sc 
on s.student_id = sc.student_id
where sc.score < 85
group by s.student_id, s.name
having count(sc.course_id) >= 2

-- 30. 查询所有学生的总分并按降序排列。
select s.name,sum(sc.score) as sum
from student as s
left join score as sc
on s.student_id = sc.student_id
group by s.name
order by sum desc

-- 31. 查询平均分数超过85分的课程名称。
select c.course_name, round(avg(sc.score), 2) as average_score
from course c
join score sc 
on c.course_id = sc.course_id
group by c.course_name
having avg(sc.score) > 85;

-- 32. 查询每个学生的平均成绩排名。
select s.student_id, s.name, round(avg(sc.score), 2)as average_score,
rank() over (order by avg(sc.score) desc) as ranking
from student s
join score sc on s.student_id = sc.student_id
group by s.student_id, s.name
order by average_score desc;

-- 33. 查询每门课程分数最高的学生姓名和分数。
select s.name, c.course_name, sc.score
from student s
join score sc 
on s.student_id = sc.student_id
join course c 
on sc.course_id = c.course_id
where sc.score = (
    select max(score)
    from score
    where course_id = c.course_id
)

-- 34. 查询选修了"高等数学"和"大学物理"的学生姓名。
select s.name
from student s
where s.student_id in (
    select sc1.student_id
    from score sc1
    join course c1 
		on sc1.course_id = c1.course_id
    where c1.course_name = '高等数学'
)
and s.student_id in (
    select sc2.student_id
    from score sc2
    join course c2 
		on sc2.course_id = c2.course_id
    where c2.course_name = '大学物理'
)

-- 35. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。
select s.student_id, s.name, c.course_name, sc.score, round(avg(sc.score) over (partition by s.student_id), 2) as average_score
from student s
left join score sc 
on s.student_id = sc.student_id
left join course c 
on sc.course_id = c.course_id
order by average_score desc, s.student_id, c.course_name

-- 36. 查询分数最高和最低的学生姓名及其分数。
select student.name, score.score
from student
join score on student.student_id = score.student_id
where score.score = (select max(score) from score)
   or score.score = (select min(score) from score)

-- 37. 查询每个班级的最高分和最低分。
select student.my_class, max(score.score) as highest_score, min(score.score) as lowest_score
from student
join score on student.student_id = score.student_id
group by student.my_class

-- 38.  查询每门课程的优秀率（优秀为90分）。
select 
    course.course_name, 
    count(case when score.score >= 90 then 1 end) / count(*) * 100 as excellent_rate
from score
join course on score.course_id = course.course_id
group by course.course_name

-- 39.  查询平均分数超过班级平均分数的学生。
select student.student_id, student.name, avg(score.score) as avg_score
from student
join score on student.student_id = score.student_id
group by student.student_id, student.my_class
having avg(score.score) > (
    select avg(s.score)
    from score s
    join student st on s.student_id = st.student_id
    where st.my_class = student.my_class
)

-- 40.  查询每个学生的分数及其与课程平均分的差值。
select 
    student.student_id, 
    student.name, 
    score.course_id, 
    score.score, 
    (score.score - course_avg.avg_score) as score_diff
from 
    student
join 
    score on student.student_id = score.student_id
join 
    (select course_id, avg(score) as avg_score from score group by course_id) as course_avg
    on score.course_id = course_avg.course_id

-- 41.  查询至少有一门课程分数低于80分的学生姓名。
select distinct student.name
from student
join score on student.student_id = score.student_id
where score.score < 80

-- 42. 查询所有课程分数都高于85分的学生姓名。
select student.name
from student
join score on student.student_id = score.student_id
group by student.student_id
having min(score.score) > 85

-- 43.  查询查询平均成绩大于等于90分的同学的学生编号和学生姓名和平均成绩。
select student.student_id, student.name, avg(score.score) as avg_score
from student
join score on student.student_id = score.student_id
group by student.student_id
having avg(score.score) >= 90

-- 44. 查询选修课程数量最少的学生姓名。
select student.name
from student
join score on student.student_id = score.student_id
group by student.student_id
order by count(score.course_id) asc
limit 1

-- 45. 查询每个班级的第2名学生（按平均分数排名）。
select t.my_class, t.student_id, t.name, t.avg_score
from (
    select student.my_class, student.student_id, student.name, avg(score.score) as avg_score,
           dense_rank() over (partition by student.my_class order by avg(score.score) desc) as `rank`
    from student
    join score on student.student_id = score.student_id
    group by student.student_id, student.my_class
) as t
where t.`rank` = 2

-- 46.  查询每门课程分数前三名的学生姓名和分数。
select t.course_id, t.name, t.score
from (
    select score.course_id, student.name, score.score,
           dense_rank() over (partition by score.course_id order by score.score desc) as `rank`
    from score
    join student on score.student_id = student.student_id
) as t
where t.`rank` <= 3

-- 47.  查询平均分数最高和最低的班级。
-- 查询平均分数最高的班级
select my_class, avg_score
from (
    select student.my_class, avg(score.score) as avg_score
    from student
    join score on student.student_id = score.student_id
    group by student.my_class
) as class_avg
order by avg_score desc
limit 1;

-- 查询平均分数最低的班级
select my_class, avg_score
from (
    select student.my_class, avg(score.score) as avg_score
    from student
    join score on student.student_id = score.student_id
    group by student.my_class
) as class_avg
order by avg_score asc
limit 1;

--  48. 查询每个学生的总分和他所在班级的平均分数。
select student.student_id, student.name, sum(score.score) as total_score, class_avg.avg_score as class_avg_score
from student
join score on student.student_id = score.student_id
join (
    select student.my_class, avg(score.score) as avg_score
    from student
    join score on student.student_id = score.student_id
    group by student.my_class
) as class_avg on student.my_class = class_avg.my_class
group by student.student_id

-- 49. 查询每个学生的最高分的课程名称、学生名称和成绩
select s.name as student_name, c.course_name, sc.max_score
from (
    select student_id, max(score) as max_score
    from score
    group by student_id
) as sc
join student s on sc.student_id = s.student_id
join score sc2 on sc.student_id = sc2.student_id and sc.max_score = sc2.score
join course c on sc2.course_id = c.course_id;


-- 50. 查询每个班级的学生人数和平均年龄
select student.my_class, count(student.student_id) as student_count, avg(year(curdate()) - year(student.birth_date)) as avg_age
from student
group by student.my_class

