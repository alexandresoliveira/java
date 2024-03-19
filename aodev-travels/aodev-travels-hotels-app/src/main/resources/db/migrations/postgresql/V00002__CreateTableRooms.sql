create table tbl_rooms (
    id uuid primary key,
    hotel_id uuid not null,
    external_id uuid,
    type varchar(10) not null,
    beds varchar(50) not null,
    is_available boolean default false,
    price numeric(15,2) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_rooms_hotel_id on tbl_rooms(hotel_id);
create index idx_rooms_external_id on tbl_rooms(external_id);
create index idx_rooms_type on tbl_rooms(type);
create index idx_rooms_is_available on tbl_rooms(is_available);
create index idx_rooms_price on tbl_rooms(price);