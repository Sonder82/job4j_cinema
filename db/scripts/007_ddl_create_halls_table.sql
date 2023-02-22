create table if not exists halls
(
    id           SERIAL PRIMARY KEY,
    name         varchar  not null,
    row_count    int      not null,
    place_count  int      not null,
    description  varchar  not null
);