/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programação II
Concluido em: 09/05/2022
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import management_models.ManagementMenu;
import management_models.ManagementSales;
import modeling_models.Entities;
import modeling_models.Items;
import modeling_models.Sales;

/**
 * Classe que gera os relatorios relacionados as vendas
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class ReportsSale {

	/**
	 * Gera o pdf com o relatorio
	 * @param sales: Gerenciamento das vendas
	 * @param itemsMenu: Gerenciamento do cardapio
	 * @param dateBefore: Primeira data do periodo
	 * @param dateAfter: Segunda data do periodo
	 * @param idPlate: Id do prato a ser exibido
	 * @throws IdDoesntExist
	 * @throws EntitiesNotRegistred
	 */
	public void generatePDF(ManagementSales sales, ManagementMenu itemsMenu, LocalDate dateBefore, LocalDate dateAfter, String idPlate) throws IdDoesntExist, EntitiesNotRegistred {
		Document document = new Document();
		String name = "sale_" + dateHour() + ".pdf";
        
		try {
            PdfWriter.getInstance(document, new FileOutputStream(name));
            document.open();

            Paragraph p = new Paragraph("Relatorio de Vendas");
            p.setAlignment(1);
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Vendas realizadas: ");
            document.add(p);
            
            salesTotal(sales, p, document);
            
            p = new Paragraph(" ");
            document.add(p);
   
            p = new Paragraph("Vendas realizadas por periodo: ");
            document.add(p);
            
            saleByPeriod(sales, p, document, dateBefore, dateAfter);
            
            p = new Paragraph(" ");
            document.add(p);
            
            p = new Paragraph("Vendas realizadas por tipo de prato do cardapio: ");
            document.add(p);
            
            p = new Paragraph(" ");
            document.add(p);
            
            saleByPlate(sales, itemsMenu, p, document, idPlate);
            
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
	
	/**
	 * Adiciona as informacoes de todas as vendas
	 * @param sales
	 * @param p
	 * @param document
	 * @throws DocumentException
	 */
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
	 
	/**
	 * Adiciona as informacoes das vendas dentro do periodo pedido
	 * @param sales
	 * @param p
	 * @param document
	 * @param dateBefore
	 * @param dateAfter
	 * @throws DocumentException
	 */
	public void saleByPeriod(ManagementSales sales, Paragraph p, Document document, LocalDate dateBefore, LocalDate dateAfter) throws DocumentException {
		
		p = new Paragraph(" ");
        document.add(p);
        
        p = new Paragraph("Periodo de " + 
        			dateBefore.getDayOfMonth() + "/" + 
        			dateBefore.getMonthValue() + "/" + 
        			dateBefore.getYear() + " a  " + 
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
	
	/**
	 * Adiciona as informacoes das vendas do prato pedido
	 * @param sales
	 * @param itemsMenu
	 * @param p
	 * @param document
	 * @param idPlate
	 * @throws DocumentException
	 * @throws IdDoesntExist
	 * @throws EntitiesNotRegistred
	 */
	public void saleByPlate(ManagementSales sales, ManagementMenu itemsMenu, Paragraph p, Document document, String idPlate) throws DocumentException, IdDoesntExist, EntitiesNotRegistred {
		
		Items itemChoosed = (Items) itemsMenu.searchEntities(idPlate);
		p = new Paragraph(" ");
        document.add(p);
        
        p = new Paragraph("Prato: " + itemChoosed.getName());
        document.add(p);
        
        p = new Paragraph(" ");
        document.add(p);
        
        int cont = 1;
        for (Entities i : sales.getList()) {
			Sales sale = (Sales) i;
			if (sale.getItemsPurchased().contains(itemChoosed)) {
				p = new Paragraph(cont++ + "- ID: " + sale.getId() + "\n" +
									"Dia: " + sale.getDay() + "\n" +
									"Hora: " + sale.getHour() + "\n" +
									"Preco: R$" + sale.getPriceTotal() + "\n" +
									"Metodo de pagamento: " + sale.getPaymentMethod());
				document.add(p);
				p = new Paragraph(" ");
		        document.add(p);
			} 
        }
	}
	
	/**
	 * @return Retorna a data e o dia atual formatados para o nome do arquivo
	 */
	public String dateHour() {
		Date d = Calendar.getInstance().getTime();
		String formatString = "dd.MM.yyyy_hh.mm.ss" ;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (formatString);
		String formattedDate = simpleDateFormat.format(d) ;
		
		return formattedDate;
	}
}

