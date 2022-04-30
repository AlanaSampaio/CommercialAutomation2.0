package modeling_models;

import java.util.ArrayList;
import java.util.HashMap;

public class Providers extends Entities {
	private String name;
	private String cnpj;
	private String address;
	private ArrayList<Products> productsProvided;

	public Providers(String name, String cnpj, String address) {
		super();
		this.name = name;
		this.cnpj = cnpj;
		this.address = address;

		this.productsProvided = new ArrayList<Products>();
		generatorCode("F");
	}
	
	public void listProdProvided() {
		if (getProductsProvided() != null) {
		this.getProductsProvided().forEach(product -> {
			System.out.println("\tID: " + product.getId() +
							   "\tNome: " + product.getName());
			System.out.println("\n");
		});
		}
	}
	
	public HashMap<String, String> getAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("id", this.getId());
		attributes.put("name", this.getName());
		attributes.put("cnpj", this.getCnpj());
		attributes.put("address", this.getAddress());
		return attributes;
	}
	
	public void addProduct(Products prod) {
		this.productsProvided.add(prod);	
	}
	
	
	public void removeProduct(String idProd) {
		for (int i = 0; i < this.productsProvided.size(); i++) {
			String currentProd = (this.productsProvided.get(i)).getId();
			if (idProd.equals(currentProd)) {
				this.productsProvided.remove(i);
				}
			}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the productsProvided
	 */
	public ArrayList<Products> getProductsProvided() {
		return productsProvided;
	}

	/**
	 * @param productsProvided the productsProvided to set
	 */
	public void setProductsProvided(ArrayList<Products> productsProvided) {
		this.productsProvided = productsProvided;
	}

}
