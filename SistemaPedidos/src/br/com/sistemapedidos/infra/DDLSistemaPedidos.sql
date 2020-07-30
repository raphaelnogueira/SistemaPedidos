-- Banco de dados postgres

CREATE TABLE clientes(
    id serial PRIMARY KEY,
    nome varchar(30) NOT NULL,
    sobrenome varchar(50) NOT NULL,
    cpf varchar(15) NOT NULL
);

CREATE TABLE pedidos(
    id serial PRIMARY KEY,
    id_cliente integer NOT NULL,
    data date NOT NULL ,
    CONSTRAINT pedidos_id_cliente_fkey FOREIGN KEY(id_cliente)
        REFERENCES clientes(id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE produtos(
    id serial PRIMARY KEY,
    descricao varchar(45) NOT NULL
);

CREATE TABLE item_do_pedido(
    id_pedido integer NOT NULL,
    id_produto integer NOT NULL,
    quantidade integer NOT NULL,
    PRIMARY KEY(id_pedido, id_produto),
    CONSTRAINT item_do_pedido_id_pedido_fkey FOREIGN KEY(id_pedido)
        REFERENCES pedidos(id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT item_do_pedido_id_produto_fkey FOREIGN KEY(id_produto)
        REFERENCES produtos(id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

