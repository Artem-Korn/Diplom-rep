create extension if not exists pgcrypto;

insert into user_table (id, username, password, last_name, first_name, patronymic)
    values (nextval('user_table_seq'), 'admin_teacher', crypt('t', gen_salt('bf', 8)), 'Гріченко', 'Валерій', 'Семенович');

insert into user_role_table (user_id, roles)
    values (lastval(), 'ADMIN'),
           (lastval(), 'TEACHER');