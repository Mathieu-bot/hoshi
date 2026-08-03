create table if not exists seat
(
    id      uuid       not null,
    number  varchar(10) not null,
    room_id uuid       not null,
    constraint seat_pk primary key (id),
    constraint fk_seat_on_room foreign key (room_id) references room (id)
);

alter table seat
    add constraint uc_seat_room_number unique (room_id, number);
