create table if not exists projection
(
    id         uuid             not null,
    datetime   timestamp with time zone not null,
    seat_price numeric(10, 2)   not null,
    movie_id   uuid             not null,
    room_id    uuid             not null,
    constraint projection_pk primary key (id),
    constraint fk_projection_on_movie foreign key (movie_id) references movie (id),
    constraint fk_projection_on_room foreign key (room_id) references room (id)
);
