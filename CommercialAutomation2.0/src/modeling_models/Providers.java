package modeling_models;

import java.util.ArrayList;

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
		generatorCode("F");
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
