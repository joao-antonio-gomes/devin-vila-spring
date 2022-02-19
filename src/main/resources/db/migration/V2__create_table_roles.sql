create table roles
(
    id   bigint      not null auto_increment,
    name varchar(50) not null,
    primary key (id)
);

insert into roles (name)
values ('ADMIN'),
       ('USER');