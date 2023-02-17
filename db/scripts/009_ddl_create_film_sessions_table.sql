CREATE TABLE film_sessions
(
    id          SERIAL PRIMARY KEY,
    film_id     int references films (id),
    hall_id     int references halls (id),
    start_time  timestamp                 not null,
    end_time    timestamp                 not null,
    price       int                       not null
);