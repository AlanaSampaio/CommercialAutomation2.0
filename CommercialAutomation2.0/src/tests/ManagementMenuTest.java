package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.ManagementMenu;
import modeling_models.Items;
import modeling_models.Products;
import modeling_models.Providers;

class ManagementMenuTest {
	private HashMap<String, Integer> compositionTest1;
	private HashMap<String, Integer>  compositionTest2;
	private ManagementMenu managMenuTest;
	private Products prodTest1;
	private Products prodTest2;
	private Products prodTest3;
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
		prodTest3 = new Products("tomate", new BigDecimal("5.50"), date1, 15, providerTest);
		
		compositionTest1 = new HashMap<String, Integer> ();
		compositionTest2 = new HashMap<String, Integer> ();
		
		compositionTest1.put("batata", 5);
		compositionTest1.put("sal", 10);
		
		compositionTest2.put("tomate", 5);
		compositionTest2.put("sal", 5);
	}

	@Test
	void testRegisterNewItem() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
		
		Items item1 = (Items) managMenuTest.searchEntities(idTest1);
		
		assertEquals(1, managMenuTest.getList().size(), "Tamanho com uma adição.");
		assertEquals("porção de batata", item1.getName(), "Certifica que é o nome do objeto inserido.");
		assertEquals(idTest1, item1.getId(), "Certifica que é o id do objeto inserido.");
		
		String idTest2 = managMenuTest.register("molho de tomate", "porção com molho de tomare", new BigDecimal("3.50"), "molho", compositionTest2);
		Items item2 = (Items) managMenuTest.searchEntities(idTest2);
		
		assertEquals(2, managMenuTest.getList().size(), "Tamanho com duas adições.");
		assertEquals("molho de tomate", item2.getName(), "Certifica que é o nome do objeto inserido.");
		assertEquals(idTest2, item2.getId(), "Certifica que é o id do objeto inserido.");
	}
	
	@Test
	void testDeleteAItem() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
		String idTest2 = managMenuTest.register("molho de tomate", "porção com molho de tomare", new BigDecimal("3.50"), "molho", compositionTest2);
		
		Items item1 = (Items) managMenuTest.searchEntities(idTest1);
		Items item2 = (Items) managMenuTest.searchEntities(idTest2);
	
		assertTrue(managMenuTest.getList().contains(item2), "Item 2 está presente na lista.");
		managMenuTest.delete(idTest2);
		assertFalse(managMenuTest.getList().contains(item2), "Item 2 foi removido da lista.");
		
		assertTrue(managMenuTest.getList().contains(item1), "Item 1 está presente na lista.");
		managMenuTest.delete(idTest1);
		assertFalse(managMenuTest.getList().contains(item1), "Item 1 foi removido da lista.");
	}
	
	@Test
	void testItemDataEditing() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
		Items item1 = (Items) managMenuTest.searchEntities(idTest1);
		
		managMenuTest.edit(idTest1, "nome", "french fries");
		assertEquals("french fries", item1.getName(), "Mudança no nome do item.");
		
		managMenuTest.edit(idTest1, "descricao", "potatos");
		assertEquals("potatos", item1.getDescription(), "Mudança na descrição do item.");
		
		managMenuTest.edit(idTest1, "preco", new BigDecimal("14.00"));
		assertEquals(new BigDecimal("14.00"), item1.getPrice(), "Mudança no preço do item.");
		
		managMenuTest.edit(idTest1, "categoria", "petisco");
		assertEquals("petisco", item1.getCategoryItems(), "Mudança na categoria do item.");
	}
	
	@Test
	void testAddAProductToItem() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
		Items item1 = (Items) managMenuTest.searchEntities(idTest1);
		
		managMenuTest.addProductsItems(idTest1, prodTest1, 5);
		assertTrue(item1.getComposition().containsKey(prodTest1.getName()), "1º produto adicionado ao item.");
		
		managMenuTest.addProductsItems(idTest1, prodTest2, 5);
		assertTrue(item1.getComposition().containsKey(prodTest2.getName()), "2º produto adicionado ao item.");
		
		assertEquals(2, item1.getComposition().size(), "Tamanho da composição do item");
	}
	
	@Test
	void testRemovingProductFromItem() throws IdDoesntExist, EntitiesNotRegistred {
		String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
		Items item1 = (Items) managMenuTest.searchEntities(idTest1);
		
		managMenuTest.addProductsItems(idTest1, prodTest1, 5);
		managMenuTest.addProductsItems(idTest1, prodTest2, 10);
		
		managMenuTest.removeProductFromItem(idTest1, prodTest1);
		assertFalse(item1.getComposition().containsKey(prodTest1.getName()), "1º produto removido do item.");
		
		managMenuTest.removeProductFromItem(idTest1, prodTest2);
		assertFalse(managMenuTest.getList().contains(item1), "Item removido do cardápio por não ter mais produtos");
	}
	
	@Test
	void testExceptions() throws IdDoesntExist, EntitiesNotRegistred {
		assertThrows(EntitiesNotRegistred.class, () ->{
			managMenuTest.list(true);
		});
		
		assertThrows(IdDoesntExist.class, () -> {
			managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
			managMenuTest.searchEntities("P-111111111111");
		}, "String parecida com ID gera erro por não existir.");
		
		assertDoesNotThrow(() -> {
			managMenuTest.list(false);
		}, "Listar parte das informações não gera nenhum erro");
		
		assertDoesNotThrow(() -> {
			managMenuTest.list(true);
		}, "Listar todas as informações não gera nenhum erro");
	}
}
