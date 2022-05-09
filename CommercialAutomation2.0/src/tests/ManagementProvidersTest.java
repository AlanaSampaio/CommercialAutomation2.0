package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.ManagementProviders;
import modeling_models.Providers;

class ManagementProvidersTest {
	private ManagementProviders managProv;
	
	@BeforeEach
	void createManager() {
		managProv = new ManagementProviders();
	}
	
	@Test
	void testsRegistrationOfANewSupplier() throws IdDoesntExist, EntitiesNotRegistred {
		managProv.register("Empresa 1 ltda", "123456789", "Rua A"); //registrando fornecedor
		assertEquals(1, managProv.getList().size(), "Tamanho da lista de fornecedores apos uma adicao");
		String idTest1 = managProv.getList().get(0).getId(); //recebe id de fornecedores
		Providers provTest1 = (Providers) managProv.searchEntities(idTest1); 
		
		assertEquals("Empresa 1 ltda", provTest1.getName(), "Verifica que o nome 'Empresa 1 ltda' foi cadastrado");
		assertEquals("123456789", provTest1.getCnpj(), "Verifica se o cnpj '123456789' foi cadastrado");
		assertEquals("Rua A", provTest1.getAddress(), "Verifica se o endereço 'Rua A' foi cadastrado");
		
		managProv.register("Empresa 2 ltda", "987654321", "Rua B");
		assertEquals(2, managProv.getList().size(), "Tamanho da lista de fornecedores apos duas edicoes");
		String idTest2 = managProv.getList().get(1).getId();
		Providers provTest2 = (Providers) managProv.searchEntities(idTest2);
		
		assertEquals("Empresa 2 ltda", provTest2.getName(), "Verifica que o nome 'Empresa 2 ltda' foi cadastrado");
		assertEquals("987654321", provTest2.getCnpj(), "Verifica se o cnpj '987654321' foi cadastrado");
		assertEquals("Rua B", provTest2.getAddress(), "Verifica se o endereço 'Rua B' foi cadastrado");
	}
	
	@Test
	void testEditingAProvidersInformation() throws IdDoesntExist, EntitiesNotRegistred {
		managProv.register("Empresa 1 ltda", "123456789", "Rua A");//registrando fornecedor
		String idTest1 = managProv.getList().get(0).getId();
		Providers provTest1 = (Providers) managProv.searchEntities(idTest1);
		
		managProv.edit(idTest1, "nome", "Laticinios ltda");
		assertEquals("Laticinios ltda", provTest1.getName(), "Verifica se mudanca para 'Laticinios ltda' foi feita corretamente");
		
		managProv.edit(idTest1, "cnpj", "123456780");
		assertEquals("123456780", provTest1.getCnpj(), "Verifica se mudanca para '123456780' foi feita corretamente");
		
		managProv.edit(idTest1, "endereco", "Rua C");
		assertEquals("Rua C", provTest1.getAddress(), "Verifica se mudanca para 'Rua C' foi feita corretamente");
	}

}
