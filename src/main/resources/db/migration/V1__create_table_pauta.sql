CREATE TABLE pauta (
    id UUID NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    CONSTRAINT pk_pauta PRIMARY KEY (id)
);
