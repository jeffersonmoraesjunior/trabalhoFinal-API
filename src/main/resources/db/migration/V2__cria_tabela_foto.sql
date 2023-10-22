CREATE TABLE foto (
	id_foto SERIAL PRIMARY KEY,
	dados oid,
	tipo VARCHAR(100),
	nome VARCHAR(100),
	user_id INTEGER,
	FOREIGN KEY (user_id) REFERENCES usuario (id_user)
);