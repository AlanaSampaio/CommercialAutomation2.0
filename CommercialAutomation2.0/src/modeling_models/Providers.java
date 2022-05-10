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

import java.util.ArrayList;

/**
 * Classe dos fornecedores dos produtos do sistema.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class Providers extends Entities {
	/**
	 * Nome do fornecedor
	 */
	private String name;
	/**
	 * CNPJ do fornecedor
	 */
	private String cnpj;
	/**
	 * Endereco do fornecedor
	 */
	private String address;
	/**
	 * Produtos fornecidos pelo fornecedor
	 */
	private ArrayList<Products> productsProvided;

	/**
	 * @param name: String representando o nome do fornecedor
	 * @param cnpj: String representando o cnpj do fornecedor
	 * @param address: String representando o endereco do fornecedor
	 */
	public Providers(String name, String cnpj, String address) {
		super();
		this.name = name;
		this.cnpj = cnpj;
		this.address = address;

		this.productsProvided = new ArrayList<Products>();
		generatorCode("F");
	}
	
	/**
	 * Lista no terminal os produtos fornecidos por esse fornecedor.
	 */
	public void listProdProvided() {
		if (getProductsProvided() != null) {
		this.getProductsProvided().forEach(product -> {
			System.out.println("\tID: " + product.getId() +
							   "\tNome: " + product.getName());
			System.out.println("\n");
		});
		}
	}
	
	/**
	 * Adiciona um produto na lista de produtos fornecidos.
	 * @param prod: Produto a ser adicionado
	 */
	public void addProduct(Products prod) {
		this.productsProvided.add(prod);	
	}
	
	
	/**
	 * Remove um produto dos produtos fornecidos
	 * @param idProd: Id do produto a ser removido
	 */
	public void removeProduct(String idProd) {
		for (int i = 0; i < this.productsProvided.size(); i++) {
			String currentProd = (this.productsProvided.get(i)).getId();
			if (idProd.equals(currentProd)) {
				this.productsProvided.remove(i);
				}
			}
	}
	
	/**
	 * @return o name
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
	 * @return o cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj: Novo cnpj
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return o endereco
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address: Novo endereco
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return os produtos fornecidos
	 */
	public ArrayList<Products> getProductsProvided() {
		return productsProvided;
	}

	/**
	 * @param productsProvided: Novos produtos fornecidos
	 */
	public void setProductsProvided(ArrayList<Products> productsProvided) {
		this.productsProvided = productsProvided;
	}

}
