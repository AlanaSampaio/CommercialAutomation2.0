package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modeling_models.Products;
import modeling_models.Providers;

class ProvidersTest {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
	
	@Test
	void testsTheSupplierBuild() {
		Providers providerTest = new Providers("Empresa ltda", "123456789", "Centro");
		
		assertEquals("Empresa ltda", providerTest.getName(), "Verificacao do nome");
		assertEquals("123456789", providerTest.getCnpj(), "Verificacao do CNPJ");
		assertEquals("Centro", providerTest.getAddress(), "Verificacao do endere�o");
	}
	
	@Test
	void productListingTestLinkedWithSuppliers() {
		Providers providerTest = new Providers("Empresa ltda", "123456789", "Centro");
		
		assertDoesNotThrow(() -> { 
			providerTest.listProdProvided();
		}, "Verifica se lista possui erros");
	}
	
	@Test
	void attributeListingTestOnProducts() {
		Providers providerTest = new Providers("Empresa 1 ltda", "123402789", "Rua A");
		
		assertDoesNotThrow(() -> { 
			providerTest.listProdProvided();
		}, "Verifica se lista de fornecedores possui erros com um fornecedor");
	
		Providers providerTest1 = new Providers("Empresa Laticínios ltda", "1234021009", "Rua E");
		
		assertDoesNotThrow(() -> { 
			providerTest.listProdProvided();
		}, "Verifica se lista de fornecedores possui erros com dois fornecedores");
	}
	
	@Test
	void testProductListing() {
		LocalDate data = LocalDate.parse("20/12/2022", dateTimeFormatter);
		
		Providers providerTest = new Providers("Empresa 2 ltda", "1244542789", "Rua Jurema");
		Products prod = new Products("batata", new BigDecimal("15.00"), data, 20, providerTest);
		
		providerTest.addProduct(prod);
		assertEquals(prod, providerTest.getProductsProvided().get(0), "Verifica se o valor foi inserido corretamente");
	
		Providers providerTest1 = new Providers("Empresa 221 ltda", "1244555589", "Rua Jurema");
		Products prod1 = new Products("tomate", new BigDecimal("10.00"), data, 23, providerTest1);
		
		providerTest.addProduct(prod1);
		assertEquals(prod1, providerTest.getProductsProvided().get(1), "Verifica se o valor foi inserido corretamente");
	}
	
	@Test
	void testExchangeValuesManually() {
		Providers providerTest = new Providers("Empresa 2 ltda", "1244542789", "Rua Jurema");
		providerTest.setName("Nabos e verduras ltda");
		assertEquals("Nabos e verduras ltda", providerTest.getName(), "Verificar se ocorre erros com a troca manual do nome");
		
		providerTest.setCnpj("33216548970");
		assertEquals("33216548970", providerTest.getCnpj(), "Verificar se ocorre erros com a troca manual do cnpj");
		
		providerTest.setAddress("Rua Ju");
		assertEquals("Rua Ju", providerTest.getAddress(), "Verificar se ocorre erros com a troca manual do endereco");
	}
	
	@Test 
	void testRemovalProductLinkedToProvider() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
		
		Providers providerTest = new Providers("Empresa 2 ltda", "1244542789", "Rua Jurema");
		
		Products productTest = new Products("melancia", new BigDecimal("3.99"), date1, 10, providerTest);
		
		ArrayList<Products> listProd = new ArrayList<Products>();
		listProd.add(productTest);
		
		providerTest.setProductsProvided(listProd);
		assertEquals(1, providerTest.getProductsProvided().size(), "Tamanho da lista produtos antes da remocao 1");
		
		providerTest.removeProduct(productTest.getId());
		assertEquals(0, providerTest.getProductsProvided().size(), "Tamanho da lista produtos depois da remocao 1");
		
	}
}
