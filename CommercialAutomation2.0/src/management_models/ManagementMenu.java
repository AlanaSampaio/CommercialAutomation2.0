package management_models;

import java.math.BigDecimal;
import java.util.ArrayList;

import modeling_models.Items;
import modeling_models.Products;

public class ManagementMenu extends Management {

	public void register(String name, String description, String price, String category, ArrayList<Products> composition) {
		Items newItem = new Items(name, description, new BigDecimal(price), category, composition);
		this.register((Items) newItem);
	}
	
	@Override
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
			itemPEdit.setPrice(new BigDecimal((String) newValue));
			break;
		case "categoria":
			itemPEdit.setCategoryItems((String) newValue);
			break;
		}
	}
	
	public void addProductsItems(String idPEdit, Products productPAdd) {
		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.addProduct(productPAdd);
	}
	
	public void removerProdutoDeItem (String idPEdit, Products produtoPRemover) {
		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		int size = itemPEdit.deleteProduct(produtoPRemover);
		if (size == 0) {
			this.delete(idPEdit);
		}
	}
}
