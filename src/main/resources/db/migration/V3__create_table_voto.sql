CREATE TABLE voto (
    id UUID PRIMARY KEY,
    pauta_id UUID NOT NULL,
    associado_id VARCHAR(255) NOT NULL,
    voto VARCHAR(50) NOT NULL,
    CONSTRAINT fk_voto_pauta FOREIGN KEY (pauta_id) REFERENCES pauta (id),
    CONSTRAINT uk_pauta_associado UNIQUE (pauta_id, associado_id)
);