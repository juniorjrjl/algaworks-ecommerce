insert into produto (nome, preco, descricao) values ('Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (nome, preco, descricao) values ('Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.');

insert into cliente (nome) values ('Fernando Medeiros');
insert into cliente (nome) values ('Marcos Mariano');

insert into pedido (id, cliente_id, data_pedido, total, status) values (1, 1, sysdate(), 100.0, 'AGUARDANDO');
insert into item_pedido (id, pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 1, 5.0, 2);

insert into categoria (id, nome) values (1, 'Eletrônicos');