package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import main.ReportsSale;
import management_models.*;
import modeling_models.*;

class ReportsSaleTest {
	private ManagementSales managSalesTest = new ManagementSales();
	private ManagementMenu managMenuTest = new ManagementMenu();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    		.withResolverStyle(ResolverStyle.STRICT);
    private static LocalDate date1;
    private static LocalDate date2;
    private static LocalTime hour1;
    private static ArrayList<Items> itemsPurchased =  new ArrayList<Items>();
    
	@Test
	void testExceptions() {
		
		assertDoesNotThrow(() ->{
			// Criar prato
			HashMap<String, Integer> compositionTest1 = new HashMap<String, Integer> ();
			String idTest1 = managMenuTest.register("porção de batata", "batatas fritas", new BigDecimal("13.99"), "entrada", compositionTest1);
			Items item1 = (Items) managMenuTest.searchEntities(idTest1);
			
			// Criar venda
		    date1 = LocalDate.parse("12/03/2022", dateFormatter);
		    date2 = LocalDate.parse("13/01/2023", dateFormatter);
		    
		    hour1 = LocalTime.parse("12:13", timeFormatter);
		    
		    itemsPurchased.add(item1);
		    
			managSalesTest.register(date1, hour1, itemsPurchased, idTest1);
			
			
			ReportsSale reportSaleTesting = new ReportsSale();
			reportSaleTesting.generatePDF(managSalesTest, managMenuTest, date1, date2, idTest1);
		});
	}

}
