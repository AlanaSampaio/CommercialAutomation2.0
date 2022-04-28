package modeling_models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Items extends Entities {
	private String name;
	private String description;
	private BigDecimal price;
	private String categoryItems;
	
	private ArrayList<Products> composition = new ArrayList<Products>();

	public Items(String name, String description, BigDecimal price, String categoryItems,
			ArrayList<Products> composition) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.categoryItems = categoryItems;
		this.composition = composition;
		generatorCode("I");
	}
	
	public void addProduct(Products product) {
		this.composition.add(product);
	}
	
	public int deleteProduct(Products prod) {
		if (this.composition.remove(prod) == false) {
			System.out.println("Produto não está nessa composição");
		}
		return this.composition.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(String categoryItems) {
		this.categoryItems = categoryItems;
	}

	public ArrayList<Products> getComposition() {
		return composition;
	}

	public void setComposition(ArrayList<Products> composition) {
		this.composition = composition;
	}
	
	public ArrayList<Products> getComposition1() {
		return composition;
	}
	
	public void setComposicao(ArrayList<Products> composition) {
		this.composition = composition;
	}
}
