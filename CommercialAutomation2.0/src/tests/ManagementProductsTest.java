/**
 * 
 */
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
import exceptions.NotEnoughStock;
import management_models.ManagementProducts;
import modeling_models.Products;
import modeling_models.Providers;

/**
 * @author vande
 *
 */
class ManagementProductsTest {
	private static ManagementProducts managProdTest;
	private static Providers providerTest1;
	private static Providers providerTest2;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
	
	@BeforeEach
	void createManageAndProviderForTest() {
		managProdTest = new ManagementProducts();
		providerTest1 = new Providers("Frutas ltda", "23456789", "Pra�a");
		providerTest2 = new Providers("Empresa ltda", "12345678", "Centro");
	}
	
	@Test
	void testRegisterNewProduct() throws IdDoesntExist, EntitiesNotRegistred {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    LocalDate date2 = LocalDate.parse("13/01/2023", dateTimeFormatter);
	    String idTest1 = managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
		assertEquals(1, managProdTest.getList().size(), "Tamanho da lista de produtos ap�s um cadastro.");
		
		
		Products productTest1 = (Products) managProdTest.searchEntities(idTest1);
		
		assertEquals("ma��", productTest1.getName(), "Certificar que o nome foi cadastrado certo.");
		assertEquals(new BigDecimal("1.23"), productTest1.getPrice(), "Certificar que o pre�o foi cadastrado certo.");
		assertEquals(date1, productTest1.getValidity(), "Certificar que a validade foi cadastrado certo.");
		assertEquals(providerTest1, productTest1.getProvider(), "Certificar que o fornecedor foi cadastrado certo.");
		
		
		String idTest2 = managProdTest.register("abacate", new BigDecimal("0.13"), date2, 20, providerTest1);
		assertEquals(2, managProdTest.getList().size(), "Tamanho da lista de produtos ap�s um cadastro.");
		
		Products productTest2 = (Products) managProdTest.searchEntities(idTest2);
		
		assertEquals("abacate", productTest2.getName(), "Certificar que o nome foi cadastrado certo.");
		assertEquals(new BigDecimal("0.13"), productTest2.getPrice(), "Certificar que o pre�o foi cadastrado certo.");
		assertEquals(date2, productTest2.getValidity(), "Certificar que a validade foi cadastrada certo.");
		assertEquals(providerTest1, productTest2.getProvider(), "Certificar que o fornecedor foi cadastrado certo.");
	}
	
	@Test
	void testProductsDataEditing() throws IdDoesntExist, EntitiesNotRegistred {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    LocalDate date2 = LocalDate.parse("13/01/2023", dateTimeFormatter);
	    
	    String idTest1 = managProdTest.register("banana", new BigDecimal("1.23"), date1, 10, providerTest1);
		
		
		Products productTest1 = (Products) managProdTest.searchEntities(idTest1);
		
		managProdTest.edit(idTest1, "nome", "ma��");
		assertEquals("ma��", productTest1.getName(), "Mudan�a no nome do produto.");
		
		managProdTest.edit(idTest1, "preco", new BigDecimal("0.13"));
		assertEquals(new BigDecimal("0.13"), productTest1.getPrice(), "Mudan�a no pre�o do produto.");
		
		managProdTest.edit(idTest1, "validade", date2);
		assertEquals(date2, productTest1.getValidity(), "Mudan�a no validade do produto.");
		
		managProdTest.edit(idTest1, "fornecedor", providerTest2);
		assertEquals(providerTest2, productTest1.getProvider(), "Mudan�a no fornecedor do produto.");
		
		managProdTest.edit(idTest1, "quantidade", 20);
		assertEquals(20, productTest1.getQuantity(), "Mudan�a na quantidade do produto.");
	}
	
	@Test
	void testNewProductInStock() throws IdDoesntExist, EntitiesNotRegistred {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    String idTeste1 = managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
	    Products productTest1 = (Products) managProdTest.searchEntities(idTeste1);
	    
	    ArrayList<Products> group1 = managProdTest.getStock().get("ma��");
	    assertTrue(group1.contains(productTest1), "Primeiro produto foi inserido no grupo do seu nome");
	    
	    String idTeste2 = managProdTest.register("batata", new BigDecimal("2.34"), date1, 12, providerTest2);
	    Products productTest2 = (Products) managProdTest.searchEntities(idTeste2);
	    
	    ArrayList<Products> group2 = managProdTest.getStock().get("batata");
	    assertTrue(group2.contains(productTest2), "Segundo produto foi inserido no grupo do seu nome");
	    
	    String idTeste3 = managProdTest.register("batata", new BigDecimal("3.45"), date1, 52, providerTest1);
	    Products productTest3 = (Products) managProdTest.searchEntities(idTeste3);
	    
	    group2 = managProdTest.getStock().get("batata");
	    assertTrue(group2.contains(productTest2) && group2.contains(productTest3), "Produtos diferentes de mesmo nome foram inseridos no mesmo grupo");
	}
	
	@Test
	void addedProductsAddQuantityGroup() throws IdDoesntExist, EntitiesNotRegistred {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    String idTeste1 = managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
	    Products productTest1 = (Products) managProdTest.searchEntities(idTeste1);
	    
	    assertEquals(10, managProdTest.getGroupQuantity(productTest1.getName()), "Quantidade do estoque do novo grupo � igual ao do produto rec�m adicionado.");
	    
	    String idTeste2 = managProdTest.register("ma��", new BigDecimal("2.34"), date1, 12, providerTest2);
	    Products productTest2 = (Products) managProdTest.searchEntities(idTeste2);
	    
	    assertEquals(22, managProdTest.getGroupQuantity(productTest2.getName()), "Nova quantidade do grupo igual a soma da quantidade dos produtos dele.");
	    
	    String idTeste3 = managProdTest.register("ma��", new BigDecimal("3.45"), date1, 52, providerTest1);
	    Products productTest3 = (Products) managProdTest.searchEntities(idTeste3);
	    
	    assertEquals(74, managProdTest.getGroupQuantity(productTest3.getName()), "Nova quantidade do grupo igual a soma da quantidade dos produtos dele.");
	}
	
	@Test
	void deletedProductremovedFromStock() throws IdDoesntExist, EntitiesNotRegistred {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    String idTeste1 = managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
	    String idTeste2 = managProdTest.register("batata", new BigDecimal("2.34"), date1, 12, providerTest2);
	    String idTeste3 = managProdTest.register("ma��", new BigDecimal("3.45"), date1, 52, providerTest1);
	   
	    Products productTest1 = (Products) managProdTest.searchEntities(idTeste1);
	    Products productTest2 = (Products) managProdTest.searchEntities(idTeste2);
	    Products productTest3 = (Products) managProdTest.searchEntities(idTeste3);
	    
	    ArrayList<Products> group1 = managProdTest.getStock().get("ma��");
	    ArrayList<Products> group2 = managProdTest.getStock().get("batata");
	    assertTrue(group1.contains(productTest1), "Produto 1 est� presente no estoque.");
	    assertTrue(group2.contains(productTest2), "Produto 2 est� presente no estoque.");
	    assertTrue(group1.contains(productTest3), "Produto 3 est� presente no estoque.");
	    
	    managProdTest.delete(productTest1);
	    assertFalse(group1.contains(productTest1), "Produto 1 foi deletado do estoque");
	    assertEquals(52, managProdTest.getGroupQuantity(productTest1.getName()), "Quantidade do produto 1 subtraida do seu grupo no estoque.");
	    
	    managProdTest.delete(productTest2);
	    assertFalse(group2.contains(productTest2), "Produto 2 foi deletado do estoque");
	    assertEquals(0, managProdTest.getGroupQuantity(productTest2.getName()), "Quantidade do produto 2 subtraida do seu grupo no estoque.");
	}
	
	@Test
	void testsUpdateStock() throws IdDoesntExist, EntitiesNotRegistred, NotEnoughStock {
	    LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	    String idTeste1 = managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
	    String idTeste2 = managProdTest.register("batata", new BigDecimal("2.34"), date1, 12, providerTest2);
	    String idTeste3 = managProdTest.register("ma��", new BigDecimal("3.45"), date1, 52, providerTest1);
	   
	    Products productTest1 = (Products) managProdTest.searchEntities(idTeste1);
	    Products productTest2 = (Products) managProdTest.searchEntities(idTeste2);
	    Products productTest3 = (Products) managProdTest.searchEntities(idTeste3);
	    
	    ArrayList<Products> group1 = managProdTest.getStock().get("ma��");
	    ArrayList<Products> group2 = managProdTest.getStock().get("batata");
	    
	    HashMap<String, Integer> groupsUsed = new HashMap<String, Integer>();
	    
	    groupsUsed.put("ma��", 30);
	    groupsUsed.put("batata", 6);
	    managProdTest.updateStock(groupsUsed);
	    
	    assertFalse(group1.contains(productTest1), "Produto 1 deletado pois toda sua quantidade foi usada");
	    assertEquals(6, productTest2.getQuantity(), "Quantidade do produto 2 reduzida");
	    assertEquals(32, productTest3.getQuantity(), "Quantidade do produto 3 reduzida");
	    
	    managProdTest.updateStock(groupsUsed);
	    assertFalse(group2.contains(productTest2), "Produto 2 deletado pois toda sua quantidade foi usada");
	    assertEquals(2, productTest3.getQuantity(), "Quantidade do produto 3 reduzida");
	    
	}
	
	@Test
	void testExceptions() throws IdDoesntExist, EntitiesNotRegistred, NotEnoughStock{
		LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
		assertThrows(IdDoesntExist.class, () -> {
			managProdTest.register("ma��", new BigDecimal("1.23"), date1, 10, providerTest1);
			managProdTest.searchEntities("Nao � um ID");
		}, "String nada parecida com ID gera erro.");
		
		assertThrows(IdDoesntExist.class, () -> {
			managProdTest.register("pera", new BigDecimal("1.23"), date1, 10, providerTest1);
			managProdTest.searchEntities("P-111111111111");
		}, "String parecida com ID gera erro.");
		
		assertThrows(NotEnoughStock.class, () -> {
			managProdTest.register("batata", new BigDecimal("1.23"), date1, 10, providerTest1);
		    HashMap<String, Integer> groupsUsed = new HashMap<String, Integer>();
		    groupsUsed.put("batata", 30);
		    managProdTest.updateStock(groupsUsed);
		}, "Tentar retirar mais produtos do que dispon�vel geral erro.");
		
		assertDoesNotThrow(() -> {
			managProdTest.list(true);
		}, "Listar todas as informa��es dos produtos.");
		
		assertDoesNotThrow(() -> {
			managProdTest.list(false);
		}, "Listar as informa��es relevantes dos produtos.");
	}
}
