
/**
 * 
 */
package modeling_models;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	 * Preco do produto.
	 */
	private BigDecimal price;
	/**
	 * Validade do produto. 
	 * Por nao haver manipulacao da data, ela e armazenada em String
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
		this.quantity = quantity;
		this.provider = provider;
		generatorCode("P");
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
	 * @return o preco
	 */
	public BigDecimal getPrice() {
		return price;
	}


	/**
	 * @param price: Novo preco
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
