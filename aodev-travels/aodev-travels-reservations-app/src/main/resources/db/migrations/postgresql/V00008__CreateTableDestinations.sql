create table tbl_destinations (
    id uuid primary key,
    city varchar(100) not null,
    state varchar(100) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_destinations_reservation_id on tbl_destinations(city);
create index idx_destinations_external_id on tbl_destinations(state);
