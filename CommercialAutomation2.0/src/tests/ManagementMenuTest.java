package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import management_models.ManagementMenu;
import modelagem.gerenciamento.GerenciaCardapio;
import modelagem.modelos.Fornecedores;
import modelagem.modelos.Itens;
import modelagem.modelos.Produtos;
import modeling_models.Products;
import modeling_models.Providers;

class ManagementMenuTest {
	private ArrayList<Products> compositionTest1;
	private ArrayList<Products> compositionTest2;
	private ManagementMenu managMenuTest;
	private Products prodTest1;
	private Products prodTest2;
	private Providers providerTest;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
	
	@BeforeEach
	void initializeAndKeepVariablesForTesting() {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    LocalDate date2 = LocalDate.parse("13/01/2023", dateTimeFormatter);
		managMenuTest= new ManagementMenu();
		
		providerTest = new Providers("empresa ltda", "123456789", "centro");
		
		prodTest1 = new Products("batata", new BigDecimal("1.50"), date1, 10, providerTest);
		prodTest2 = new Products("sal", new BigDecimal("0.50"), date2, 20, providerTest);
		
		compositionTest1 = new ArrayList<Products>();
		compositionTest1 = new ArrayList<Products>();
	}

	@Test
	void testaCadastroDeNovoItemNoCardapio() {
		gerenCardTeste.cadastrar("porção de batata", "batatas fritas", "13.99", "entrada", composicaoTeste1);
		
		String idTeste1 = gerenCardTeste.listar().get(0).getId();
		Itens item1 = (Itens) gerenCardTeste.buscaEntidade(idTeste1);
		
		assertEquals(1, gerenCardTeste.listar().size(), "Tamanho com uma adição.");
		assertEquals("porção de batata", item1.getNome(), "Certifica que é o objeto inserido.");
		
		gerenCardTeste.cadastrar("molho de tomate", "porção com molho de tomare", "3.50", "molho", composicaoTeste2);
		
		String idTeste2 = gerenCardTeste.listar().get(1).getId();
		Itens item2 = (Itens) gerenCardTeste.buscaEntidade(idTeste2);
		
		assertEquals(2, gerenCardTeste.listar().size(), "Tamanho com duas adições.");
		assertEquals("molho de tomate", item2.getNome(), "Certifica que é o objeto inserido.");
	}
	
	@Test
	void testaDeletarUmItemDoCardapio() {
		gerenCardTeste.cadastrar("porção de batata", "batatas fritas", "13.99", "entrada", composicaoTeste1);
		gerenCardTeste.cadastrar("molho de tomate", "porção com molho de tomare", "3.50", "molho", composicaoTeste2);
		
		String idTeste1 = gerenCardTeste.listar().get(0).getId();
		String idTeste2 = gerenCardTeste.listar().get(1).getId();
		
		Itens item1 = (Itens) gerenCardTeste.buscaEntidade(idTeste1);
		Itens item2 = (Itens) gerenCardTeste.buscaEntidade(idTeste2);
		
		assertTrue(gerenCardTeste.listar().contains(item2), "Item 2 está presente na lista.");
		gerenCardTeste.deletar(idTeste2);
		assertFalse(gerenCardTeste.listar().contains(item2), "Item 2 foi removido da lista.");
		
		assertTrue(gerenCardTeste.listar().contains(item1), "Item 1 está presente na lista.");
		gerenCardTeste.deletar(idTeste1);
		assertFalse(gerenCardTeste.listar().contains(item1), "Item 1 foi removido da lista.");
	}
	
	@Test
	void testaAEdicaoDasInformacoesDeUmItem() {
		gerenCardTeste.cadastrar("porção de batata", "batatas fritas", "13.99", "entrada", composicaoTeste1);
		
		String idTeste1 = gerenCardTeste.listar().get(0).getId();
		
		Itens item1 = (Itens) gerenCardTeste.listar().get(0);
		
		gerenCardTeste.editar(idTeste1, "nome", "french fries");
		assertEquals("french fries", item1.getNome(), "Mudança no nome do item.");
		
		gerenCardTeste.editar(idTeste1, "descricao", "potatos");
		assertEquals("potatos", item1.getDescricao(), "Mudança na descrição do item.");
		
		gerenCardTeste.editar(idTeste1, "preco", "14.00");
		assertEquals(new BigDecimal("14.00"), item1.getPreco(), "Mudança no preço do item.");
		
		gerenCardTeste.editar(idTeste1, "categoria", "petisco");
		assertEquals("petisco", item1.getCategoria(), "Mudança na descrição do item.");
	}
	
	@Test
	void testaAdicaoDeProdutoEmUmItem() {
		gerenCardTeste.cadastrar("porção de batata", "batatas fritas", "13.99", "entrada", composicaoTeste1);
		gerenCardTeste.cadastrar("molho de tomate", "porção com molho de tomare", "3.50", "molho", composicaoTeste2);
		
		String idTeste1 = gerenCardTeste.listar().get(0).getId();
		
		
		Itens item1 = (Itens) gerenCardTeste.listar().get(0);
		
		
		gerenCardTeste.addProdutoDeItem(idTeste1, produtosTeste1);
		assertTrue(item1.getComposicao().contains(produtosTeste1), "1º produto adicionado ao item.");
		
		gerenCardTeste.addProdutoDeItem(idTeste1, produtosTeste2);
		assertTrue(item1.getComposicao().contains(produtosTeste2), "2º produto adicionado ao item.");
		
		assertEquals(2, item1.getComposicao().size(), "Tamanho da composição do item");
	}
	
	@Test
	void testaRemocaoDeProdutoEmUmItem() {
		gerenCardTeste.cadastrar("porção de batata", "batatas fritas", "13.99", "entrada", composicaoTeste1);
		gerenCardTeste.cadastrar("molho de tomate", "porção com molho de tomare", "3.50", "molho", composicaoTeste2);
		
		String idTeste1 = gerenCardTeste.listar().get(0).getId();
		
		Itens item1 = (Itens) gerenCardTeste.buscaEntidade(idTeste1);
		
		gerenCardTeste.addProdutoDeItem(idTeste1, produtosTeste1);
		gerenCardTeste.addProdutoDeItem(idTeste1, produtosTeste2);
		
		gerenCardTeste.removerProdutoDeItem(idTeste1, produtosTeste1);
		assertFalse(item1.getComposicao().contains(produtosTeste1), "1º produto removido do item.");
		
		gerenCardTeste.removerProdutoDeItem(idTeste1, produtosTeste2);
		assertFalse(gerenCardTeste.listar().contains(item1), "Item removido do cardápio por não ter mais produtos");
	}

}
