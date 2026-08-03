create table if not exists "user"
(
    id         uuid         not null,
    first_name varchar(200),
    last_name  varchar(200) not null,
    birthdate  date,
    email      varchar(255) not null,
    password   varchar(255) not null,
    phone      varchar(20),
    role       varchar(20)  not null,
    constraint user_pk primary key (id)
);

alter table "user"
    add constraint uc_user_email unique (email);
