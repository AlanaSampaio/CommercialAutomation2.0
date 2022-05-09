package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.ManagementSales;
import modeling_models.*;

class ManagementSalesTest {
	private ManagementSales managSalesTest;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static LocalDate date1;
    private static LocalDate date2;
    private static LocalTime hour1;
    private static LocalTime hour2;
    private static Items itemTest1;
	private static Items itemTest2;
    private static ArrayList<Items> itemsPurchased1;
    private static ArrayList<Items> itemsPurchased2;
	
	@BeforeEach
	void createManageAndItemsForTest() {
		managSalesTest = new ManagementSales();
		
	    date1 = LocalDate.parse("12/03/2022", dateFormatter);
	    date2 = LocalDate.parse("13/01/2023", dateFormatter);
	    
	    hour1 = LocalTime.parse("12:13", timeFormatter);
	    hour2 = LocalTime.parse("13:13", timeFormatter);
		
		HashMap<String, Integer> compositionTest1 = new HashMap<String, Integer> ();
		HashMap<String, Integer> compositionTest2 = new HashMap<String, Integer> ();
		
		compositionTest1.put("batata", 5);
		compositionTest1.put("sal", 10);
		
		compositionTest2.put("tomate", 5);
		compositionTest2.put("sal", 5);
		
		itemTest1 = new Items("por��o de batata", "batatas fritas", new BigDecimal("15.00"), "entrada", compositionTest1);
		itemTest2 = new Items("salada", "salada de tomate", new BigDecimal("5.00"), "salada", compositionTest2);
		
		itemsPurchased1 = new ArrayList<Items>();
		itemsPurchased2 = new ArrayList<Items>();
	}

	@Test
	void testRegisterNewSale() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
		Sales sale1 = (Sales) managSalesTest.searchEntities(idTest1);
		
		assertEquals(1, managSalesTest.getList().size(), "Tamanho com uma adi��o.");
		
		assertEquals(date1, sale1.getDay(), "Certifica que � o dia da venda inserida.");
		assertEquals(hour1, sale1.getHour(), "Certifica que � o hor�rios da venda inserida.");
		assertSame(itemsPurchased1, sale1.getItemsPurchased(), "Certifica que s�o os itens da venda inserida.");
		assertEquals("PIX", sale1.getPaymentMethod(), "Certifica que � o modo de pagamento da venda inserida.");
		
		
		String idTest2 = managSalesTest.register(date2, hour2, itemsPurchased2, "Dinheiro");
		Sales sale2 = (Sales) managSalesTest.searchEntities(idTest2);
		
		assertEquals(2, managSalesTest.getList().size(), "Tamanho com duas adi��es.");
		
		assertEquals(date2, sale2.getDay(), "Certifica que � o dia da venda inserida.");
		assertEquals(hour2, sale2.getHour(), "Certifica que � o hor�rios da venda inserida.");
		assertSame(itemsPurchased2, sale2.getItemsPurchased(), "Certifica que s�o os itens da venda inserida.");
		assertEquals("Dinheiro", sale2.getPaymentMethod(), "Certifica que � o modo de pagamento da venda inserida.");
	}
	
	@Test
	void testSalesDataEditing() throws IdDoesntExist, EntitiesNotRegistred {
		LocalDate date3 = LocalDate.parse("12/12/2022", dateFormatter);
		LocalTime hour3 = LocalTime.parse("12:59", timeFormatter);
		
		String idTest1 = managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
		
		Sales sale1 = (Sales) managSalesTest.searchEntities(idTest1);
		
		managSalesTest.edit(idTest1, "dia", date3);
		assertEquals(date3, sale1.getDay(), "Mudan�a no dia da venda.");
		
		managSalesTest.edit(idTest1, "horario", hour3);
		assertEquals(hour3, sale1.getHour(), "Mudan�a no hor�rio da venda.");
		
		managSalesTest.edit(idTest1, "modoDePagamento", "Cart�o");
		assertEquals("Cart�o", sale1.getPaymentMethod(), "Mudan�a no pre�o da venda.");
	}
	
	@Test
	void testaAddingItemToSale() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
		
		Sales sale1 = (Sales) managSalesTest.searchEntities(idTest1);
		
		managSalesTest.addItem(idTest1, itemTest1);
		assertTrue(sale1.getItemsPurchased().contains(itemTest1), "1� item adicionado � venda.");
		
		managSalesTest.addItem(idTest1, itemTest2);
		assertTrue(sale1.getItemsPurchased().contains(itemTest2), "2� item adicionado � venda.");
		
		assertEquals(2, sale1.getItemsPurchased().size(), "Tamanho da lista de itens vendidos da venda");
	}
	
	@Test
	void testaRemoveItemFromSale() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
		
		Sales sale1 = (Sales) managSalesTest.searchEntities(idTest1);
		
		managSalesTest.addItem(idTest1, itemTest1);
		managSalesTest.addItem(idTest1, itemTest2);
		
		managSalesTest.deleteItem(idTest1, itemTest1);
		assertFalse(sale1.getItemsPurchased().contains(itemTest1), "1� item removido da venda.");
		
		managSalesTest.deleteItem(idTest1, itemTest2);
		assertFalse(managSalesTest.getList().contains(sale1), "Venda removida por n�o ter nenhum item associado");
	}
	
	@Test
	void testExceptions() {
		assertThrows(EntitiesNotRegistred.class, () ->{
			System.out.println(managSalesTest.getList().size());
			managSalesTest.edit("ID-123", "horario", hour1);
		}, "Exception ao tentar editar sem que nada tenha sido cadastrado.");
		
		assertDoesNotThrow(()->{
			String idTest1 = managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
			managSalesTest.addItem(idTest1, itemTest1);
			managSalesTest.list(true);
		}, "N�o gera erro ao exibir a lista das vendas cadastradas");
		
		assertDoesNotThrow(()->{
			managSalesTest.list(true);
		}, "N�o gera erro ao tentar exibir a lista sem nenhuma venda cadastrada");
		
		assertThrows(IdDoesntExist.class, () ->{
			managSalesTest.register(date1, hour1, itemsPurchased1, "PIX");
			managSalesTest.searchEntities("11111111");
		}, "Exception ao tentar pesquisar ID n�o existente.");
	}
}
