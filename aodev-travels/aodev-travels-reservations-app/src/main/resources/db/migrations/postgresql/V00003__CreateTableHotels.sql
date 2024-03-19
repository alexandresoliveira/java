create table tbl_hotels (
    id uuid primary key,
    reservation_id uuid not null,
    external_id uuid not null,
    name varchar(100) not null,
    room varchar(50) not null,
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

create index idx_hotels_reservation_id on tbl_hotels(reservation_id);
create index idx_hotels_external_id on tbl_hotels(external_id);
