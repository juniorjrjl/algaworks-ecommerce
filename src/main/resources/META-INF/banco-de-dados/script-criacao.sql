create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from pedido);

alter sequence hibernate_sequence restart with 50;