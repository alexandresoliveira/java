create table tbl_seats (
    id uuid primary key,
    flight_id uuid not null,
    external_id uuid,
    seat_number varchar(3) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_seats_flight_id on tbl_seats(flight_id);
create index idx_seats_external_id on tbl_seats(external_id);
create index idx_seats_seat_number on tbl_seats(seat_number);