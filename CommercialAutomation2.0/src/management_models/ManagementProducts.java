package management_models;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;


import modeling_models.Products;
import modeling_models.Providers;

public class ManagementProducts extends Management {


	public String register(String name, BigDecimal price, LocalDate validity, BigDecimal quantity, Providers provider) {
		Products novoProduto = new Products (name, price, validity, quantity, provider);
		this.register(novoProduto);
		return novoProduto.getId();
	}

	public void edit(String idPEdit, String changedValue, Object newValue) {
		Products productPEdit = (Products) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "nome":
				productPEdit.setName((String) newValue);
				break;
			case "preco":

				productPEdit.setPrice((BigDecimal) newValue);
				break;
			case "validade":
				productPEdit.setValidity((LocalDate) newValue);
				break;
			case "quantidade":
				productPEdit.setQuantity((BigDecimal) newValue);

				break;
			case "fornecedor":
				productPEdit.setProvider((Providers) newValue);
				break;
		}
		
	}

	
	@Override
	public void list() {
		this.getList().forEach(element -> {
			Products prod = (Products) element;
		    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
		    		.withResolverStyle(ResolverStyle.STRICT);    
			System.out.println("ID: " + prod.getId() + "\n" + 
							   "Nome: " + prod.getName()+ "\n" + 
							   "Pre�o: R$ " + prod.getPrice()+ "\n" +
							   "Validade: " + prod.getValidity().format(dateTimeFormatter)+ "\n" +
							   "Quantidade: " + prod.getQuantity()+ "unidades \n" +
							   "Fornecedor: \n" + 
							   "\tID: " + prod.getProvider().getId() +
							   "\tNome: "+ prod.getProvider().getName());
			System.out.println("\n");
		});
	}
}
