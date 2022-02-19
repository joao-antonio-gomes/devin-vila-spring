create table habitantes_tipos_habitante
(
    habitante_id      bigint not null,
    tipo_habitante_id bigint not null,
    foreign key (habitante_id) references habitantes (id),
    foreign key (tipo_habitante_id) references tipos_habitante (id)
);

insert into habitantes_tipos_habitante (habitante_id, tipo_habitante_id)
values (1, 1);