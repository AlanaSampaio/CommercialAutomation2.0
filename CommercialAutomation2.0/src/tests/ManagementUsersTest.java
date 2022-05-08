package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EntitiesNotRegistred;
import exceptions.ExistentNicknameException;
import exceptions.IdDoesntExist;
import exceptions.LoginDoesntMatch;
import management_models.ManagementUsers;
import modeling_models.Users;

class ManagementUsersTest {
	private ManagementUsers managUser;
	
	@BeforeEach
	void addManager() {
		managUser = new ManagementUsers();
	}
	
	@Test
	void testExistNicknameException() throws ExistentNicknameException, IdDoesntExist, EntitiesNotRegistred {
		managUser.register("josee", "abcdefgh", "Jose Souza", "Gerente");
		assertThrows(ExistentNicknameException.class, () -> managUser.register("josee", "abcdef", "Jose", "Gerente"), "Verificando se o nickname 'josee' existe.");
		
		managUser.register("carlinhos21", "caca123", "Carlos", "Funcionario");
		assertThrows(ExistentNicknameException.class, () -> managUser.register("carlinhos21", "qwer123", "Carlos Almeida", "Gerente"), "Verificando se o nickname 'carlinhos21' existe.");
	}
	
	@Test
	void nicknameAndPasswordCheckForLogin() throws ExistentNicknameException {
		managUser.register("carlinhos21", "caca1243", "Carlos", "Funcionario");
		assertThrows(LoginDoesntMatch.class, () -> managUser.checkLogin("carlinhos21", "caca123"), "Verificando se as informacoes do login 'senha' são verdadeiras.");
		
		managUser.register("melissazinha", "abcd1234", "Melissa", "Gerente");
		assertThrows(LoginDoesntMatch.class, () -> managUser.checkLogin("melisazinha", "abcd1234"), "Verificando se as informacoes do login 'nickname' são verdadeiras.");
	}
	
	@Test
	void testRegistrationOfANewUser() throws ExistentNicknameException, IdDoesntExist, EntitiesNotRegistred {
		managUser.register("joao12", "abc123", "Joao Silva", "Funcionario");
		assertEquals(1, managUser.getList().size(),"Tamanho da lista de usuarios apos uma adicao." );
	
		String idTest1 = managUser.getList().get(0).getId();
		
		Users userTest1 = (Users) managUser.searchEntities(idTest1);
		assertNotNull(userTest1, "Certifica que o id existe.");
		
		assertEquals("joao12", userTest1.getNickname(), "Certificar que o nickname 'joao12' foi cadastrado certo.");
		assertEquals("abc123", userTest1.getPassword(), "Certificar que a senha 'abc123' foi registrado certo.");
		assertEquals("Joao Silva", userTest1.getName(), "Certificar que o nome 'Joao Silva' foi cadastrado certo.");
		assertEquals("Funcionario", userTest1.getCategory(), "Certificar que a categoria 'Funcionario' foi salva corretamente.");
		
		managUser.register("maria_5", "yzw456", "Maria", "Gerente");
		assertEquals(2, managUser.getList().size(),"Tamanho da lista de usuarios apos duas adicoes.");
		
		String idTest2 = managUser.getList().get(1).getId();
		
		Users userTest2 = (Users) managUser.searchEntities(idTest2);
		assertNotNull(userTest2, "Certifica que o id existe.");
	
		assertEquals("maria_5", userTest2.getNickname(), "Certificar que o nickname 'maria_5' foi cadastrado certo.");
		assertEquals("yzw456", userTest2.getPassword(), "Certificar que a senha 'yzw456' foi registrado certo.");
		assertEquals("Maria", userTest2.getName(), "Certificar que o nome 'Maria' foi cadastrado certo.");
		assertEquals("Gerente", userTest2.getCategory(), "Certificar que a categoria 'Gerente' foi salva corretamente.");
	}
	
	@Test
	void testEditingAUserInformation() throws ExistentNicknameException, IdDoesntExist, EntitiesNotRegistred {
		managUser.register("melissazinha", "abcd1234", "Melissa", "Gerente");
		String idTest1 = managUser.getList().get(0).getId();
		Users userTest1 = (Users) managUser.searchEntities(idTest1);
		managUser.edit(idTest1, "nome", "Mel Marinho");
		assertEquals("Mel Marinho", userTest1.getName(), "Mudanca de nome do usuario para 'Mel Marinho'.");
		
		managUser.register("nando", "nandinho", "Fernando", "Gerente");
		String idTest2 = managUser.getList().get(1).getId();
		Users userTest2 = (Users) managUser.searchEntities(idTest2);
		managUser.edit(idTest2, "cargo", "Funcionario");
		assertEquals("Funcionario", userTest2.getCategory(), "Mudanca de cargo do usuario para 'Funcionario'.");
		
		managUser.register("nandinha1", "fer789", "Fernanda", "Funcionario");
		String idTest3 = managUser.getList().get(2).getId();
		Users userTest3 = (Users) managUser.searchEntities(idTest3);
		managUser.edit(idTest3, "nickname", "nandainha1");
		assertEquals("nandainha1", userTest3.getNickname(), "Mudanca de nickname do usuario para 'Funcionario'.");
		
		managUser.register("lulu11", "lu11", "Luiza", "Funcionario");
		String idTest4 = managUser.getList().get(3).getId();
		Users userTest4 = (Users) managUser.searchEntities(idTest4);
		managUser.edit(idTest4, "senha", "0011abc");
		assertEquals("0011abc", userTest4.getPassword(), "Mudança de senha do usuario para '0011abc'");
	}

	@Test
	void testIfTheListIsEmptyOrNot() throws ExistentNicknameException {
		assertFalse(managUser.checkSizeList(), "Verifica se a lista esta vazia");
		
		managUser.register("nando", "nandinho", "Fernando", "Gerente");
		assertTrue(managUser.checkSizeList(), "Verifica se a lista não esta vazia");
	}
}
