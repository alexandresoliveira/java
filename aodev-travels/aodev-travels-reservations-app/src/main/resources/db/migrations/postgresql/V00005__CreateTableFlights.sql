create table tbl_flights (
    id uuid primary key,
    reservation_id uuid not null,
    external_id uuid not null,
    company varchar(100) not null,
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

create index idx_flights_reservation_id on tbl_flights(reservation_id);
create index idx_flights_external_id on tbl_flights(external_id);
