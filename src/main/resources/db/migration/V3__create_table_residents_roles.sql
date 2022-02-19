create table residents_roles
(
    resident_id      bigint not null,
    role_id bigint not null,
    foreign key (resident_id) references residents (id),
    foreign key (role_id) references roles (id)
);

insert into residents_roles (resident_id, role_id)
values (1, 1);