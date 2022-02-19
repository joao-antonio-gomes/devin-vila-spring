create table residents
(
    id    bigint       not null auto_increment,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);

insert into residents (email, password)
values ('admin@admin.com', '$2a$12$aDlKdl1vb5xfVYFLIaQ9f.6oWIJ/LHwbqo/j6ytLnP17OAZjSn/iq');