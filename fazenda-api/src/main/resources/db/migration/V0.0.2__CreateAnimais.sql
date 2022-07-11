create table animais (
	id uuid primary key,
    tag varchar(100) not null,
    fazenda_id uuid not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint fk_fazenda_id foreign key (fazenda_id) references fazendas(id)
);

create index animais_idx on animais(id);