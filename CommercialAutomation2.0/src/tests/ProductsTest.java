package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.Test;

import modeling_models.*;

class ProductsTest {

	@Test
	void testBuildAndSetProducts() {
	   DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
	   
	   LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
	   LocalDate date2 = LocalDate.parse("22/06/2023", dateTimeFormatter);
	   
		Providers providerTest1 = new Providers("empresa ltda", "123456789", "centro");
		Providers providerTest2 = new Providers("nova empresa ltda", "987654321", "praca");
		Products productTest = new Products("melancia", new BigDecimal("3.99"), date1, 10, providerTest1);
		
		assertEquals("melancia", productTest.getName(), "Verifica��o do nome");
		productTest.setName("banana");
		assertEquals("banana", productTest.getName(), "Verifica��o do novo nome");
		
		assertEquals(new BigDecimal("3.99"), productTest.getPrice(), "Verifica��o do pre�o");
		productTest.setPrice(new BigDecimal("5.43"));
		assertEquals(new BigDecimal("5.43"), productTest.getPrice(), "Verifica��o do novo pre�o");
		
		assertEquals(10, productTest.getQuantity(), "Verifica��o da quantidade");
		productTest.setQuantity(5);
		assertEquals(5, productTest.getQuantity(), "Verifica��o da nova quantidade");
		
		assertEquals(date1, productTest.getValidity(), "Verifica��o da validade");
		productTest.setValidity(date2);
		assertEquals(date2, productTest.getValidity(), "Verifica��o da nova validade");
		
		assertEquals(providerTest1, productTest.getProvider(), "Verifica��o do fornecedor");
		productTest.setProvider(providerTest2);
		assertEquals(providerTest2, productTest.getProvider(), "Verifica��o do novo fornecedor");
	}

}
