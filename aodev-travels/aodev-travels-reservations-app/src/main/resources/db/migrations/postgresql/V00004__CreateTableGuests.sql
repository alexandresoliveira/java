create table tbl_guests (
    id uuid primary key,
    hotel_id uuid not null,
    name varchar(100) not null,
    age integer not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_guests_hotel_id on tbl_guests(hotel_id);
