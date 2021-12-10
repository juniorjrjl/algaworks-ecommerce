package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.dto.CategoriaDTO;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.util.List;

@ExtendWith(EntityManagerExtension.class)
public class ConsultaNativaTest {

    @Test
    public void mapearConsultaParaDTOEmArquivoExternoExercicio(final @EManager EntityManager entityManager) {
        var query = entityManager.createNamedQuery("ecm_categoria.listar.dto");

        List<CategoriaDTO> lista = query.getResultList();

        lista.forEach(obj -> System.out.println(
                String.format("CategoriaDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarArquivoXML(final @EManager EntityManager entityManager){
        var query = entityManager.createNamedQuery("ecm_categoria.listar");
        List<Categoria> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getId() + " | " + l.getNome()));
    }

    @Test
    public void usarNamedNativeQuery02(final @EManager EntityManager entityManager){
        var query = entityManager.createNamedQuery("produtoFS.listar");
        List<Produto> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getId() + " | " + l.getNome()));
    }

    @Test
    public void usarNamedNativeQuery01(final @EManager EntityManager entityManager){
        var query = entityManager.createNamedQuery("produto.listar");
        List<Produto> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getId() + " | " + l.getNome()));
    }

    @Test
    public void usarColumnResultComDTO(final @EManager EntityManager entityManager){
        var sql = "select * from produto";
        var query = entityManager.createNativeQuery(sql, "produtoCR.ProdutoDTO");
        List<ProdutoDTO> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getId() + " | " + l.getNome()));
    }

    @Test
    public void usarFieldResult(final @EManager EntityManager entityManager){
        var sql = "select * from produto";
        var query = entityManager.createNativeQuery(sql, "produtoFS.Produto");
        List<Produto> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getId() + " | " + l.getNome()));
    }

    @Test
    public void usarSQLResultSetMapping02(final @EManager EntityManager entityManager){
        var sql = "select * from item_pedido ip join produto p on p.id = ip.produto_id";
        var query = entityManager.createNativeQuery(sql, "item_pedido-produto.ItemPedido-Produto");
        List<ItemPedido> lista = query.getResultList();
        lista.forEach(l -> System.out.println(l.getProduto() + " | " + l.getId()));
    }

    @Test
    public void usarSQLResultSetMapping01(final @EManager EntityManager entityManager){
        var sql = "select * from produto";
        var query = entityManager.createNativeQuery(sql, "produto.Produto");
        List<Produto> lista = query.getResultList();
        lista.forEach(System.out::println);
    }

    @Test
    public void passarParametros(final @EManager EntityManager entityManager){
        var sql = "select * from produto where id = :id";
        var query = entityManager.createNativeQuery(sql, Produto.class);
        query.setParameter("id", 1);
        List<Produto> lista = query.getResultList();
        lista.forEach(System.out::println);
    }

    @Test
    public void executarSQLRetornandoEntidade(final @EManager EntityManager entityManager){
        var sql = "select * from produto";
        var query = entityManager.createNativeQuery(sql, Produto.class);
        List<Produto> lista = query.getResultList();
        lista.forEach(System.out::println);
    }


    @Test
    public void executarSQL(final @EManager EntityManager entityManager){
        var sql = "select id, nome from produto";
        var query = entityManager.createNativeQuery(sql);
        List<Object[]> lista = query.getResultList();
        lista.forEach(l -> System.out.println(String.format("0 : %s | 1 : %s", l[0], l[1])));
    }

}
