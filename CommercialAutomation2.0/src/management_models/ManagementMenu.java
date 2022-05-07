package management_models;

import java.math.BigDecimal;

import java.util.HashMap;

import exceptions.*;
import modeling_models.Items;
import modeling_models.Products;

public class ManagementMenu extends Management {


	public String register(String name, String description, BigDecimal price, String category, HashMap<String, Integer> composition) {
		Items newItem = new Items(name, description, price, category, composition);
		this.register((Items) newItem);
		return newItem.getId();
	}

	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		switch(changedValue) {
		case "nome":
			itemPEdit.setName((String) newValue);
			break;
		case "descricao":
			itemPEdit.setDescription((String) newValue);
			break;
		case "preco":

			itemPEdit.setPrice((BigDecimal) newValue);
			break;
		case "categoria":
			itemPEdit.setCategoryItems((String) newValue);
			break;
		}
	}
	

	public void addProductsItems(String idPEdit, Products productPAdd, int quantity) throws IdDoesntExist, EntitiesNotRegistred{
		Items itemPEdit;
		itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.addProduct(quantity, productPAdd.getName());
	}
	
	public void removeProductFromItem (String idPEdit, Products produtoPRemover) throws IdDoesntExist, EntitiesNotRegistred {

		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.deleteProduct(produtoPRemover.getName());
		int size = itemPEdit.getCompositionSize();
		if (size == 0) {
			this.delete(idPEdit);
		}
	}


	@Override
	public void list(boolean allInformations) throws EntitiesNotRegistred{
		if (this.getList().size() == 0) {
			throw new EntitiesNotRegistred();
		}
		this.getList().forEach(element -> {
			Items item = (Items) element; 
			System.out.println("ID: " + item.getId() + "\n" + 
							   "Nome: " + item.getName()+ "\n" + 
							   "Preco: R$ " + item.getPrice());
			
			if (allInformations) {
				System.out.println("Descricao: " + item.getDescription()+ "\n" +
								   "Categoria: " + item.getCategoryItems()+ "\n" +
							       "Composto de: \n");
			
				item.getComposition().forEach((prod, quantity) ->{
					if (prod != null) {
					System.out.println("\tNome: " + prod +
								   	   "\n\tQntd por item: " + quantity);
					}
				});
			}
			System.out.println("\n");
		});
	}
}
