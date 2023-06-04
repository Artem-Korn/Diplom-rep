create extension if not exists pgcrypto;

insert into user_table (id, username, password, last_name, first_name, patronymic)
    values (1, 'admin_teacher', crypt('t', gen_salt('bf', 8)), 'Гріченко', 'Валерій', 'Семенович');

insert into user_role_table (user_id, roles)
    values (1, 'ADMIN'),
           (1, 'TEACHER');