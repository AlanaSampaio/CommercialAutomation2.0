package management_models;

import java.math.BigDecimal;

import java.util.HashMap;


import modeling_models.Items;
import modeling_models.Products;

public class ManagementMenu extends Management {


	public String register(String name, String description, BigDecimal price, String category, HashMap<Products, BigDecimal> composition) {
		Items newItem = new Items(name, description, price, category, composition);
		this.register((Items) newItem);
		return newItem.getId();
	}

	public void edit(String idPEdit, String changedValue, Object newValue) {
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
	

	public void addProductsItems(String idPEdit, Products productPAdd, BigDecimal quantity) {
		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.addProduct(quantity, productPAdd);
	}
	
	public void removeProductFromItem (String idPEdit, Products produtoPRemover) {

		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		int size = itemPEdit.deleteProduct(produtoPRemover);
		if (size == 0) {
			this.delete(idPEdit);
		}
	}


	@Override
	public void list() {
		this.getList().forEach(element -> {
			Items item = (Items) element; 
			System.out.println("ID: " + item.getId() + "\n" + 
							   "Nome: " + item.getName()+ "\n" + 
							   "Preco: R$ " + item.getPrice()+ "\n" +
							   "Descricao: " + item.getDescription()+ "\n" +
							   "Categoria: " + item.getCategoryItems()+ "\n" +
							   "Composto de: \n");
			
			item.getComposition().forEach((prod, quantity) ->{
				if (prod != null) {
				System.out.println("\tID: " + prod.getId() +
								   "\n\tNome: " + prod.getName()+ 
								   "\n\tQntd por item: " + quantity);
				}
			});
			System.out.println("\n");
		});
	}
}
