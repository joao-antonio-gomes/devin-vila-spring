create table financial_reports
(
    id                         bigint         not null auto_increment,
    budget_remaining           decimal(10, 2) not null,
    budget_spent               decimal(10, 2) not null,
    created_at                 date           not null,
    most_expensive_resident_id bigint         not null,
    primary key (id),
    foreign key (most_expensive_resident_id) references residents (id)
);