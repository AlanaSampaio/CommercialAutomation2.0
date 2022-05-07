package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class ReportsStock {
	
	public void generatePDF(ManagementSales sales, ManagementProducts products, LocalDate dateBefore, LocalDate dateAfter, String idPlate, String idProvider) throws IdDoesntExist, EntitiesNotRegistred {
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            document.open();

            Paragraph p = new Paragraph("Relatorio");
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
            
            p = new Paragraph("Vendas realizadas por periodo: ");
            Section section1 = chapter.addSection(p);
            document.add(section1);
            
            saleByPeriod(sales, p, document, dateBefore, dateAfter);
            
            p = new Paragraph(" ");
            document.add(p);
            
        	p = new Paragraph("Vendas realizadas por tipo de prato do cardapio: ");
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
            
            totalAmountOfStock(products, p, document);
            
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
	

	
	public void totalAmountOfStock(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		String groupName;
		ArrayList<Products> group;
		
		p = new Paragraph(" ");
        document.add(p);
        
    	HashMap<String, ArrayList<Products>> groupProducts = products.getStock();
    	int cont = 1;
    	for (HashMap.Entry<String, ArrayList<Products>> groupProds : groupProducts.entrySet()){
    		groupName = groupProds.getKey();
    		group = groupProds.getValue();
   
    		p = new Paragraph(cont++ + "- " + "Produtos de nome: " + groupName +
    				   "   |   Total no estoque: " + products.getGroupQuantity(groupName) + "\n");
    		document.add(p);
    		p = new Paragraph(" ");
    		for (Products prod : group) {
    			p = new Paragraph("\nID: " + prod.getId() + "\n" + 
					   "Fornecedor: " + prod.getProvider().getName()+ "\n" + 
					   "Preco: R$" + prod.getPrice()+ "\n" +
					   "Quantidade: " + prod.getQuantity() + " unidades\n" +
					   "Validade: " + prod.getValidity() + "\n");
    			document.add(p);
    		}
        	
        	p = new Paragraph(" ");
            document.add(p);
    	}
	}
	

	
	
	public String dateHour() {
		Date d = Calendar.getInstance().getTime();
		String formatString = "dd.MM.yyyy_hh.mm.ss" ;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (formatString);
		String formattedDate = simpleDateFormat.format(d) ;
		
		return formattedDate;
	}
}

