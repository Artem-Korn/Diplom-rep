delete from user_task_table;
delete from user_studclass_table;
delete from user_role_table;
delete from user_table;

insert into user_table (id, username, password, last_name, first_name, patronymic)
    values (1, 'test_admin', '$2a$08$nPdU1n7bcWhN9QTvGRwqP.cAAXElJJNHzHjCC9j/rI5rTmmYo.rhu', 'Іванов', 'Іван', 'Іванович'),
           (2, 'test_student', '$2a$08$nPdU1n7bcWhN9QTvGRwqP.cAAXElJJNHzHjCC9j/rI5rTmmYo.rhu', 'Петров', 'Петро', 'Петрович');

insert into user_role_table (user_id, roles)
    values (1, 'ADMIN'), (1, 'TEACHER'),
           (2, 'STUDENT');

alter sequence user_table_seq restart with 10;