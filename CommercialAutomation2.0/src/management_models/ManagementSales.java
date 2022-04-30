package management_models;

import java.util.ArrayList;

import modeling_models.Items;
import modeling_models.Sales;

public class ManagementSales extends Management {

	public void register(String day, String hour, ArrayList<Items> itemsPurchased, String paymentMethod) {
		Sales newSales = new Sales(day, hour, paymentMethod, itemsPurchased);
		this.register(newSales);
	}

	public void edit(String idPEdit, String changedValue, Object newValue) {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "dia":
				salesPEdit.setDay((String) newValue);
				break;
			case "horario":
				salesPEdit.setHour((String) newValue);
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
		// TODO Auto-generated method stub
		
	}
}
