package modeling_models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Items extends Entities {
	private String name;
	private String description;
	private BigDecimal price;
	private String categoryItems;
	private HashMap<String, Integer> composition = new HashMap<String, Integer>();

	
	public Items(String name, String description, BigDecimal price, String categoryItems,
			HashMap<String, Integer> composition) {

		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.categoryItems = categoryItems;
		this.composition = composition;
		generatorCode("I");
	}

	public void addProduct(int quantity, String product) {
		this.composition.put(product, quantity);
	}
	
	public void deleteProduct(String prod) {
		this.composition.remove(prod);
	}
	
	public int getCompositionSize() {
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


	public HashMap<String, Integer> getComposition() {
		return composition;
	}

	public void setComposition(HashMap<String, Integer> composition) {
		this.composition = composition;
	}
}
