package management_models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import modeling_models.Items;
import modeling_models.Sales;

public class ManagementSales extends Management {

	public String register(LocalDate day, LocalTime hour, ArrayList<Items> itemsPurchased, String paymentMethod) {
		Sales newSale = new Sales(day, hour, paymentMethod, itemsPurchased);
		this.register(newSale);
		return newSale.getId();
	}

	public void edit(String idPEdit, String changedValue, Object newValue) {
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
	
	public void addItem(String idPEdit, Items itemPAdd) {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		salesPEdit.addItem(itemPAdd);
	}
	
	public void deleteItem(String idPEditar, Items itemPDeletar) {
		Sales salesPEdit = (Sales) this.searchEntities(idPEditar);
		int size = salesPEdit.deleteItem(itemPDeletar);
		if (size == 0) {
			this.delete(idPEditar);
		}
	}


	@Override
	public void list() {
		this.getList().forEach(element -> {
			Sales sale = (Sales) element; 
			System.out.println("ID: " + sale.getId() + "\n" + 
							   "Dia: " + sale.getDay()+ "\n" + 
							   "Horario: " + sale.getHour()+ "\n" +
							   "Preco total: R$" + sale.getPriceTotal() + "\n" +
							   "Modo de Pagamento: " + sale.getPaymentMethod()+ "\n" +
							   "Itens vendidos: \n");
			
			sale.getItemsPurchased().forEach(item ->{
				System.out.println("\tID: " + item.getId() +
								   "\n\tNome: " + item.getName() +
								   "\n\tPreco: " + item.getPrice());
			});
			System.out.println("\n");
		});
		
	}
}
