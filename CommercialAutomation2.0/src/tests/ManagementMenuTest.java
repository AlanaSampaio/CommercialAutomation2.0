package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import management_models.ManagementMenu;
import modeling_models.Products;
import modeling_models.Providers;

class ManagementMenuTest {
	private ArrayList<Products> compositionTest1;
	private ArrayList<Products> compositionTest2;
	private ManagementMenu managMenu;
	private Products prodTest1;
	private Products prodTest2;
	
	@BeforeEach
	void initializeAndKeepVariablesForTesting() {
		managMenu = new ManagementMenu();
		
		Providers provTest = new Providers("Empresa ltda", "123456789", "Rua A");
		
		//prodTest1 = new Products("batata", new BigDecimal("1.50"), "01/01/2021", provTest);
		//prodTest2 = new Products("sal", new BigDecimal("0.50"), "03/03/2023", provTest);
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
