create table tbl_transfers (
    id uuid primary key,
    reservation_id uuid not null,
    external_id uuid not null,
    car_info varchar(100) not null,
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

create index idx_transfers_reservation_id on tbl_transfers(reservation_id);
create index idx_transfers_external_id on tbl_transfers(external_id);
