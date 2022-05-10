/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programa��o II
Concluido em: 09/05/2022
Declaro que este c�digo foi elaborado por mim de forma individual e n�o cont�m nenhum
trecho de c�digo de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e p�ginas ou documentos eletr�nicos da Internet. Qualquer trecho de c�digo
de outra autoria que n�o a minha est� destacado com uma cita��o para o autor e a fonte
do c�digo, e estou ciente que estes trechos n�o ser�o considerados para fins de avalia��o.
******************************/

package modeling_models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Classe das vendas a serem realizadas.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class Sales extends Entities {
	/**
	 * Dia de realiza��o da venda
	 */
	private LocalDate day;
	/**
	 * Hora da realiza��o da venda
	 */
	private LocalTime hour;
	/**
	 * Itens comprados nesta venda
	 */
	private ArrayList<Items> itemsPurchased;
	/**
	 * Preco total da venda
	 */
	private BigDecimal priceTotal;
	/**
	 * Metodo de pagamento da venda
	 */
	private String paymentMethod;
	
	/**
	 * @param day: LocalDate representando o dia da venda
	 * @param hour: LocalTime representando a hora da venda
	 * @param paymentMethod: String representando o m�todo de pagamento da venda
	 * @param itemsPurchased: ArrayList de Items representando os itens comprados
	 */
	public Sales(LocalDate day, LocalTime hour, String paymentMethod, ArrayList<Items> itemsPurchased) {
		super();
		this.day = day;
		this.hour = hour;
		this.itemsPurchased = itemsPurchased;
		this.paymentMethod = paymentMethod;
		updatePrice();
		generatorCode("V");
	}

	/**
	 * Atualiza o preco total da venda somando o preco de todos os itens
	 */
	public void updatePrice() {
		this.priceTotal = new BigDecimal("0");
		if (this.itemsPurchased.size() > 0) {
			itemsPurchased.forEach(item -> priceTotal = priceTotal.add(item.getPrice()));
		}
	}

	/**
	 * Adiciona um novo item a lista de itens comprados
	 * @param item: item a ser adicionado
	 */
	public void addItem(Items item) {
		this.itemsPurchased.add(item);
		updatePrice();
	}
	
	/**
	 * Deleta um item da lista de itens comprados.
	 * @param item: item a ser deletado
	 * @return o tamanho atual da lista de itens
	 */
	public int deleteItem(Items item) {
		this.itemsPurchased.remove(item);
		updatePrice();
		return this.itemsPurchased.size();
	}

	/**
	 * @return o dia
	 */
	public LocalDate getDay() {
		return day;
	}

	/**
	 * @param day: Novo dia
	 */
	public void setDay(LocalDate day) {
		this.day = day;
	}

	/**
	 * @return a hora
	 */
	public LocalTime getHour() {
		return hour;
	}

	/**
	 * @param hour: Nova hora
	 */
	public void setHour(LocalTime hour) {
		this.hour = hour;
	}

	/**
	 * @return a lista de itens comprados
	 */
	public ArrayList<Items> getItemsPurchased() {
		return itemsPurchased;
	}

	/**
	 * @param itemsPurchased: Nova lista de itens comprados
	 */
	public void setItemsPurchased(ArrayList<Items> itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
	}

	/**
	 * @return preco total
	 */
	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	/**
	 * @param priceTotal: Novo preco total
	 */
	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	/**
	 * @return metodo de pagamento
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod: Novo metodo de pagamento
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
