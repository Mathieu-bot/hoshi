create table if not exists movie_genre
(
    movie_id uuid        not null,
    genre    varchar(20) not null,
    constraint movie_genre_pk primary key (movie_id, genre),
    constraint fk_movie_genre_on_movie foreign key (movie_id) references movie (id)
);
