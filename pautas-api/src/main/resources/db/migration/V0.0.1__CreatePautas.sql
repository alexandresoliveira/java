create table pautas (
    id uuid primary key,
    name varchar(50) not null unique,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create index pautas_idx on pautas(id);