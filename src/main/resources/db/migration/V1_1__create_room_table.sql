create table if not exists room
(
    id       uuid         not null,
    number   varchar(50)  not null,
    capacity integer      not null,
    constraint room_pk primary key (id)
);

alter table room
    add constraint uc_room_number unique (number);
