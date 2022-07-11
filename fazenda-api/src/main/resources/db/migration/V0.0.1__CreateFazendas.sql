create table fazendas (
    id uuid primary key,
    nome varchar(80) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create index fazendas_idx on fazendas(id);