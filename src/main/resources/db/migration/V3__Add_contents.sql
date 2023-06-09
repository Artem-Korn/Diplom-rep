insert into user_table (id, username, password, last_name, first_name, patronymic)
values
    (2, 'petrenko', crypt('t', gen_salt('bf', 8)), 'Петренко', 'Максим', 'Семенович'),
    (3, 'kravchenko', crypt('t', gen_salt('bf', 8)), 'Кравченко', 'Вячеслав', 'Павлович'),
    (4, 'afanasenko_yakov', crypt('t', gen_salt('bf', 8)), 'Афанасенко', 'Яков', 'Кирилович'),
    (5, 'kostirko', crypt('s', gen_salt('bf', 8)), 'Костирко', 'Ілля', 'Владиславович'),
    (6, 'vaschenko', crypt('s', gen_salt('bf', 8)), 'Ващенко', 'Борис', 'Едуардович'),
    (7, 'levchenko', crypt('s', gen_salt('bf', 8)), 'Левченко', 'Василій', 'Тимурович'),
    (8, 'kostushko_georgiy', crypt('s', gen_salt('bf', 8)), 'Костюшко', 'Георгій', 'Едуардович'),
    (9, 'svanchenko', crypt('s', gen_salt('bf', 8)), 'Іванченко', 'Миколай', 'Леонідович'),
    (10, 'kostenko', crypt('s', gen_salt('bf', 8)), 'Костенко', 'Петро', 'Леонідович'),
    (11, 'timchenko_elisaveta', crypt('s', gen_salt('bf', 8)), 'Тимченко', 'Єлизавета', 'Іванівна'),
    (12, 'velichko', crypt('s', gen_salt('bf', 8)), 'Величко', 'Агнія', 'Сергіївна'),
    (13, 'ystimenko', crypt('s', gen_salt('bf', 8)), 'Устименко', 'Марія', 'Георгіївна'),
    (14, 'mischenko', crypt('s', gen_salt('bf', 8)), 'Міщенко', 'Любов', 'Антонівна'),
    (15, 'kostushko_ganna', crypt('s', gen_salt('bf', 8)), 'Костюшко', 'Ганна', 'Анатоліївна'),
    (16, 'grabyanko', crypt('s', gen_salt('bf', 8)), 'Грабянко', 'Лариса', 'Кириловна'),
    (17, 'karpenko', crypt('s', gen_salt('bf', 8)), 'Карпенко', 'Анна', 'Артемівна'),
    (18, 'kazachenko_nina', crypt('s', gen_salt('bf', 8)), 'Казаченко', 'Ніна', 'Сергіївна'),
    (19, 'ryabchenko', crypt('s', gen_salt('bf', 8)), 'Рябченко', 'Поліна', 'Артемівна'),
    (20, 'bezborodko_irina', crypt('s', gen_salt('bf', 8)), 'Безбородко', 'Ірина', 'Едуардівна'),
    (21, 'korolenko_vira', crypt('s', gen_salt('bf', 8)), 'Короленко', 'Віра', 'Сергіївна'),
    (22, 'sydienko', crypt('s', gen_salt('bf', 8)), 'Судіенко', 'Надія', 'Сергіївна');

select setval('user_table_seq', 22);

insert into user_role_table (user_id, roles)
values
    (2, 'TEACHER'), (3, 'TEACHER'), (4, 'TEACHER'),
    (5, 'STUDENT'), (6, 'STUDENT'), (7, 'STUDENT'), (8, 'STUDENT'), (9, 'STUDENT'), (10, 'STUDENT'),
    (11, 'STUDENT'), (12, 'STUDENT'), (13, 'STUDENT'), (14, 'STUDENT'), (15, 'STUDENT'), (16, 'STUDENT'),
    (17, 'STUDENT'), (18, 'STUDENT'), (19, 'STUDENT'), (20, 'STUDENT'), (21, 'STUDENT'), (22, 'STUDENT');

insert into studclass_table (id, name, user_id)
values
    (1, '4-А Вечір', 3),
    (2, '4-А Ранок', 3),
    (3, 'Збірка 2023', 3),
    (4, 'Посилений', 4),
    (5, 'Послаблений', 4),
    (6, 'Весна 2023', 2),
    (7, 'Літо 2023', 2),
    (8, 'Вчителі 2023', 1);

select setval('studclass_table_seq', 8);

insert into user_studclass_table (user_id, studclass_id)
values
    (22, 1), (21, 1), (20, 1), (5, 1), (6, 1), (7, 1),
    (19, 2), (18, 2), (17, 2), (8, 2), (9, 2), (10, 2), (11, 2), (12, 2), (13, 2),
    (22, 3), (21, 3), (20, 3), (5, 3), (6, 3), (7, 3), (8, 3), (9, 3), (10, 3),
    (11, 3), (12, 3), (13, 3), (14, 3), (19, 3), (18, 3), (17, 3), (3, 3),
    (15, 4), (16, 4), (5, 4), (9, 4), (21, 4),
    (22, 5), (19, 5), (8, 5), (13, 5),
    (5, 6), (11, 6), (13, 6), (19, 6), (20, 6), (21, 6), (10, 6),
    (2, 8), (3, 8), (4, 8);

insert into task_table (id, difficulty, game_name, options, user_id)
values
    (1, 75, 'Таблиця Шульте', '6;6;1;1;0;3;5', 1),
    (2, 50, 'Знайди крота', '5;5;1;13;7;5;0;0', 1),
    (3, 71, 'Де більше?', '0;100;60', 1),
    (4, 39, 'Таблиця Шульте', '3;3;0;1;0;1;5', 3),
    (5, 45, 'Таблиця Шульте', '5;5;1;1;0;1;5', 3),
    (6, 29, 'Знайди крота', '3;3;0;1;3;2;0;0', 4),
    (7, 42, 'Де більше?', '0;5;30', 2);

select setval('task_table_seq', 7);

insert into user_task_table (user_id, task_id)
values
    (2, 1), (2, 2), (2, 3), (2, 4),
    (3, 1), (3, 2), (3, 3), (3, 4),
    (4, 1), (4, 2), (4, 3),
    (5, 4), (5, 5), (5, 6);

insert into mark_table (id, date, difficulty, game_name, mark, task_id, user_id)
values
    (1, '2023-04-10 17:39:00', 0, 'Таблиця Шульте', -1, null, 5),
    (2, '2023-04-21 16:16:00', 39, 'Таблиця Шульте', 100, null, 5),
    (3, '2023-05-04 17:01:00', 39, 'Таблиця Шульте', 87, null, 5),
    (4, '2023-05-11 17:11:00', 46, 'Таблиця Шульте', 69, null, 5),
    (5, '2023-05-19 16:10:00', 51, 'Таблиця Шульте', 31, null, 5),
    (6, '2023-05-26 17:05:00', 39, 'Таблиця Шульте', 100, null, 5),
    (7, '2023-06-01 17:47:00', 46, 'Таблиця Шульте', 91, null, 5),

    (8, '2023-03-19 15:57:00', 29, 'Знайди крота', 100, null, 5),
    (9, '2023-03-20 16:24:00', 36, 'Знайди крота', 100, null, 5),
    (10, '2023-04-01 16:08:00', 41, 'Знайди крота', 50, null, 5),
    (11, '2023-04-10 17:48:00', 36, 'Знайди крота', 100, null, 5),
    (12, '2023-04-21 16:01:00', 41, 'Знайди крота', 100, null, 5),
    (13, '2023-05-04 17:10:00', 48, 'Знайди крота', 100, null, 5),
    (14, '2023-05-11 17:24:00', 57, 'Знайди крота', 0, null, 5),
    (15, '2023-05-19 16:15:00', 48, 'Знайди крота', 100, null, 5),
    (16, '2023-05-26 17:09:00', 57, 'Знайди крота', 50, null, 5),
    (17, '2023-06-01 17:58:00', 57, 'Знайди крота', 100, null, 5),

    (18, '2023-03-19 15:41:00', 29, 'Де більше?', 59, null, 5),
    (19, '2023-03-20 16:37:00', 36, 'Де більше?', 61, null, 5),
    (20, '2023-04-01 15:50:00', 41, 'Де більше?', 32, null, 5),
    (21, '2023-04-08 17:28:00', 36, 'Де більше?', 97, null, 5),
    (22, '2023-04-15 16:23:00', 41, 'Де більше?', 22, null, 5),
    (23, '2023-04-23 17:20:00', 48, 'Де більше?', 46, null, 5),
    (24, '2023-05-01 17:22:00', 57, 'Де більше?', 70, null, 5),
    (25, '2023-05-08 16:09:00', 48, 'Де більше?', 91, null, 5),
    (26, '2023-05-15 17:31:00', 57, 'Де більше?', 66, null, 5),
    (27, '2023-05-23 17:29:00', 57, 'Де більше?', 75, null, 5),
    (28, '2023-05-30 16:16:00', 48, 'Де більше?', 87, null, 5),
    (29, '2023-06-01 15:34:00', 57, 'Де більше?', 82, null, 5),
    (30, '2023-06-01 16:27:00', 57, 'Де більше?', 85, null, 5),

    (31, '2023-06-01 16:48:00', 39, 'Таблиця Шульте', 100, 4, 22),
    (32, '2023-06-01 16:45:00', 39, 'Таблиця Шульте', 71, 4, 18),
    (33, '2023-06-01 16:50:00', 39, 'Таблиця Шульте', 90, 4, 9),
    (34, '2023-06-01 16:47:00', 39, 'Таблиця Шульте', 54, 4, 6),
    (35, '2023-06-01 16:51:00', 39, 'Таблиця Шульте', 78, 4, 10),

    (36, '2023-06-01 17:30:00', 45, 'Таблиця Шульте', 93, 5, 11),
    (37, '2023-06-01 17:29:00', 45, 'Таблиця Шульте', 100, 5, 6),
    (38, '2023-06-01 17:51:00', 45, 'Таблиця Шульте', 87, 5, 7);

select setval('mark_table_seq', 30);

