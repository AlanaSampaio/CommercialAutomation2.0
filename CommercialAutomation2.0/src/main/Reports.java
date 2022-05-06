package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

import management_models.*;
import modeling_models.*;

public class Reports {
	
	public void generatePDF(ManagementSales sales) {
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
            p = new Paragraph(" ");
            document.add(p);
        	ArrayList<Entities> listSales = sales.getList();
        	int cont = 1;
        	
            /*sales.getList().forEach(element -> {
            	Sales sale = (Sales) element; 
            	p = new Paragraph( "ID: " + sale.getId() + "\n" + 
					   "Dia: " + sale.getDay()+ "\n" + 
					   "Horario: " + sale.getHour()+ "\n" +
					   "Preco total: R$" + sale.getPriceTotal() + "\n" +
					   "Modo de Pagamento: " + sale.getPaymentMethod());
            	document.add(p);
            	});*/
            	
				
            //});
        	for (Entities sale : listSales) {
            	p = new Paragraph(cont++ + "-" +"ID: " + sale.getId());
            	document.add(p);
        	}
            
            
            String saleTotal = sales.salesMade(sales.getList());
            Phrase ph = new Phrase("Total das vendas realizadas: R$");
            document.add(ph);
            ph = new Phrase(saleTotal);
            document.add(ph);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Vendas realizadas por período: ");
            Section section1 = chapter.addSection(p);
            document.add(section1);
        	
        	p = new Paragraph(" ");
            document.add(p);
            
        	p = new Paragraph("Vendas realizadas por tipo de prato do cardápio: ");
            Section section2 = chapter.addSection(p);
            document.add(section2);
            
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
}

