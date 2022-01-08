insert into produto (id, nome, preco, descricao, ativo, data_criacao, versao) values (1, 'Kindle', 799.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', 'SIM', date_sub(sysdate(), interval 1 day), 0);
insert into produto (id, nome, preco, descricao, ativo, data_criacao, versao) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', 'SIM', date_sub(sysdate(), interval 1 day), 0);
insert into produto (id, nome, preco, descricao, ativo, data_criacao, versao) values (4, 'Câmera GoPro Hero 8', 2400.0, 'Desempenho 4x melhor.', 'SIM', date_sub(sysdate(), interval 1 day), 0);
insert into produto (id, nome, preco, data_criacao, ativo,  descricao, versao) values (5, 'Câmera Canon 80D', 3500.0, sysdate(), 'NAO', 'O melhor ajuste de foco.', 0);

insert into cliente (id, nome, cpf, versao) values (1, 'Fernando Medeiros', '000', 0);
insert into cliente (id, nome, cpf, versao) values (2, 'Marcos Mariano', '111', 0);

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year));

insert into pedido (id, cliente_id, data_criacao, total, status, versao) values (1, 1, sysdate(), 2398.0, 'AGUARDANDO', 0);
insert into pedido (id, cliente_id, data_criacao, total, status, versao) values (2, 1, sysdate(), 499.0, 'AGUARDANDO', 0);
insert into pedido (id, cliente_id, data_criacao, total, status, versao) values (4, 2, date_sub(sysdate(), interval 2 day), 499.0, 'PAGO', 0);
insert into pedido (id, cliente_id, data_criacao, total, status, versao) values (5, 1, date_sub(sysdate(), interval 2 day), 799.0, 'PAGO', 0);
insert into pedido (id, cliente_id, data_criacao, total, status, versao) values (6, 2, sysdate(), 799.0, 'AGUARDANDO', 0);

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (1, 1, 499.0, 2, 0);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (1, 3, 1400, 1, 0);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (2, 1, 499.0, 1, 0);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (4, 1, 499, 1, 0);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (5, 1, 799, 1, 0);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade, versao) values (6, 1, 799, 1, 0);

insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, versao) values (1, 'RECEBIDO', 'cartao', '0123', null, 0);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, versao) values (2, 'PROCESSANDO', 'cartao', '4567', null, 0);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, data_vencimento, versao) values (3, 'RECEBIDO', 'boleto', null, '8910', date_sub(sysdate(), interval 2 day), 0);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, versao) values (4, 'PROCESSANDO', 'cartao', '1112', null, 0);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, data_vencimento, versao) values (6, 'PROCESSANDO', 'boleto', null, '456', date_add(sysdate(), interval 2 day), 0);

insert into nota_fiscal (pedido_id, xml, data_emissao, versao) values (2, '<xml />', sysdate(), 0);

insert into categoria (id, nome, versao) values (1, 'Eletrônicos', 0);
insert into categoria (id, nome, versao) values (2, 'Livros', 0);
insert into categoria (id, nome, versao) values (3, 'Esportes', 0);
insert into categoria (id, nome, versao) values (4, 'Futebol', 0);
insert into categoria (id, nome, versao) values (5, 'Natação', 0);
insert into categoria (id, nome, versao) values (6, 'Notebooks', 0);
insert into catego0ria (id, nome, versao) values (7, 'Smartphones', 0);
insert into categoria (id, nome, versao) values (8, 'Câmeras', 0);

insert into produto_categoria (produto_id, categoria_id) values (1, 2);
insert into produto_categoria (produto_id, categoria_id) values (3, 8);
insert into produto_categoria (produto_id, categoria_id) values (4, 8);