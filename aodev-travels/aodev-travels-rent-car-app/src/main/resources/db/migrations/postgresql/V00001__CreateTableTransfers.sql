create table tbl_transfers (
    id uuid primary key,
    external_id uuid,
    car_info varchar(100) not null,
    status boolean default false,
    price numeric(15,2) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_transfers_car_info on tbl_transfers("car_info");
create index idx_transfers_external_id on tbl_transfers("external_id");
