package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.*;
import modeling_models.*;

public class Reports {
	
	public void generatePDF(ManagementSales sales, LocalDate dateBefore, LocalDate dateAfter, String idPlate, String idProvider) throws IdDoesntExist, EntitiesNotRegistred {
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            document.open();

            Paragraph p = new Paragraph("Relatório");
            Chapter chapter = new Chapter(p, 1);
            p.setAlignment(1);
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            p = new Paragraph("Vendas:");
            Section section = chapter.addSection(p);
            document.add(section);
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Vendas realizadas: ");
            Section section0 = chapter.addSection(p);
            document.add(section0);
            
            salesTotal(sales, p, document);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Vendas realizadas por período: ");
            Section section1 = chapter.addSection(p);
            document.add(section1);
            
            saleByPeriod(sales, p, document, dateBefore, dateAfter);
            
            p = new Paragraph(" ");
            document.add(p);
            
        	p = new Paragraph("Vendas realizadas por tipo de prato do cardápio: ");
            Section section2 = chapter.addSection(p);
            document.add(section2);
            
            p = new Paragraph(" ");
            document.add(p);
            
            saleByPlate(sales, p, document, idPlate);
            
            p = new Paragraph(" ");
            document.add(p);
            Chapter chapter0 = new Chapter(p, 2);
            p = new Paragraph("Estoque:");
            Section section3 = chapter0.addSection(p);
            document.add(section3);
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Quantidade total do estoque:");
            Section section4 = chapter0.addSection(p);
            document.add(section4);
            
            
            p = new Paragraph("Quantidade por produto:");
            Section section5 = chapter0.addSection(p);
            document.add(section5);
            
            p = new Paragraph("Produtos a vencer:");
            Section section6 = chapter0.addSection(p);
            document.add(section6);
            
            p = new Paragraph(" ");
            document.add(p);
            Chapter chapter1 = new Chapter(p, 3);
            p = new Paragraph("Fornecedores:");
            Section section7 = chapter1.addSection(p);
            document.add(section7);
            p = new Paragraph(" ");
            document.add(p);
        
            p = new Paragraph("Fornecedores totais:");
            Section section8 = chapter1.addSection(p);
            document.add(section8);
            
            p = new Paragraph("Fornecedores por produto:");
            Section section9 = chapter1.addSection(p);
            document.add(section9);
            
            document.close();
            Desktop.getDesktop().open(new File("relatorio.pdf"));
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
	}
	
	public void salesTotal(ManagementSales sales, Paragraph p, Document document) throws DocumentException {
        
        p = new Paragraph(" ");
        document.add(p);
        
    	ArrayList<Entities> listSales = sales.getList();
    	int cont = 1;
    	for (Entities sale : listSales) {
    		Sales sales1 = (Sales) sale; 
    		p = new Paragraph(cont++ + "- ID: " + sales1.getId() + "\n" + 
					   "Dia: " + sales1.getDay()+ "\n" + 
					   "Horario: " + sales1.getHour()+ "\n" +
					   "Preco total: R$" + sales1.getPriceTotal() + "\n" +
					   "Modo de Pagamento: " + sales1.getPaymentMethod() + "\n");
        	document.add(p);
        	
        	p = new Paragraph(" ");
            document.add(p);
    	}
	}
	 
	public void saleByPeriod(ManagementSales sales, Paragraph p, Document document, LocalDate dateBefore, LocalDate dateAfter) throws DocumentException {
		
		p = new Paragraph(" ");
        document.add(p);
        
        p = new Paragraph("Período de " + 
        			dateBefore.getDayOfMonth() + "/" + 
        			dateBefore.getMonthValue() + "/" + 
        			dateBefore.getYear() + " à " + 
        			dateAfter.getDayOfMonth() + "/" + 
        			dateAfter.getMonthValue() + "/" + 
        			dateAfter.getYear() );
        document.add(p);
        
        p = new Paragraph(" ");
        document.add(p);
	
        int cont = 1;
        for (Entities i : sales.getList()) {
			Sales sales1 = (Sales) i;
			boolean before = dateBefore.isBefore(sales1.getDay());
			boolean after = dateAfter.isAfter(sales1.getDay());
			if (before && after) {
				p = new Paragraph(cont++ + "- ID: " + sales1.getId() + "\n" + 
	 					   "Dia: " + sales1.getDay()+ "\n" + 
	 					   "Horario: " + sales1.getHour()+ "\n" +
	 					   "Preco total: R$" + sales1.getPriceTotal() + "\n" +
	 					   "Modo de Pagamento: " + sales1.getPaymentMethod() + "\n");
				document.add(p);
				
				p = new Paragraph(" ");
		        document.add(p);
			} 
			
        }
	}
	
	public void saleByPlate(ManagementSales sales, Paragraph p, Document document, String idPlate) throws DocumentException, IdDoesntExist, EntitiesNotRegistred {
		
		p = new Paragraph(" ");
        document.add(p);
        
        p = new Paragraph("Prato: " + idPlate );
        document.add(p);
        
        p = new Paragraph(" ");
        document.add(p);
        
        int cont = 1;
        for (Entities i : sales.getList()) {
			Sales sales1 = (Sales) i;
			Entities plate = sales.searchEntities(idPlate);
			if (plate != null) {
				p = new Paragraph(cont++ + "- ID: " + sales1.getId());
				document.add(p);
				
				p = new Paragraph(" ");
		        document.add(p);
			} 
        }
	}
	
	/*public void totalAmountOfStock(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		p = new Paragraph(" ");
        document.add(p);
        
    	HashMap<String, ArrayList<Products>> listProducts = products.getStock();
    	int cont = 1;
    	for (Entities product : listProducts) {
    		Products prod = (Products) product; 
    		p = new Paragraph(cont++ + "- ID: " + prod.getId() + "\n" + 
					   "Dia: " + sales1.getDay()+ "\n" + 
					   "Horario: " + sales1.getHour()+ "\n" +
					   "Preco total: R$" + sales1.getPriceTotal() + "\n" +
					   "Modo de Pagamento: " + sales1.getPaymentMethod() + "\n");
        	document.add(p);
        	
        	p = new Paragraph(" ");
            document.add(p);
    	}
	}*/
	
	public void providerRelationship(ManagementProviders provider, Paragraph p, Document document) throws DocumentException {
		p = new Paragraph(" ");
        document.add(p);
        
        int cont = 1;
        for (Entities provider1 : provider.getList()) {
        	Providers prov = (Providers) provider1;
        	p = new Paragraph(cont++ + "ID: " + prov.getId() + "\n" + 
					   "Nome: " + prov.getName()+ "\n" + 
					   "CNPJ: " + prov.getCnpj()+ "\n" +
					   "Endere�o: " + prov.getAddress() + "\n\n" +
					   "Produtos fornecidos:" + "\n" +
					   prov.getProductsProvided()
        	);
        	document.add(p);
        	
        	p = new Paragraph(" ");
            document.add(p);
        }
        
	}
}

