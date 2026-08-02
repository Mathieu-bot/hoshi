create table if not exists movie
(
    id    uuid        not null,
    title varchar(200) not null,
    constraint movie_pk primary key (id)
);
