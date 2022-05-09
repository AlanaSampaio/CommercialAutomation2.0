package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import modeling_models.*;


class ItemsTest {
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
	LocalDate date1 = LocalDate.parse("01/01/2021", dateFormatter);
	private Providers providerTest = new Providers("empresa ltda", "123456789", "centro");
	private Products productsTest1 = new Products("batata", new BigDecimal("1.50"), date1, 5, providerTest);
	private Products productsTest2 = new Products("sal", new BigDecimal("0.50"), date1, 10, providerTest);
	private Products productsTest3 = new Products("tomate", new BigDecimal("2.50"), date1, 15, providerTest);
	private HashMap<String, Integer> composicaoTeste = new HashMap<String, Integer>();
	private Items itemTest = new Items("porção de batata", "batatas fritas", new BigDecimal("15.00"), "entrada", composicaoTeste);

	@Test
	void testAddingOneOrMoreProducts() {
		assertEquals(0, itemTest.getComposition().size(), "Tamanho da composição antes das adições");
		
		itemTest.addProduct(1, productsTest1.getName());
		assertTrue(itemTest.getComposition().containsKey(productsTest1.getName()), "Produto 1 está presente");
		
		itemTest.addProduct(2, productsTest2.getName());
		itemTest.addProduct(3, productsTest3.getName());
		
		assertTrue(itemTest.getComposition().containsKey(productsTest2.getName()), "Produto 2 está presente");
		assertTrue(itemTest.getComposition().containsKey(productsTest3.getName()), "Produto 3 está presente");
		
		assertEquals(3, itemTest.getComposition().size(), "Tamanho da composição depois das adições");
	}
	
	@Test
	void testRemovingOneOrMoreProducts(){
		itemTest.addProduct(1, productsTest1.getName());
		itemTest.addProduct(2, productsTest2.getName());
		itemTest.addProduct(3, productsTest3.getName());
		
		assertEquals(3, itemTest.getComposition().size(), "Tamanho da composição antes das remoções");
		
		itemTest.deleteProduct(productsTest1.getName());
		assertFalse(itemTest.getComposition().containsKey(productsTest1.getName()), "Produto 1 foi removido");
		
		itemTest.deleteProduct(productsTest2.getName());
		assertFalse(itemTest.getComposition().containsKey(productsTest2.getName()), "Produto 2 foi removido");
		
		itemTest.deleteProduct(productsTest3.getName());
		assertFalse(itemTest.getComposition().containsKey(productsTest3.getName()), "Produto 3 foi removido");
		
		assertEquals(0, itemTest.getComposition().size(), "Tamanho da composição depois das remoções");
	}
	
	@Test
	void testItemsBuildAndSet() {
		Items item1 = new Items("porção de batata", "batatas fritas", new BigDecimal("15.00"), "entrada", composicaoTeste);
		HashMap<String, Integer> composicaoTeste2 = new HashMap<String, Integer>();
		
		assertEquals("porção de batata", item1.getName());
		item1.setName("novo nome");
		assertEquals("novo nome", item1.getName());
		
		assertEquals("batatas fritas", item1.getDescription());
		item1.setDescription("nova descrição");
		assertEquals("nova descrição", item1.getDescription());
		
		assertEquals(new BigDecimal("15.00"), item1.getPrice());
		item1.setPrice(new BigDecimal("20.00"));
		assertEquals(new BigDecimal("20.00"), item1.getPrice());
		
		assertEquals("entrada", item1.getCategoryItems());
		item1.setCategoryItems("sobremesa");
		assertEquals("sobremesa", item1.getCategoryItems());
		
		assertEquals(composicaoTeste, item1.getComposition());
		item1.setComposition(composicaoTeste2);
		assertEquals(composicaoTeste2, item1.getComposition());
	}
}
