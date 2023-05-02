create sequence users_seq start with 1 increment by 50;

create table user_role (
    user_id bigint not null,
    roles varchar(255)
);

create table users (
    id bigint not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references users;
