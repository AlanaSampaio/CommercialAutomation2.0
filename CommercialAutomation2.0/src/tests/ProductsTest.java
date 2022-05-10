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
		
		assertEquals("melancia", productTest.getName(), "Verificação do nome");
		productTest.setName("banana");
		assertEquals("banana", productTest.getName(), "Verificação do novo nome");
		
		assertEquals(new BigDecimal("3.99"), productTest.getPrice(), "Verificação do preço");
		productTest.setPrice(new BigDecimal("5.43"));
		assertEquals(new BigDecimal("5.43"), productTest.getPrice(), "Verificação do novo preço");
		
		assertEquals(10, productTest.getQuantity(), "Verificação da quantidade");
		productTest.setQuantity(5);
		assertEquals(5, productTest.getQuantity(), "Verificação da nova quantidade");
		
		assertEquals(date1, productTest.getValidity(), "Verificação da validade");
		productTest.setValidity(date2);
		assertEquals(date2, productTest.getValidity(), "Verificação da nova validade");
		
		assertEquals(providerTest1, productTest.getProvider(), "Verificação do fornecedor");
		productTest.setProvider(providerTest2);
		assertEquals(providerTest2, productTest.getProvider(), "Verificação do novo fornecedor");
	}

}
