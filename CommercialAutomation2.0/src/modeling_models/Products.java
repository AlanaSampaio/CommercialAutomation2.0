/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programação II
Concluido em: 09/05/2022
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************/
package modeling_models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe dos produtos . Se relaciona com a classe Fornecedores, por um 
 * dos atributos ser o fornecedor do item.
 * @author Alana Sampaio
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
	 */
	private LocalDate validity;
	
	/**
	 * Atributos do fornecedor do produto.
	 */
	private Providers provider;
	
	private int quantity;
	
	/**
	 * Construtor do produto com a declaracao de seus atributos
	 * @param name: Nome do produto.
	 * @param price: Preco do produto.
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
	 * @param name: Novo nome
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
	 * @param provider: Novo fornecedor.
	 */
	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	/**
	 * @return a quantidade
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity: Nova quantidade.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
