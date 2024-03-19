create table tbl_reservations (
    id uuid primary key,
    user_id uuid not null,
    user_name varchar(100) not null,
    status boolean default false,
    price numeric(15,2) not null,
    created_at timestamp with time zone not null,
    created_by varchar(100) not null,
    updated_at timestamp with time zone not null,
    updated_by varchar(100) not null,
    version timestamp with time zone not null
);

create index idx_reservations_user_id on tbl_reservations(user_id);
create index idx_reservations_user_name on tbl_reservations(user_name);
