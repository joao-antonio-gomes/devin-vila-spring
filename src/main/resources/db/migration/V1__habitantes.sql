create table habitantes
(
    id    bigint       not null auto_increment,
    email varchar(255) not null,
    senha varchar(255) not null,
    primary key (id)
);

insert into habitantes (email, senha)
values ('admin@admin.com', '$2a$12$aDlKdl1vb5xfVYFLIaQ9f.6oWIJ/LHwbqo/j6ytLnP17OAZjSn/iq');