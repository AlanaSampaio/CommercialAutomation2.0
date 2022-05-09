package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modeling_models.Items;
import modeling_models.Products;
import modeling_models.Providers;
import modeling_models.Sales;

class SalesTest {
	private static Items itemTest1;
	private static Items itemTest2;
	private static Sales saleTest;
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    		.withResolverStyle(ResolverStyle.STRICT); 
	
	@BeforeAll
	static void creationOfTheItemsToComposeTheSales() {
		LocalDate date1 = LocalDate.parse("15/08/2022", dateTimeFormatter);
		LocalDate date2 = LocalDate.parse("12/09/2023", dateTimeFormatter);
		LocalDate date3 = LocalDate.parse("30/05/2022", dateTimeFormatter);
		
		Providers providerTest = new Providers("Empresa ltda", "123456789", "Rua A");
		Products productTest1 = new Products("batata", new BigDecimal("1.50"), date1, 10, providerTest );
		Products productTest2 = new Products("sal", new BigDecimal("0.50"), date2, 5, providerTest );
		Products productTest3 = new Products("tomate", new BigDecimal("2.50"), date3, 23, providerTest );
		
		HashMap<String, Integer> compositionTest1 = new HashMap<String, Integer>();
		HashMap<String, Integer> compositionTest2 = new HashMap<String, Integer>();
		
		itemTest1 = new Items("porcao de batata", "batata frita", new BigDecimal("23.50"), "entrada", compositionTest1);
		itemTest1.addProduct(3, "batata");
		itemTest1.addProduct(1, "sal");
		itemTest1.addProduct(4, "tomate");
		
		itemTest2 = new Items("molho de tomate", "porcao P de molho de tomate", new BigDecimal("3.50"), "acompanhamento", compositionTest2);
		itemTest2.addProduct(1, "sal");
		itemTest2.addProduct(10, "tomate");
	}
	
	@BeforeEach
	void createTestSales() {
		LocalDate date = LocalDate.parse("15/08/2022", dateTimeFormatter);
		LocalTime time = LocalTime.parse("13:13", timeFormatter);
		
		ArrayList<Items> cart = new ArrayList<Items>();
		saleTest = new Sales(date, time, "PIX", cart);
	}
	
	@Test
	void testAddItemsShoppingCart() {
		saleTest.addItem(itemTest1);
		assertTrue(saleTest.getItemsPurchased().contains(itemTest1), "Verifica primeiro item adicionado na lista");
		assertEquals(1, saleTest.getItemsPurchased().size(), "Tamanho do carrinho apos a adicao de um item");
		
		saleTest.addItem(itemTest2);
		assertTrue(saleTest.getItemsPurchased().contains(itemTest2), "Verifica segundo item adicionado na lista");
		assertEquals(2, saleTest.getItemsPurchased().size(), "Tamanho do carrinho apos a adicao de dois item");
	
	}
	
	@Test
	void testRemovingItemsFromCart() {
		saleTest.addItem(itemTest1);
		saleTest.addItem(itemTest2);
		assertEquals(2, saleTest.getItemsPurchased().size(), "Quantidade dois de itens adicionados ao carrinho");
		
		saleTest.deleteItem(itemTest1);
		assertFalse(saleTest.getItemsPurchased().contains(itemTest1));
		assertEquals(1, saleTest.getItemsPurchased().size(), "Quantidade de um item apos a remocao de um item");
		
		saleTest.deleteItem(itemTest2);
		assertFalse(saleTest.getItemsPurchased().contains(itemTest2));
		assertEquals(0, saleTest.getItemsPurchased().size(), "Quantidade de um item apos a remocao de um item");
	}
	
	@Test
	void foreheadSumOfThePricesOfTheItemsInTheCart() {
		assertEquals(new BigDecimal("0"), saleTest.getPriceTotal(), "Soma dos precos do carrinho vazio");
		
		saleTest.addItem(itemTest1);
		assertEquals(new BigDecimal("23.50"), saleTest.getPriceTotal(), "Soma dos precos do carrinho com um item");

		saleTest.addItem(itemTest1);
		assertEquals(new BigDecimal("47.00"), saleTest.getPriceTotal(), "Soma dos precos do carrinho com dois itens");

	}

	@Test
	void testExchangeValuesManually() {
		LocalDate date = LocalDate.parse("02/08/2022", dateTimeFormatter);
		LocalTime time = LocalTime.parse("15:13", timeFormatter);
		ArrayList<Items> cart = new ArrayList<Items>();
		
		LocalDate date1 = LocalDate.parse("02/08/2022", dateTimeFormatter);
		LocalTime time1 = LocalTime.parse("15:13", timeFormatter);
		
		saleTest.setDay(date);
		assertEquals(date1, saleTest.getDay(), "Verifica se o valor foi trocado para '02/08/2022'");
		
		saleTest.setHour(time);
		assertEquals(time1, saleTest.getHour(), "Verifica se o valor foi trocado para '15:13'");
		
		saleTest.setPaymentMethod("Dinheiro");
		assertEquals("Dinheiro", saleTest.getPaymentMethod(), "Verifica se o valor foi trocado para 'Dinheiro'");
		saleTest.setPriceTotal(new BigDecimal("55.00"));
		
		assertFalse(saleTest.getItemsPurchased().contains(cart), "Verifica se a lista esta correta");
		
	
	}
	
}
