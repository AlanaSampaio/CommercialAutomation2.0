package management_models;

import java.math.BigDecimal;

import modeling_models.Products;
import modeling_models.Providers;

public class ManagementProducts extends Management {

	public void register(String name, BigDecimal price, String validity, Providers provider) {
		Products novoProduto = new Products (name, price, validity, provider);
		this.register(novoProduto);
	}

	/**
	 * M�todo herdado de Gerenciamentos para editar um produto.
	 */
	@Override
	public void edit(String idPEdit, String changedValue, Object newValue) {
		Products productPEdit = (Products) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "nome":
				productPEdit.setName((String) newValue);
				break;
			case "preco":
				productPEdit.setPrice(new BigDecimal((String) newValue));
				break;
			case "validade":
				productPEdit.setValidity((String) newValue);
				break;
			case "fornecedor":
				productPEdit.setProvider((Providers) newValue);
				break;
		}
		
	}
}
