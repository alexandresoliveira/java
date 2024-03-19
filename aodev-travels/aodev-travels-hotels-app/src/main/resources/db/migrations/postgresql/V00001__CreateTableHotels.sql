create table tbl_hotels (
    id uuid primary key,
    name varchar(100) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_hotels_name on tbl_hotels("name");
create index idx_hotels_city on tbl_hotels("city");
create index idx_hotels_state on tbl_hotels("state");