create table registration.users (
    id uuid primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(200) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);