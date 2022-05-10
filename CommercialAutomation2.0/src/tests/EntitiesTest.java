package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.Test;

import main.Main;
import modeling_models.Products;
import modeling_models.Providers;
import modeling_models.Users;

class EntitiesTest {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);

	Users userTest = new Users("luizi25", "abc123", "Luiza", "gerente");
	Providers provTest = new Providers("Empresa 1", "123789654", "Centro");
	LocalDate data = LocalDate.parse("20/12/2022", dateTimeFormatter);
	Products prodTest = new Products("batata", new BigDecimal("5.25"), data, 25, provTest);
	
	@Test
	void testsTwoEqualObjectsWithDifferentIDs() {
		Users userTest2 = new Users("caca1", "1234", "Carla", "gerente");
		assertNotEquals(userTest.getId(), userTest2.getId(), "Verifica se nao possui ID iguais em usuarios");
		
		Providers provTest2 = new Providers("Empresa 2", "123780014", "Rua Alameda");
		assertNotEquals(provTest.getId(), provTest2.getId(), "Verifica se nao possui ID iguais em fornecedores");

		Products prodTest2 = new Products("tomate", new BigDecimal("2.95"), data, 30, provTest);
		assertNotEquals(prodTest.getId(), prodTest2.getId(), "Verifica se nao possui ID iguais em produtos");

	}

	@Test
	void TestPreFixedNoSingleCode() {
		String firstUserCharID = userTest.getId().split("-")[0];
		assertEquals("U", firstUserCharID, "Verifica o uso correto do pre-fixo em usuario");
		
		String firstProvCharID = provTest.getId().split("-")[0];
		assertEquals("F", firstProvCharID, "Verifica o uso correto do pre-fixo em fornecedor");
		
		String firstProdCharID = prodTest.getId().split("-")[0];
		assertEquals("P", firstProdCharID, "Verifica o uso correto do pre-fixo em produto");
	}
	
	@Test
	void testsIDGeneration() {
		String expected = "U-" + String.valueOf(userTest.hashCode());
		assertEquals(expected, userTest.getId(), "Geracao de ID do usuario");
		
		String expected1 = "P-" + String.valueOf(prodTest.hashCode());
		assertEquals(expected1, prodTest.getId(), "Geracao de ID do produto");
		
		String expected2 = "F-" + String.valueOf(provTest.hashCode());
		assertEquals(expected2, provTest.getId(), "Geracao de ID do fornecedor");
	}
	
	@Test
	void testIDValidation() {
		Main.addId("U-12345");
		
		assertEquals("U-12344", userTest.validateCode("U-12344"), "Primeira validacao de um ID nao cadastrado");
		assertEquals("U-12346", userTest.validateCode("U-12346"), "Segunda validacao de um ID nao cadastrado");
	
		assertEquals("U-112345", userTest.validateCode("U-12345"), "Validacao de um ID ja cadastrado");
		
		Main.addId("U-112345");
		
		assertEquals("U-212345", userTest.validateCode("U-12345"), "Validacao de um codigo e seu sucessor ja cadastrado");
	}
}
