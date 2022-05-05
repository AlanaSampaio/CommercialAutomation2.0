package management_models;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.*;
import modeling_models.*;


public class ManagementProducts extends Management {
	private HashMap<String, ArrayList<Products>> stock = new HashMap<String, ArrayList<Products>>();
	
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
							   "Quantidade: " + prod.getQuantity()+ " unidades \n" +
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
	
	public void deleteProductStock(Products prod){
		boolean nameExist = this.stock.containsKey(prod.getName());
		if (nameExist) {
			this.stock.get(prod.getName()).remove(prod);
		}
	}
	
	public void removeQuantProd(Products prod, int quant) throws NotEnoughProduct{
		// Remove a quantidade passada da quantidade do produto. Se o produto for zerado ele � deletado.
		int quantBefore = prod.getQuantity();
		if (quantBefore <= quant) {
			this.delete(prod);
			throw new NotEnoughProduct(quant - quantBefore);
		} else {
			prod.setQuantity(quantBefore - quant);
		}
	}
	
	public void removeQuantGroup(String nameGroup, int quant) throws NotEnoughStock{
		// Remove a quantidade "quant" passada dos produtos do conjunto.
		ArrayList<Products> prods = this.stock.get(nameGroup);
		int left = quant;
		int i = 0;
		if (quant > this.getGroupQuantity(nameGroup)) {
			throw new NotEnoughStock();
		} else {
			while (left > 0) {
				//System.out.println(i++);
				try {
					this.removeQuantProd(prods.get(0), left);
					left = 0;
				} catch(NotEnoughProduct eProd) {
					left = eProd.getQuantLeft();
					//System.out.println(left);
				}
			}
		}
	}
	
	public int getGroupQuantity(String prodName) {
		// Recebe o nome de um conjunto de produtos e retorna a 
		//quantidade total (soma da quantidade de todos os produtos desse conjunto).
		int totalQuantity = 0;
		ArrayList<Products> prods = this.stock.get(prodName);
		for(Products prod : prods) {
			totalQuantity += prod.getQuantity();
		};
		return totalQuantity;
	}
	
	public boolean checkAllProductsEnough(HashMap<String, Integer> groupsNeed){
		for (HashMap.Entry<String,Integer> nameQuant : groupsNeed.entrySet()) {
			if(this.getGroupQuantity(nameQuant.getKey()) < nameQuant.getValue()) {
				return false;
			}
		};
		return true;
	}
	
	
	public void updateStock(HashMap<String, Integer> groupsUsed) throws NotEnoughStock{
		
		if (!this.checkAllProductsEnough(groupsUsed)) {
			throw new NotEnoughStock();
		}
		
		for (HashMap.Entry<String,Integer> nameQuant : groupsUsed.entrySet()) {
			try {
				this.removeQuantGroup(nameQuant.getKey(), nameQuant.getValue());
			} catch(NotEnoughStock e){
				throw new NotEnoughStock();
			}
		};
	}
	
	
	public HashMap<String, ArrayList<Products>> getStock() {
		return stock;
	}
	
	public void delete(Products prod) {
		this.deleteProductStock(prod);
		this.delete(prod.getId());
	}
}
