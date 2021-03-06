/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programa??o II
Concluido em: 09/05/2022
Declaro que este c?digo foi elaborado por mim de forma individual e n?o cont?m nenhum
trecho de c?digo de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e p?ginas ou documentos eletr?nicos da Internet. Qualquer trecho de c?digo
de outra autoria que n?o a minha est? destacado com uma cita??o para o autor e a fonte
do c?digo, e estou ciente que estes trechos n?o ser?o considerados para fins de avalia??o.
******************************/

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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.*;
import modeling_models.*;

/**
 * Classe que gera os relatorios relacionados ao estoque
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class ReportsStock {
	
	public void generatePDF(ManagementProducts products, String idProd) throws IdDoesntExist, EntitiesNotRegistred {
		Document document = new Document();
		String name = "product_" + dateHour() + ".pdf";
		
        try {
            PdfWriter.getInstance(document, new FileOutputStream(name));
            document.open();

            Paragraph p = new Paragraph("Relatorio de Produtos");
            p.setAlignment(1);
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Todos os produtos no estoque: ");
            document.add(p);
            
            totalAmountOfStock(products, p, document);
            
            p = new Paragraph(" ");
            document.add(p);
   
            p = new Paragraph("Informacoes do produto selecionado: ");
            document.add(p);
            
            byProduct(products, idProd, p, document);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Produtos que vencem no proximo mes: ");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            productsToExpire(products, p, document);
            
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
	
	
	public void byProduct(ManagementProducts products, String idProd, Paragraph p, Document document) throws DocumentException, IdDoesntExist, EntitiesNotRegistred {
		Products prod = (Products) products.searchEntities(idProd);
		
		p = new Paragraph(" ");
        document.add(p);
    		
    	p = new Paragraph("\nID: " + prod.getId() + "\n" + 
    					  "Nome: " + prod.getName() + "\n" +
					   	  "Fornecedor: " + prod.getProvider().getName()+ "\n" + 
					      "Preco: R$" + prod.getPrice()+ "\n" +
					      "Quantidade: " + prod.getQuantity() + " unidades\n" +
					      "Validade: " + prod.getValidity() + "\n");
    	document.add(p);   
    	
        p = new Paragraph(" ");
        document.add(p);
	}
	
	
	public void productsToExpire(ManagementProducts products, Paragraph p, Document document) throws DocumentException {
		LocalDate currentDay = LocalDate.now();
		LocalDate nextMonth = currentDay.plusMonths(1);
		
		p = new Paragraph(" ");
        document.add(p);
        
		for (Entities enti : products.getList()) {
			Products prod = (Products) enti;
			if (nextMonth.isAfter(prod.getValidity()) && currentDay.isBefore(prod.getValidity())) {		    		
		    	p = new Paragraph("\nID: " + prod.getId() + "\n" + 
		    					  "Validade: " + prod.getValidity() + "\n" +
		    					  "Nome: " + prod.getName() + "\n" +
							   	  "Fornecedor: " + prod.getProvider().getName()+ "\n" + 
							      "Preco: R$" + prod.getPrice()+ "\n" +
							      "Quantidade: " + prod.getQuantity() + " unidades\n");
		    	document.add(p);   
			}
		}
		
		p = new Paragraph("\nProdutos ja vencidos:\n");
		document.add(p);  
		
		for (Entities enti : products.getList()) {
			Products prod = (Products) enti;
			if (currentDay.isAfter(prod.getValidity())) {		    		
		    	p = new Paragraph("\nID: " + prod.getId() + "\n" + 
		    					  "Validade: " + prod.getValidity() + "\n" +
		    					  "Nome: " + prod.getName() + "\n" +
							   	  "Fornecedor: " + prod.getProvider().getName()+ "\n" + 
							      "Preco: R$" + prod.getPrice()+ "\n" +
							      "Quantidade: " + prod.getQuantity() + " unidades\n");
		    	document.add(p);   
			}
		}		
	}
	
	/**
	 * @return Retorna a data e o dia atual formatados para o nome do arquivo
	 */
	public String dateHour() {
		Date d = Calendar.getInstance().getTime();
		String formatString = "dd.MM.yyyy" ;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (formatString);
		String formattedDate = simpleDateFormat.format(d) ;
		
		return formattedDate;
	}
}

