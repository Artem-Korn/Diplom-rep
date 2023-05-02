insert into users (id, username, password)
    values (1, 't', 't'), (2, 's', 's');

insert into user_role (user_id, roles)
    values (1, 'TEACHER'), (1, 'ADMIN'),
           (2, 'STUDENT'), (2, 'ADMIN');