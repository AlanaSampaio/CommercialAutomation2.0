/**
 * 
 */
package modeling_models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Classe dos produtos do estabelecimento. Se relaciona com a classe Fornecedores, por um 
 * dos atributos ser o fornecedor do item.
 * @author Vanderleicio Junior
 */
public class Products extends Entities{
	/**
	 * Nome do produto.
	 */
	private String name;
	/**
	 * Preço do produto.
	 */
	private BigDecimal price;
	/**
	 * Validade do produto. 
	 * Por não haver manipulação da data, ela é armazenada em String
	 */
	private LocalDate validity;
	
	/**
	 * Atributos do fornecedor do produto.
	 */
	private Providers provider;
	
	private BigDecimal quantity;
	
	/**
	 * Construtor do produto com a declaração de seus atributos
	 * @param name: Nome do produto.
	 * @param price: Preço do produto.
	 * @param validity: Validade do produto.
	 * @param provider: Fornecedor do produto.
	 */
	public Products(String name, BigDecimal price, LocalDate validity, BigDecimal quantity, Providers provider){
		this.name = name;
		this.price = price;
		this.validity = validity;
		this.setQuantity(quantity);
		this.provider = provider;
		generatorCode("P");
	}
	
	public HashMap<String, String> getAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("id", this.getId());
		attributes.put("nome", this.getName());
		attributes.put("preço", String.valueOf(this.getPrice()));
		attributes.put("validade", String.valueOf(this.getValidity()));
		attributes.put("fornecedor", String.valueOf(this.getProvider()));
		return attributes;
	}
	
	/**
	 * @return o nome
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param nome: Novo nome
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return o preço
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price: Novo preço
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return a validade
	 */
	public LocalDate getValidity() {
		return validity;
	}

	/**
	 * @param validity: Nova validade
	 */
	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}

	/**
	 * @return o fornecedor
	 */
	public Providers getProvider() {
		return provider;
	}

	/**
	 * @param fornecedor: Novo fornecedor.
	 */
	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}
