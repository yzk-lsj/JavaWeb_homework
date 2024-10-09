-- 1. 查询所有员工的姓名、邮箱和工作岗位。
select first_name,last_name,email,job_title from employees

-- 2. 查询所有部门的名称和位置
select dept_name,location from departments

-- 3. 查询工资超过70000的员工姓名和工资
select first_name,last_name,salary from employees where salary > 70000

-- 4. 查询IT部门的所有员工
select first_name,last_name from employees where dept_id in (select dept_id from departments where dept_name = 'IT')

-- 5. 询入职日期在2020年之后的员工信息
select * from employees where hire_date >= '2021-01-01'

-- 6. 计算每个部门的平均工资
select avg(salary),dept_id from employees group by dept_id

-- 7. 查询工资最高的前3名员工信息
select * from employees order by salary desc limit 3

-- 8. 查询每个部门员工数量。
select dept_id,count(dept_id) from employees group by dept_id

-- 9. 查询没有分配部门的员工。
select first_name,last_name from employees where dept_id is null

-- 10. 查询参与项目数量最多的员工。
select e.emp_id, e.first_name, e.last_name, COUNT(ep.project_id) as project_count
from employees e
join employee_projects ep on e.emp_id = ep.emp_id
group by e.emp_id
having project_count = (
    select MAX(project_count)
    from (
        select COUNT(ep.project_id) as project_count
        from employee_projects ep
        group by ep.emp_id
    ) as subquery
)
order by e.emp_id;

-- 11. 计算所有员工的工资总和。
select sum(salary) from employees

-- 12. 查询姓"Smith"的员工信息。
select * from employees where first_name = 'Smith'

-- 13. 查询即将在半年内到期的项目。
select * from projects where end_date between curdate() and date_add(curdate(), interval 6 month);

-- 14.查询至少参与了两个项目的员工。
select e.emp_id,e.first_name,e.last_name,ep.count from employees as e join (select emp_id,count(emp_id) as count from employee_projects group by emp_id having count >= 2) as ep on e.emp_id = ep.emp_id

-- 15. 查询没有参与任何项目的员工。
select *
from employees as e
where e.emp_id not in(
	select DISTINCT ep.emp_id
	from employee_projects as ep
)

-- 16. 计算每个项目参与的员工数量。
select project_id,count(project_id) as count
from employee_projects
group by project_id

-- 17. 查询工资第二高的员工信息。
select * 
from employees 
order by salary DESC
limit 1,1

-- 18. 查询每个部门工资最高的员工。
select e.*
from employees e
join (
    select dept_id, max(salary) as max_salary
    from employees
    group by dept_id
) as max_salaries on e.dept_id = max_salaries.dept_id and e.salary = max_salaries.max_salary;

-- 19. 计算每个部门的工资总和,并按照工资总和降序排列。
select dept_id,sum(salary) as sum 
from employees
group by dept_id
order by sum desc

-- 20. 查询员工姓名、部门名称和工资。
select e.first_name,d.dept_name,e.salary
from employees as e
join departments as d
on e.dept_id = d.dept_id

-- 21. 查询每个员工的上级主管(假设emp_id小的是上级)。
select e1.emp_id,e1.first_name as worker_first_name,e1.last_name as worker_last_name,e2.first_name as boss_first_name,e2.last_name as boss_last_nam
from employees as e1
join employees as e2
on e1.dept_id = e2.dept_id
where e1.emp_id > e2.emp_id

-- 22. 查询所有员工的工作岗位,不要重复。
select distinct job_title
from employees

-- 23. 查询平均工资最高的部门。
select dept_id,avg(salary) as avg_salary
from employees
group by dept_id
order by avg_salary DESC
limit 1

-- 24. 查询工资高于其所在部门平均工资的员工。
select e.first_name,e.last_name
from employees as e
join (
	select dept_id,avg(salary) as avg_salary
	from employees
	group by dept_id) as avg
on e.dept_id = avg.dept_id
where e.salary > avg.avg_salary

-- 25. 查询每个部门工资前两名的员工。
select * 
from (
	select dept_id,first_name,last_name,
	rank() over(
		partition by dept_id
		order by salary desc
	)as rn
	from employees
) as ranked
where rn <= 2

-- 26. 查询跨部门的项目(参与员工来自不同部门)。
select p.project_id, p.project_name
from projects p
join employee_projects ep on p.project_id = ep.project_id
join employees e on ep.emp_id = e.emp_id
group by p.project_id, p.project_name
having count(distinct e.dept_id) > 1;

-- 27. 查询每个员工的工作年限,并按工作年限降序排序。
select emp_id,first_name,last_name,hire_date
from employees
order by hire_date desc

-- 28. 查询本月过生日的员工(假设hire_date是生日)。
select *
from employees
where month(hire_date) = month(curdate()) and year(hire_date) <> year(curdate());

-- 29. 查询即将在90天内到期的项目和负责该项目的员工。
select p.project_id, p.project_name, e.first_name, e.last_name
from projects p
join employee_projects ep on p.project_id = ep.project_id
join employees e on ep.emp_id = e.emp_id
where p.end_date between curdate() and date_add(curdate(), interval 90 day);

-- 30. 计算每个项目的持续时间(天数)。
select project_id, project_name, 
       datediff(end_date, start_date) as duration_days
from projects;

-- 31. 查询没有进行中项目的部门。
select d.dept_id, d.dept_name
from departments d
where d.dept_id not in (
    select distinct e.dept_id
    from employees e
    join employee_projects ep on e.emp_id = ep.emp_id
    join projects p on ep.project_id = p.project_id
    where p.start_date <= curdate() and p.end_date >= curdate()
);

-- 32. 查询员工数量最多的部门。
select d.dept_id, d.dept_name, count(e.emp_id) as employee_count
from departments d
left join employees e on d.dept_id = e.dept_id
group by d.dept_id, d.dept_name
order by employee_count desc
limit 1;

-- 33. 查询参与项目最多的部门。
select b.dept_id, count(distinct b.project_id) as count
from (
	select e.emp_id, e.dept_id, ep.project_id
	from employees as e
	join employee_projects as ep on e.emp_id = ep.emp_id
) as b
group by b.dept_id
order by count desc
limit 1;

-- 34. 计算每个员工的薪资涨幅(假设每年涨5%)
select 
    emp_id, 
    first_name, 
    last_name, 
    salary, 
    hire_date,
    round(salary * power(1.05, year(curdate()) - year(hire_date)), 2) as adjusted_salary,
    round((salary * power(1.05, year(curdate()) - year(hire_date)) - salary), 2) as salary_increase
from employees;

-- 35. 查询入职时间最长的3名员工。
select emp_id,first_name,last_name, (CURRENT_DATE - hire_date) as times 
from employees 
order by times desc
limit 3

-- 36. 查询名字和姓氏相同的员工。
select first_name,last_name
from employees
where first_name = last_name

-- 37. 查询每个部门薪资最低的员工。
select e.*
from employees e
join (
    select dept_id, min(salary) as min_salary
    from employees
    group by dept_id
) as min_salaries on e.dept_id = min_salaries.dept_id and e.salary = min_salaries.min_salary;

-- 38. 查询哪些部门的平均工资高于公司的平均工资。
select dept_id,avg(salary) as avg
from
employees
group by dept_id
having avg > 
(
	select avg(salary)
	from employees
	)

-- 39. 查询姓名包含"son"的员工信息。
select *
from employees
where (first_name like '%son%') or (last_name like '%son%')

-- 40. 查询所有员工的工资级别(可以自定义工资级别)。
select emp_id, first_name, last_name, salary,
       case
           when salary < 50000 then '低级'
           when salary between 50000 and 70000 then '中级'
           when salary between 70001 and 90000 then '高级'
           else '顶级'
       end as salary_level
from employees;

-- 41. 查询每个项目的完成进度(根据当前日期和项目的开始及结束日期)。
select project_id, project_name, start_date, end_date,
       case
           when curdate() < start_date then '未开始'
           when curdate() > end_date then '已完成'
           else concat(round(100 * (datediff(curdate(), start_date) / datediff(end_date, start_date)), 2), '% 完成')
       end as progress
from projects;

-- 42.查询每个经理(假设job_title包含'Manager'的都是经理)管理的员工数量。
select m.emp_id as manager_id, m.first_name as manager_first_name, m.last_name as manager_last_name, count(e.emp_id) as employee_count
from employees m
join employees e on m.emp_id < e.emp_id and m.dept_id = e.dept_id
where m.job_title like '%manager%'
group by m.emp_id, m.first_name, m.last_name;

-- 43.查询工作岗位名称里包含"Manager"但不在管理岗位(salary<70000)的员工。
select emp_id,first_name,last_name
from employees
where job_title like '%manager%' and salary < 70000

-- 44.计算每个部门的男女比例(假设以名字首字母A-M为女性,N-Z为男性)。
select d.dept_id, d.dept_name,
       sum(case when left(e.first_name, 1) between 'A' and 'M' then 1 else 0 end) as female_count,
       sum(case when left(e.first_name, 1) between 'N' and 'Z' then 1 else 0 end) as male_count,
       round(sum(case when left(e.first_name, 1) between 'A' and 'M' then 1 else 0 end) / 
             sum(case when left(e.first_name, 1) between 'N' and 'Z' then 1 else 0 end), 2) as gender_ratio
from departments d
join employees e on d.dept_id = e.dept_id
group by d.dept_id, d.dept_name;

-- 45.查询每个部门年龄最大和最小的员工(假设hire_date反应了年龄)。
select d.dept_id, d.dept_name, '最大年龄员工' as employee_type, e.emp_id, e.first_name, e.last_name, e.hire_date
from employees e
join departments d on e.dept_id = d.dept_id
where e.hire_date = (
    select min(e2.hire_date) 
    from employees e2
    where e2.dept_id = e.dept_id
)
union all
select d.dept_id, d.dept_name, '最小年龄员工' as employee_type, e.emp_id, e.first_name, e.last_name, e.hire_date
from employees e
join departments d on e.dept_id = d.dept_id
where e.hire_date = (
    select max(e2.hire_date) 
    from employees e2
    where e2.dept_id = e.dept_id
)

-- 46. 查询连续3天都有员工入职的日期。
select distinct e1.hire_date
from employees e1
join employees e2 on date_add(e1.hire_date, interval 1 day) = e2.hire_date
join employees e3 on date_add(e1.hire_date, interval 2 day) = e3.hire_date
order by e1.hire_date;

-- 47. 查询员工姓名和他参与的项目数量。
select e.first_name, e.last_name, count(ep.project_id) as project_count
from employees e
left join employee_projects ep on e.emp_id = ep.emp_id
group by e.emp_id, e.first_name, e.last_name;

-- 48. 查询每个部门工资最高的3名员工。
select *
from (
    select e.emp_id, e.first_name, e.last_name, e.salary, e.dept_id, d.dept_name,
           row_number() over (partition by e.dept_id order by e.salary desc) as `rank`
    from employees e
    join departments d on e.dept_id = d.dept_id
) as ranked_employees
where ranked_employees.`rank` <= 3;

-- 49. 计算每个员工的工资与其所在部门平均工资的差值。
select e.emp_id, e.first_name, e.last_name, e.salary, d.dept_name,
       e.salary - (
           select avg(e2.salary)
           from employees e2
           where e2.dept_id = e.dept_id
       ) as salary_diff
from employees e
join departments d on e.dept_id = d.dept_id;

-- 50.查询所有项目的信息,包括项目名称、负责人姓名(假设工资最高的为负责人)、开始日期和结束日期。
select p.project_name, e.first_name, e.last_name, p.start_date, p.end_date
from projects p
join employee_projects ep on p.project_id = ep.project_id
join employees e on ep.emp_id = e.emp_id
where e.salary = (
    select max(e2.salary)
    from employees e2
    join employee_projects ep2 on e2.emp_id = ep2.emp_id
    where ep2.project_id = p.project_id
);
