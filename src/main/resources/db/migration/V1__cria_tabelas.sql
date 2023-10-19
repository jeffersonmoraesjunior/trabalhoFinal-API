CREATE TABLE user(
	id_user INT SERIAL PRIMARY KEY,
	nome VARCHAR(60) NOT NULL,
    sobreNome VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    dataNascimento DATE NOT NULL
);

CREATE TABLE post (
	id_post INT SERIAL PRIMARY KEY,
	conteudo TEXT NOT NULL,
	data_criacao DATE,
	user_id INT,
	FOREING KEY (user_id) REFERENCES user (id_user)
);

CREATE TABLE comment (
	id_comentario INT SERIAL PRIMARY KEY,
	text TEXT NOT NULL,
	data_criacao DATE,
	post_id INT,
	FOREING KEY (post_id) REFERENCES post (id_post)
);

CREATE TABLE relationship (
	id_user_seguidor INT REFERENCES user(id_user),
	id_user_seguido INT REFERENCES user(id_user),
	data_criacao DATE,
	CONSTRAINT id_relarionship PRIMARY KEY (id_user_seguidor, id_user_seguido)
);