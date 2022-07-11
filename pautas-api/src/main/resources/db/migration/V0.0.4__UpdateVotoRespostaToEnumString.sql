update votos
    set resposta = 'NAO'
where resposta = 'Não';

update votos
    set resposta = 'SIM'
where resposta = 'Sim';