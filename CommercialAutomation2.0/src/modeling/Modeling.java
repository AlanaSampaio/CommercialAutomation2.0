package modeling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Scanner;

import exceptions.*;
import management_models.*;
import modeling_models.*;


public class Modeling {
	private static ManagementProducts prods = new ManagementProducts();
	private static ManagementProviders forn = new ManagementProviders();
	
	public static void main(String[] args) {
//	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
//		.withResolverStyle(ResolverStyle.STRICT);
//
//	    LocalDate validity = LocalDate.parse("12/03/2022", dateTimeFormatter);
//	    
//		HashMap<String, Integer> useds = new HashMap<String, Integer>();
//		useds.put("maca", 15);
//		useds.put("abacaxi", 20);
//	    
//	    Providers p1 = (Providers) forn.searchEntities(forn.register("p1", "111111", "praça"));
//		Products pd1 = (Products) prods.searchEntities(prods.register("maca", new BigDecimal("1.23"), validity, 10, p1));
//		Products pd2 = (Products) prods.searchEntities(prods.register("maca", new BigDecimal("4.56"), validity, 20, p1));
//		Products pd3 = (Products) prods.searchEntities(prods.register("abacaxi", new BigDecimal("4.56"), validity, 20, p1));
//		prods.list();
//		System.out.println(prods.getStock());
//		System.out.println(prods.getGroupQuantity("maca"));
//		
//		try {
//		prods.updateStock(useds);
//		} catch(NotEnoughStock e) {
//			System.out.println(e.getMessage());
//		}
//		
//		System.out.println(prods.getStock());
//		System.out.println(prods.getGroupQuantity("maca"));
//		prods.list();
	}
}

