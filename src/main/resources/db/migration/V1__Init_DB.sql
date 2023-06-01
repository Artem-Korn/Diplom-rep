create sequence mark_table_seq start with 1 increment by 50;
create sequence studclass_table_seq start with 1 increment by 50;
create sequence task_table_seq start with 1 increment by 50;
create sequence user_table_seq start with 1 increment by 50;

create table mark_table
(
    id         bigint not null,
    date       timestamp(6) not null,
    difficulty integer not null,
    game_name  varchar(255) not null,
    mark       integer not null,
    task_id    bigint,
    user_id    bigint not null,
    primary key (id)
);

create table studclass_table
(
    id      bigint not null,
    name    varchar(255) not null,
    user_id bigint not null,
    primary key (id)
);

create table task_table
(
    id         bigint not null,
    difficulty integer not null,
    game_name  varchar(255) not null,
    options    varchar(255) not null,
    user_id    bigint not null,
    primary key (id)
);

create table user_role_table
(
    user_id bigint not null,
    roles   varchar(255) not null
);

create table user_studclass_table
(
    user_id      bigint not null,
    studclass_id bigint not null,
    primary key (user_id, studclass_id)
);

create table user_table
(
    id         bigint not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    patronymic varchar(255) not null,
    username   varchar(255) not null,
    primary key (id)
);

create table user_task_table
(
    user_id bigint not null,
    task_id bigint not null,
    primary key (user_id, task_id)
);

alter table if exists mark_table
    add constraint mark_task_fk foreign key (task_id) references task_table;

alter table if exists mark_table
    add constraint mark_user_fk foreign key (user_id) references user_table;

alter table if exists studclass_table
    add constraint studclass_user_fk foreign key (user_id) references user_table;

alter table if exists task_table
    add constraint task_user_fk foreign key (user_id) references user_table;

alter table if exists user_role_table
    add constraint user_role_fk foreign key (user_id) references user_table;

alter table if exists user_studclass_table
    add constraint user_studclass_fk foreign key (studclass_id) references studclass_table;

alter table if exists user_studclass_table
    add constraint studclass_user_fk foreign key (user_id) references user_table;

alter table if exists user_task_table
    add constraint user_task_fk foreign key (task_id) references task_table;

alter table if exists user_task_table
    add constraint task_user_fk foreign key (user_id) references user_table;