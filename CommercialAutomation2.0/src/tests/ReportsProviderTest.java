package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.Test;

import main.ReportsProvider;
import management_models.ManagementProducts;
import management_models.ManagementProviders;
import modeling_models.Providers;

class ReportsProviderTest {
	ManagementProviders provider = new ManagementProviders();
	ManagementProducts products = new ManagementProducts();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static LocalDate date1 = LocalDate.parse("12/03/2022", dateTimeFormatter);
    
	@Test
	void testExceptions() {
		
		assertDoesNotThrow(() ->{
			String idProv  = provider.register("fornecedor", "1234", "endereco");
			Providers provd = (Providers) provider.searchEntities(idProv);
			products.register("produto", new BigDecimal("1.23"), date1, 10, provd);
			ReportsProvider reportProvidertesting = new ReportsProvider();
			reportProvidertesting.generatePDF(provider, products);
		});
	}

}
