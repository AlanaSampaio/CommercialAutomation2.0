package modeling_models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Sales extends Entities {
	private String day;
	private String hour;
	private ArrayList<Items> itemsPurchased;
	private BigDecimal priceTotal;
	private String paymentMethod;
	
	public Sales(String day, String hour, String paymentMethod, ArrayList<Items> itemsPurchased) {
		super();
		this.day = day;
		this.hour = hour;
		this.itemsPurchased = itemsPurchased;
		this.paymentMethod = paymentMethod;
		addPrice();
		generatorCode("S");
	}

	public void addPrice() {
		this.priceTotal = new BigDecimal("0");
		if (this.itemsPurchased.size() > 0) {
			itemsPurchased.forEach(item -> priceTotal = priceTotal.add(item.getPrice()));
		}
	}
	
	public void addItem(Items item) {
		this.itemsPurchased.add(item);
		addPrice();
	}
	
	public int deleteItem(Items item) {
		this.itemsPurchased.remove(item);
		addPrice();
		return this.itemsPurchased.size();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public ArrayList<Items> getItemsPurchased() {
		return itemsPurchased;
	}

	public void setItemsPurchased(ArrayList<Items> itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}