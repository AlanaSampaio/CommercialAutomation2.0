package modeling_models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Sales extends Entities {
	private LocalDate day;
	private LocalTime hour;
	private ArrayList<Items> itemsPurchased;
	private BigDecimal priceTotal;
	private String paymentMethod;
	
	public Sales(LocalDate day, LocalTime hour, String paymentMethod, ArrayList<Items> itemsPurchased) {
		super();
		this.day = day;
		this.hour = hour;
		this.itemsPurchased = itemsPurchased;
		this.paymentMethod = paymentMethod;
		updatePrice();
		generatorCode("V");
	}

	public void updatePrice() {
		this.priceTotal = new BigDecimal("0");
		if (this.itemsPurchased.size() > 0) {
			itemsPurchased.forEach(item -> priceTotal = priceTotal.add(item.getPrice()));
		}
	}

	public void addItem(Items item) {
		this.itemsPurchased.add(item);
		updatePrice();
	}
	
	public int deleteItem(Items item) {
		this.itemsPurchased.remove(item);
		updatePrice();
		return this.itemsPurchased.size();
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public LocalTime getHour() {
		return hour;
	}

	public void setHour(LocalTime hour) {
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
