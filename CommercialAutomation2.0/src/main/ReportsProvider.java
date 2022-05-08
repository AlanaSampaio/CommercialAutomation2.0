package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.ManagementProducts;
import management_models.ManagementProviders;
import modeling_models.Entities;
import modeling_models.Products;
import modeling_models.Providers;

public class ReportsProvider {

	public void generatePDF(ManagementProviders provider, ManagementProducts products) throws IdDoesntExist, EntitiesNotRegistred {
		Document document = new Document();
		String name = "provider_" + dateHour() + ".pdf";
        
		try {
            PdfWriter.getInstance(document, new FileOutputStream(name));
            document.open();

            Paragraph p = new Paragraph("Relatorio de Fornecedores");
            p.setAlignment(1);
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Fornecedores por Fornecedores:");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            providerRelationship(provider , p, document);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Fornecedores por Produtos:");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            providerProduct(products, p, document);
            
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
	
	public void providerRelationship(ManagementProviders provider, Paragraph p, Document document) throws DocumentException {
		p = new Paragraph(" ");
        document.add(p);
        
        int cont = 1;
        for (Entities provider1 : provider.getList()) {
        	Providers prov = (Providers) provider1;
        	p = new Paragraph(cont++ + " - ID: " + prov.getId() + "\n" + 
					   "Nome: " + prov.getName()+ "\n" + 
					   "CNPJ: " + prov.getCnpj()+ "\n" +
					   "Endere�o: " + prov.getAddress() + "\n\n" +
					   "Produtos fornecidos:" + "\n" );
        	//document.add(p);
            	prov.getProductsProvided().forEach(prod -> {
            	p = new Paragraph("ID: " + prod.getId() + "\n" +
            				"Nome: " + prod.getName() + "\n");
            	document.add(p);
            	});
					   "Endereco: " + prov.getAddress() + "\n\n" +
					   "Produtos fornecidos:" + "\n");
        	document.add(p);
        	
        	for (Products prod : prov.getProductsProvided()) {
        		p = new Paragraph("     ID: " + prod.getId() + "\n" +
        						  "     Nome: " + prod.getName() + "\n\n");
        		document.add(p);
        	}
        	
        	p = new Paragraph(" ");
            document.add(p);
        }
        
        p = new Paragraph(" ");
        document.add(p);
        
        //provider.getProductsProvided().forEach( product -> {
			//System.out.println("ID: " + product.getId() +
		   //"Nome: " + product.getName())}
	}
	
	public void providerProduct(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		p = new Paragraph(" ");
        document.add(p);
        
        int cont = 1;
        for (Entities prod1 : products.getList()) {
        	Products prod = (Products) prod1;
        	p = new Paragraph(cont++ + " - ID: " + prod.getId() + "\n" + 
							   "Nome: " + prod.getName() + "\n" + 
							   "Preco: R$ " + prod.getPrice() + "\n" +
							   "Fornecedor: \n" + 
							   "     ID: " + prod.getProvider().getId() + "\n" +
							   "     Nome: "+ prod.getProvider().getName());
        	document.add(p);
        	
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
