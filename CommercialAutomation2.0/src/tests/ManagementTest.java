package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exceptions.EntitiesNotRegistred;
import exceptions.ExistentNicknameException;
import exceptions.IdDoesntExist;
import management_models.ManagementProducts;
import management_models.ManagementProviders;
import management_models.ManagementUsers;
import modeling_models.Products;
import modeling_models.Providers;
import modeling_models.Users;

class ManagementTest {
	private static ManagementUsers managUser;
	private static Users user;
	private static ManagementProviders managProv;
	private static Providers prov;
	private static ManagementProducts managProd;
	private static Products prod;
	
	@BeforeAll
	static void createManagementAndEntities() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		
		managUser = new ManagementUsers();
		user = new Users("joaozinho", "j123", "Joao", "Funcionario");
		
		managProv = new ManagementProviders();
		prov = new Providers("Empresa", "123456789", "centro");
		
		LocalDate time = LocalDate.parse("01/01/2023", dateFormatter);
		
		managProd = new ManagementProducts();
		prod = new Products("produto", new BigDecimal("15.25"), time, 20, prov);
	}
	
	@Test
	void testToVerifyThatExceptionsAreWorkingCorrectly() throws ExistentNicknameException, IdDoesntExist, EntitiesNotRegistred {
		Users userTest = new Users("carlinhos21", "caca123", "Carlos", "Funcionario");
		managUser.register(userTest); 
		assertThrows(IdDoesntExist.class, () -> {
			managUser.delete("U-123456987");
			}, "Verificando se o nickname 'carlinhos21' existe.");
	}

	@Test
	void testsRegistrationOfAnEntityInTheManagementList() {
		assertEquals(0, managUser.getList().size(), "Tamanho da lista antes de adicionar o usuario.");
		managUser.register(user);
		assertEquals(1, managUser.getList().size(), "Tamanho da lista depois da adicao de um usuario.");
		assertTrue(managUser.getList().contains(user), "Usuario um esta presente na lista");
		
		assertEquals(0, managProv.getList().size(), "Tamanho da lista antes de adicionar o fornecedor.");
		managProv.register(prov);
		assertEquals(1, managProv.getList().size(), "Tamanho da lista depois da adicao de um fornecedor.");
		assertTrue(managProv.getList().contains(prov), "Fornecedor esta presente na lista");
		
		assertEquals(0, managProd.getList().size(), "Tamanho da lista antes de adicionar o produto.");
		managProd.register(prod);
		assertEquals(1, managProd.getList().size(), "Tamanho da lista depois da adicao de um produto.");
		assertTrue(managProd.getList().contains(prod), "Produto esta presente na lista");
	}

	@Test
	void testsRemovingAnEntityFromTheManagementList() throws IdDoesntExist, EntitiesNotRegistred {
		Users userTest2 = new Users("n0m5", "123456", "nome", "gerente");
		Users userTest3 = new Users("n0m512", "654321", "nome12", "gerente");
		Users userTest1 = new Users("n0m2", "abc21", "nome5", "funcionario");
		
		managUser.register(userTest1);
		managUser.register(userTest2);
		managUser.register(userTest3);
		
		assertEquals(3, managUser.getList().size(), "Tamanho da lista antes das remocoes de usuario");
		
		managUser.delete(userTest2.getId());
		assertFalse(managUser.getList().contains(userTest2), "Verifica se o usuario 'nome' esta na lista");
		
		managUser.delete(userTest1.getId());
		assertFalse(managUser.getList().contains(userTest1), "Verifica se o usuario 'Joao' esta na lista");
		
		managUser.delete(userTest3.getId());
		assertFalse(managUser.getList().contains(userTest3), "Verifica se o usuario 'nome12' esta na lista");
		
		assertEquals(0, managUser.getList().size(), "Tamanho da lista depois das remocoes.");
	}
	
	
}
