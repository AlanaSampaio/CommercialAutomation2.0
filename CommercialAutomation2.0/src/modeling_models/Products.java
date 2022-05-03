
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
	 * Pre�o do produto.
	 */
	private BigDecimal price;
	/**
	 * Validade do produto. 
	 * Por n�o haver manipula��o da data, ela � armazenada em String
	 */
	private LocalDate validity;
	
	/**
	 * Atributos do fornecedor do produto.
	 */
	private Providers provider;
	
	private int quantity;
	
	/**
	 * Construtor do produto com a declara��o de seus atributos
	 * @param name: Nome do produto.
	 * @param price: Pre�o do produto.
	 * @param validity: Validade do produto.
	 * @param provider: Fornecedor do produto.
	 */
	public Products(String name, BigDecimal price, LocalDate validity,int quantity, Providers provider){
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
		attributes.put("pre�o", String.valueOf(this.getPrice()));
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
	 * @return o pre�o
	 */
	public BigDecimal getPrice() {
		return price;
	}


	/**
	 * @param price: Novo pre�o
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
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
