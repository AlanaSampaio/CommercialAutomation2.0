package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.Test;

import main.ReportsStock;
import management_models.*;
import modeling_models.Providers;

class ReportsStockTest {
	private static ManagementProducts managProductTest = new ManagementProducts();
	private static Providers provider;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static LocalDate date1;
    
	@Test
	void testExceptions() {
		
		assertDoesNotThrow(() ->{
			// Criar produto
			provider = new Providers("fornecedor", "1234", "endereco");
			date1 = LocalDate.parse("12/03/2022", dateFormatter);
			String idTest1 = managProductTest.register("maca", new BigDecimal("1.23"), date1, 10, provider);
			
			
			ReportsStock reportStockTesting = new ReportsStock();
			reportStockTesting.generatePDF(managProductTest, idTest1);
		});
	}

}
