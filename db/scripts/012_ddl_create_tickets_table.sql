create table if not exists tickets
(
    id           SERIAL PRIMARY KEY,
    session_id   int references film_sessions (id),
    row_number    int  not null,
    place_number  int  not null,
    user_id       int  not null,
    unique (session_id, row_number, place_number)
);