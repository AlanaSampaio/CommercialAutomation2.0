package modeling_models;

import java.math.BigDecimal;

public class Products extends Entities {
	private String name;
	private BigDecimal price;
	private String validity;
	private Providers provider;
	
	public Products(String name, BigDecimal price, String validity, Providers provider) {
		super();
		this.name = name;
		this.price = price;
		this.validity = validity;
		this.provider = provider;
		generatorCode("P");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}
	
	
	
}
