CREATE SCHEMA IF NOT EXISTS tabela_tarifaria_api;

SET search_path TO tabela_tarifaria_api;

CREATE TABLE tabela_tarifaria (
    id_tabela BIGSERIAL PRIMARY KEY,
    nome_tabela VARCHAR(255) NOT NULL,
    data_vigencia DATE NOT NULL,
    CONSTRAINT uk_tabela_nome_data UNIQUE (nome_tabela, data_vigencia)
);

CREATE TABLE categoria_relacao (
    id_categoria_relacao BIGSERIAL PRIMARY KEY,
    categoria VARCHAR(50) NOT NULL,
    id_tabela BIGINT NOT NULL,
    CONSTRAINT fk_categoria_tabela
        FOREIGN KEY (id_tabela)
        REFERENCES tabela_tarifaria (id_tabela)
        ON DELETE CASCADE
);

CREATE TABLE faixa_consumo (
    faixa_id BIGSERIAL PRIMARY KEY,
    inicio_faixa INTEGER NOT NULL,
    fim_faixa INTEGER NOT NULL,
    valor_unitario NUMERIC(10,2) NOT NULL,
    id_categoria_relacao BIGINT NOT NULL,
    CONSTRAINT fk_faixa_categoria
        FOREIGN KEY (id_categoria_relacao)
        REFERENCES categoria_relacao (id_categoria_relacao)
        ON DELETE CASCADE
);