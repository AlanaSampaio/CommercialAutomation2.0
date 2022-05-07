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
		String name = "stock_" + dateHour() + ".pdf";
		
        try {
            PdfWriter.getInstance(document, new FileOutputStream(name));
            document.open();

            Paragraph p = new Paragraph("Relatorio de Estoque");
            p.setAlignment(1);
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Quantidade total do estoque:");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            totalAmountOfStock(products, p, document);
            
            p = new Paragraph("Quantidade por produto:");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            quantityPerProduct(products, p, document);
            
            p = new Paragraph("Produtos a vencer:");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            productsToWin(products, p, document);
            
            document.close();
            Desktop.getDesktop().open(new File(name));
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
	
	public void quantityPerProduct(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		
		p = new Paragraph(" ");
        document.add(p);
	}
	
	public void productsToWin(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		
		p = new Paragraph(" ");
        document.add(p);
	}
	
	
	public String dateHour() {
		Date d = Calendar.getInstance().getTime();
		String formatString = "dd.MM.yyyy_hh.mm.ss" ;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (formatString);
		String formattedDate = simpleDateFormat.format(d) ;
		
		return formattedDate;
	}
}

