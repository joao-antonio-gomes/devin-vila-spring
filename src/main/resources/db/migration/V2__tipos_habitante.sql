create table tipos_habitante
(
    id   bigint      not null auto_increment,
    nome varchar(50) not null,
    primary key (id)
);

insert into tipos_habitante (nome)
values ('ADMIN'),
       ('USER');