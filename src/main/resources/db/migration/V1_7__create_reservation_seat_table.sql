create table if not exists reservation_seat
(
    reservation_id uuid not null,
    seat_id        uuid not null,
    constraint reservation_seat_pk primary key (reservation_id, seat_id),
    constraint fk_reservation_seat_on_reservation foreign key (reservation_id) references reservation (id),
    constraint fk_reservation_seat_on_seat foreign key (seat_id) references seat (id)
);
