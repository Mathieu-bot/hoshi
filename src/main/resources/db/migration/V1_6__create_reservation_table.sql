create table if not exists reservation
(
    id            uuid             not null,
    created_at    timestamp with time zone not null,
    status        varchar(20)      not null,
    user_id       uuid             not null,
    projection_id uuid             not null,
    constraint reservation_pk primary key (id),
    constraint fk_reservation_on_user foreign key (user_id) references "user" (id),
    constraint fk_reservation_on_projection foreign key (projection_id) references projection (id)
);
