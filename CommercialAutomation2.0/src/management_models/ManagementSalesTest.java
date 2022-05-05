package management_models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeling_models.Items;
import modeling_models.Products;
import modeling_models.Providers;

class ManagementSalesTest {
	private ManagementSales gerenVendasTeste;
	private Items itemTeste1;
	private Items itemTeste2;
	private ArrayList<Items> itensComprados1;
	private ArrayList<Items> itensComprados2;
	
	@BeforeEach
	void inicializarEManterVariaveisParaTeste() {
		gerenVendasTeste = new ManagementSales();
		
		Providers fornecedorTeste = new Providers("empresa ltda", "123456789", "centro");
		
		Products produtosTeste1 = new Products("batata", new BigDecimal("1.50"), new LocalDate("01/01/2021"),1, fornecedorTeste);
		Products produtosTeste2 = new Products("tomate", new BigDecimal("0.50"), "03/03/2023", fornecedorTeste);
		
		ArrayList<Products> composicaoTeste1 = new ArrayList<Products>();
		ArrayList<Products> composicaoTeste2 = new ArrayList<Products>();
		composicaoTeste1.add(produtosTeste1);
		composicaoTeste2.add(produtosTeste2);
		
		itemTeste1 = new Items("por��o de batata", "batatas fritas", new BigDecimal("15.00"), "entrada", composicaoTeste1);
		itemTeste2 = new Items("salada", "salada de tomate", new BigDecimal("5.00"), "salada", composicaoTeste2);
		
		itensComprados1 = new ArrayList<Items>();
		itensComprados2 = new ArrayList<Items>();
	}

	@Test
	void testaCadastroDeNovaVenda() {
		gerenVendasTeste.cadastrar("13/12/2022", "13:13", itensComprados1, "PIX");
		
		String idTeste1 = gerenVendasTeste.listar().get(0).getId();
		Vendas venda1 = (Vendas) gerenVendasTeste.buscaEntidade(idTeste1);
		
		assertEquals(1, gerenVendasTeste.listar().size(), "Tamanho com uma adi��o.");
		
		assertEquals("13/12/2022", venda1.getDia(), "Certifica que � o dia da venda inserida.");
		assertEquals("13:13", venda1.getHorario(), "Certifica que � o hor�rios da venda inserida.");
		assertSame(itensComprados1, venda1.getItensComprados(), "Certifica que s�o os itens da venda inserida.");
		assertEquals("PIX", venda1.getModoDePagamento(), "Certifica que � o modo de pagamento da venda inserida.");
		
		
		gerenVendasTeste.cadastrar("01/01/2023", "15:10", itensComprados2, "Dinheiro");
		
		String idTeste2 = gerenVendasTeste.listar().get(1).getId();
		Vendas venda2 = (Vendas) gerenVendasTeste.buscaEntidade(idTeste2);
		
		assertEquals(2, gerenVendasTeste.listar().size(), "Tamanho com duas adi��es.");
		
		assertEquals("01/01/2023", venda2.getDia(), "Certifica que � o dia da venda inserida.");
		assertEquals("15:10", venda2.getHorario(), "Certifica que � o hor�rios da venda inserida.");
		assertSame(itensComprados2, venda2.getItensComprados(), "Certifica que s�o os itens da venda inserida.");
		assertEquals("Dinheiro", venda2.getModoDePagamento(), "Certifica que � o modo de pagamento da venda inserida.");
	}

}
