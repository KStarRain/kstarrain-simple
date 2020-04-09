create tablespace kstarrain_space
datafile 'D:\Program_Files\develop\oracle_xe\tablespace\kstarrain_space.dbf'
size 100m
autoextend on
next 10m;

create user root identified by qwer1234 default tablespace kstarrain_space;
grant CONNECT,RESOURCE to root;

CREATE TABLE t_student (
  ID varchar2(32) PRIMARY KEY,
  NAME varchar2(10),
  BIRTHDAY date,
  MONEY number(10,2),
  CREATE_DATE date NOT NULL ,
  UPDATE_DATE date DEFAULT sysdate ,
  ALIVE_FLAG char(1) DEFAULT "1"
);
comment on table t_student is '学生表';
comment on column t_student.ID is '主键';
comment on column t_student.NAME is '姓名';
comment on column t_student.BIRTHDAY is '生日';
comment on column t_student.MONEY is '余额';
comment on column t_student.CREATE_DATE is '创建时间';
comment on column t_student.UPDATE_DATE is '更新时间';
comment on column t_student.ALIVE_FLAG is '删除标记';


insert into t_student (id, name, birthday, money, create_date, update_date, alive_flag)
values ('d7fbbfd87a08408ba994dea6be435ada', '貂蝉', to_date('1991-9-7', 'yyyy-mm-dd'), 13.14, to_date('2019-03-11 21:17:34', 'yyyy-mm-dd hh24:mi:ss'), to_date('2019-03-11 21:17:34', 'yyyy-mm-dd hh24:mi:ss'), '1');
commit;