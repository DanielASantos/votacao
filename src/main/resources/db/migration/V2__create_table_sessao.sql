create table sessao (
    id UUID primary key,
    pauta_id UUID UNIQUE NOT NULL,
    data_abertura TIMESTAMP NOT NULL,
    data_fechamento TIMESTAMP,
    constraint fk_sessao_pauta FOREIGN KEY (pauta_id) REFERENCES pauta (id)
)