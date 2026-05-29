DROP TABLE IF EXISTS predicao;
DROP TABLE IF EXISTS animal;
DROP TABLE IF EXISTS semen;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    endereco VARCHAR(200)
);

CREATE TABLE animal (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80),
    codigo VARCHAR(20),
    tipo VARCHAR(20) NOT NULL,
    sexo VARCHAR(10),
    especie VARCHAR(60),
    raca VARCHAR(60),
    peso DOUBLE PRECISION,
    data_nascimento DATE,
    data_vacinacao DATE,
    producao_leite_diaria DOUBLE PRECISION,
    producao_leite_mensal DOUBLE PRECISION,
    producao_media_carne DOUBLE PRECISION,
    procriacoes INTEGER,
    ultima_procriacao DATE,
    produzindo_leite BOOLEAN DEFAULT FALSE,
    destino VARCHAR(40),
    usuario_id BIGINT REFERENCES usuario(id)
);

CREATE TABLE semen (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    raca VARCHAR(60),
    especie VARCHAR(60),
    peso DOUBLE PRECISION,
    grau_sangue VARCHAR(20),
    preco DOUBLE PRECISION,
    fornecedor VARCHAR(120)
);

CREATE TABLE predicao (
    id BIGSERIAL PRIMARY KEY,
    vaca_id BIGINT,
    vaca_nome VARCHAR(80),
    semen_id BIGINT,
    semen_codigo VARCHAR(20),
    score_genetico INTEGER,
    producao_leite_estimada DOUBLE PRECISION,
    ganho_peso_estimado DOUBLE PRECISION,
    indice_fertilidade INTEGER,
    tipo_cruzamento VARCHAR(120),
    resultado_previsto VARCHAR(60),
    recomendacao VARCHAR(1000),
    criado_em TIMESTAMP
);
