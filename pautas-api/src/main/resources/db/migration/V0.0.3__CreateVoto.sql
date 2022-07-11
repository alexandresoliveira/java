create table votos (
    id uuid primary key,
    cpf varchar(11) not null,
    resposta varchar(3) not null,
    votacao_id uuid not null,
    data_voto timestamp with time zone not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint fk_votacao_id foreign key (votacao_id) references votacoes(id)
);

create index votos_idx on votos(id);