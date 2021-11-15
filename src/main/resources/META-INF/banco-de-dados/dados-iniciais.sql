insert into produto (nome, preco, descricao, data_criacao) values ('Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', date_sub(sysdate(), interval 1 day));
insert into produto (nome, preco, descricao, data_criacao) values ('Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into cliente (nome, cpf) values ('Fernando Medeiros', '000');
insert into cliente (nome, cpf) values ('Marcos Mariano', '111');

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year));

insert into pedido (cliente_id, data_criacao, total, status) values (1, sysdate(), 998.0, 'AGUARDANDO');
insert into pedido (cliente_id, data_criacao, total, status) values (1, sysdate(), 499.0, 'AGUARDANDO');


insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499.0, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499.0, 1);

insert into pagamento (pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'cartao');

insert into categoria (nome) values ('Eletrônicos');
insert into categoria (id, nome) values (2, 'Livros');

insert into produto_categoria (produto_id, categoria_id) values (1, 2);