create table votacoes (
    id uuid primary key,
    data_encerramento timestamp with time zone not null,
    pauta_id uuid not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint fk_pauta_id foreign key (pauta_id) references pautas(id)
);

create index votacoes_idx on votacoes(id);