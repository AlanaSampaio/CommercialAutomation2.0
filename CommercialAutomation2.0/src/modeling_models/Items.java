package modeling_models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Items extends Entities {
	private String name;
	private String description;
	private BigDecimal price;
	private String categoryItems;
	
	private HashMap<Products, BigDecimal> composition = new HashMap<Products, BigDecimal>();

	public Items(String name, String description, BigDecimal price, String categoryItems,
			HashMap<Products, BigDecimal> composition) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.categoryItems = categoryItems;
		this.composition = composition;
		generatorCode("I");
	}
	
	public void addProduct(BigDecimal quantity, Products product) {
		this.composition.put(product, quantity);
	}
	
	public int deleteProduct(Products prod) {
		this.composition.remove(prod);
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

	public HashMap<Products, BigDecimal> getComposition() {
		return composition;
	}

	public void setComposition(HashMap<Products, BigDecimal> composition) {
		this.composition = composition;
	}
}
