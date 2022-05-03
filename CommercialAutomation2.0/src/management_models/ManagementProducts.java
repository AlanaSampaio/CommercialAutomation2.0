package management_models;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;


import modeling_models.*;

public class ManagementProducts extends Management {
	private HashMap<String, ArrayList<Products>> stock = new HashMap<String, ArrayList<Products>>();
	private int totalQuantity;
	
	public String register(String name, BigDecimal price, LocalDate validity, int quantity, Providers provider) {
		Products newProduct = new Products (name, price, validity, quantity, provider);
		this.register(newProduct);
		addProductStock(newProduct);
		return newProduct.getId();
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
				productPEdit.setQuantity((int) newValue);

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
							   "Preco: R$ " + prod.getPrice()+ "\n" +
							   "Validade: " + prod.getValidity().format(dateTimeFormatter)+ "\n" +
							   "Quantidade: " + prod.getQuantity()+ "unidades \n" +
							   "Fornecedor: \n" + 
							   "\tID: " + prod.getProvider().getId() +
							   "\tNome: "+ prod.getProvider().getName());
			System.out.println("\n");
		});
	}
	
	public void addProductStock(Products newProd) {
		boolean nameExist = this.stock.containsKey(newProd.getName());
		if (nameExist) {
			this.stock.get(newProd.getName()).add(newProd);
		} else {
			this.stock.put(newProd.getName(), new ArrayList<Products>());
			this.stock.get(newProd.getName()).add(newProd);
		}
		
	}
	
	public void removeProductStock(Products newProd){
		boolean nameExist = this.stock.containsKey(newProd.getName());
		if (nameExist) {
			this.stock.get(newProd.getName()).remove(newProd);
		}
	}
	
	public void itemSold(Items item) {
		HashMap<Products, BigDecimal> prodsItem = item.getComposition();
		prodsItem.forEach((prod, quant) -> {
			this.stock.get(prod.getName()).forEach(prods ->{
			});
		});
	}
	
	public int prodQuantity(String prodName) {
		this.totalQuantity = 0;
		ArrayList<Products> prods = this.getStock().get(prodName);
		prods.forEach(prod -> {
			this.totalQuantity += prod.getQuantity();
		});
		return this.totalQuantity;
	}
	
	
	public HashMap<String, ArrayList<Products>> getStock() {
		return stock;
	}
	
	@Override
	public void delete(String idEntities) {
		this.removeProductStock((Products) this.searchEntities(idEntities));
		this.delete(idEntities);
	}
}
