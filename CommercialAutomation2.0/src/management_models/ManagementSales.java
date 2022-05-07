package management_models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import modeling_models.Entities;
import modeling_models.Items;
import modeling_models.Sales;
import modeling_models.Users;

public class ManagementSales extends Management {

	public String register(LocalDate day, LocalTime hour, ArrayList<Items> itemsPurchased, String paymentMethod) {
		Sales newSale = new Sales(day, hour, paymentMethod, itemsPurchased);
		this.register(newSale);
		return newSale.getId();
	}

	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "dia":
				salesPEdit.setDay((LocalDate) newValue);
				break;
			case "horario":
				salesPEdit.setHour((LocalTime) newValue);
				break;
			case "modoDePagamento":
				salesPEdit.setPaymentMethod((String) newValue);
				break;
		}
	}
	
	public void addItem(String idPEdit, Items itemPAdd) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		salesPEdit.addItem(itemPAdd);
	}
	
	public void deleteItem(String idPEditar, Items itemPDeletar) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEditar);
		int size = salesPEdit.deleteItem(itemPDeletar);
		if (size == 0) {
			this.delete(idPEditar);
		}
	}

	public String salesMade(ArrayList list) {
		String priceTotal = "";
		BigDecimal priceTotalSales = new BigDecimal("0");
		if (this.getList().size() == 0 ) {
			System.out.println("Nenhuma venda registrada");
		} else { 
			ArrayList<Sales> listSales = list;
		    for (Sales i : listSales) {
				priceTotalSales = priceTotalSales.add(i.getPriceTotal());
				
			}
			
			priceTotal = priceTotalSales.toPlainString();
			
			/*this.getList().forEach(sale -> {
				Sales sales = (Sales) sale;
			priceTotalSales = priceTotalSales.add(sales.getPriceTotal());
		});*/
		}
		return priceTotal;
	}

	@Override
	public void list(boolean allInformations) {
		this.getList().forEach(element -> {
			Sales sale = (Sales) element; 
			System.out.println("ID: " + sale.getId() + "\n" + 
							   "Dia: " + sale.getDay()+ "\n" + 
							   "Horario: " + sale.getHour()+ "\n" +
							   "Preco total: R$" + sale.getPriceTotal() + "\n" +
							   "Modo de Pagamento: " + sale.getPaymentMethod()+ "\n" +
							   "Itens vendidos: \n");
			
			sale.getItemsPurchased().forEach(item -> {
				System.out.println("\tID: " + item.getId() +
								   "\n\tNome: " + item.getName() +
								   "\n\tPreco: " + item.getPrice());
			});
			System.out.println("\n");
		});
		
	}
}
