CREATE DATABASE postgres
WITH OWNER = postgres
     ENCODING = 'UTF-8'
     LC_COLLATE = 'Portuguese_Brazil.1252'
     LC.CTYPE='Portuguese_Brazil.1252'
     TABLESPACE=pg_default
     CONNECTION LIMIT = -1;

CREATE SCHEMA financas;

DROP TABLE financas.LANCAMENTO;
DROP TABLE financas.USUARIO;

CREATE TABLE financas.usuario (
	ID BIGSERIAL NOT NULL PRIMARY KEY,
	NOME CHARACTER VARYING(150), --CHARACTER VARYING,VARCHAR
	EMAIL CHARACTER VARYING(100),
	SENHA CHARACTER VARYING(20),
	DATA_CADASTRO DATE DEFAULT NOW()
);

CREATE TABLE financas.LANCAMENTO (
	ID BIGSERIAL NOT NULL PRIMARY KEY,
	DESCRICAO CHARACTER VARYING(100) NOT NULL,
	MES INTEGER NOT NULL,
	ANO INTEGER NOT NULL,
	VALOR NUMERIC(16,2) NOT NULL,
	TIPO CHARACTER VARYING(20) CHECK( TIPO IN ('RECEITA','DESPESA')) NOT NULL,
	STATUS CHARACTER VARYING(20) CHECK (STATUS IN ('PENDENTE', 'CANCELADO', 'EFETIVADO')) NOT NULL,
	ID_USUARIO BIGINT REFERENCES FINANCAS.USUARIO(ID) NOT NULL,
	DATA_CADASTRO DATE DEFAULT NOW()
);