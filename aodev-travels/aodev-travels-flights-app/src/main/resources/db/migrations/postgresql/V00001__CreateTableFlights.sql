create table tbl_flights (
    id uuid primary key,
    company varchar(50) not null,
    flight_number integer not null,
    origin varchar(100) not null,
    destiny varchar(100) not null,
    check_in timestamp with time zone not null,
    check_out timestamp with time zone not null,
    status boolean default false,
    price numeric(15,2) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_flights_origin on tbl_flights("origin");
create index idx_flights_destiny on tbl_flights("destiny");