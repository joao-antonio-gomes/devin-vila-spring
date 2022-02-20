create table residents
(
    id         bigint         not null auto_increment,
    first_name varchar(255)   not null,
    last_name  varchar(255)   not null,
    cpf        varchar(14)    not null unique,
    birth_date date           not null,
    rent       decimal(10, 2) not null,
    email      varchar(255)   not null,
    password   varchar(255)   not null,
    primary key (id)
);

insert into residents (first_name, last_name, cpf, birth_date, rent, email, password)
values ('admin', 'admin', '000.000.000-00', '2022-01-01', 0.0, 'admin@admin.com',
        '$2a$12$aDlKdl1vb5xfVYFLIaQ9f.6oWIJ/LHwbqo/j6ytLnP17OAZjSn/iq');