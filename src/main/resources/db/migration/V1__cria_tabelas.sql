CREATE TABLE usuario (
	id_user SERIAL PRIMARY KEY,
	nome VARCHAR(60) NOT NULL,
    sobre_nome VARCHAR(60) NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(60) NOT NULL,
    senha VARCHAR(60) NOT NULL
);

CREATE TABLE post (
	id_post SERIAL PRIMARY KEY,
	conteudo TEXT NOT NULL,
	data_criacao TIMESTAMP NOT NULL,
	user_id INTEGER,
	FOREIGN KEY (user_id) REFERENCES usuario (id_user)
);

CREATE TABLE comment (
	id_comentario SERIAL PRIMARY KEY,
	texto TEXT NOT NULL,
	data_criacao  TIMESTAMP NOT NULL,
	post_id INTEGER,
	FOREIGN KEY (post_id) REFERENCES post (id_post)
);

CREATE TABLE relationship (
	id_user_seguidor INTEGER REFERENCES usuario (id_user),
	id_user_seguido INTEGER REFERENCES usuario (id_user),
	data_inicio_seguimento DATE NOT NULL,
	CONSTRAINT id_relarionship PRIMARY KEY (id_user_seguidor, id_user_seguido)
);