

CREATE or replace table Employee (Name string, Salary int);
insert into Employee values ('Elroy',3000);
insert into Employee values ('Noah',3000);
insert into Employee values ('Wilbur',3000);
create view HighSalary (EmpName) as
  select Name from Employee where Salary >2500;
-- /debug_sql HighSalary trust_tables(no) 
