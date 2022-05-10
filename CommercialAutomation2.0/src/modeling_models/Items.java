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
import java.util.HashMap;

/**
 * Representa os itens que serão cadastrados.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 * 
 */
public class Items extends Entities {
	/**
	 * Nome do item.
	 */
	private String name;
	/**
	 * Descricao do item.
	 */
	private String description;
	/**
	 * Preco do item
	 */
	private BigDecimal price;
	/**
	 * Categoria do item
	 */
	private String categoryItems;
	/**
	 * Hash Map com o nome do grupo que compõe o item e a quantidade de 
	 * produtos necessarias para fazer um item 
	 */
	private HashMap<String, Integer> composition = new HashMap<String, Integer>();

	
	/**
	 * @param name: String representando o nome do item.
	 * @param description: String representando a descrição do item
	 * @param price: BigDecimal representando o preco do item.
	 * @param categoryItems: String representando a categoria do item
	 * @param composition: HashMap representando a composição do item. 
	 */
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

	/**
	 * Adiciona um produto a composição do item.
	 * @param quantity: Quantidade do produto para fazer um item.
	 * @param product: Nome do grupo ao qual o produto pertence.
	 */
	public void addProduct(int quantity, String product) {
		this.composition.put(product, quantity);
	}
	
	/**
	 * Remove um produto da composicao do item.
	 * @param prod: Nome do grupo que será removido.
	 */
	public void deleteProduct(String prod) {
		this.composition.remove(prod);
	}
	
	/**
	 * 
	 * @return composicao do item
	 */
	public int getCompositionSize() {
		return this.composition.size();
	}
	
	/**
	 * @return nome do item
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name: novo nome do item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return descricao do item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description: nova descricao do item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return preco do item
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price: novo preco do item
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return categoria do item
	 */
	public String getCategoryItems() {
		return categoryItems;
	}

	/**
	 * @param categoryItems: nova categoria do item
	 */
	public void setCategoryItems(String categoryItems) {
		this.categoryItems = categoryItems;
	}


	/**
	 * @return composicao do item
	 */
	public HashMap<String, Integer> getComposition() {
		return composition;
	}

	/**
	 * @param composition: nova composicao do item
	 */
	public void setComposition(HashMap<String, Integer> composition) {
		this.composition = composition;
	}
}
