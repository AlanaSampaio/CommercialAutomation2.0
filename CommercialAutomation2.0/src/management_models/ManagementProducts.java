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

package management_models;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.*;
import modeling_models.*;

/**
 * Classe responsavel por gerir o CRUD de produtos
 * @author Vanderleicio Junior
 * @author Alana Sampaio
 *
 */
public class ManagementProducts extends Management {
	/**
	 * Lista que guarda as informacoes do estoque
	 */
	private HashMap<String, ArrayList<Products>> stock = new HashMap<String, ArrayList<Products>>();
	
	/**
	 * Metodo responsavel por registrar os produtos no sistema
	 * 
	 * @param name: nome do produto
	 * @param price: preco do produto
	 * @param validity: validade do produto
	 * @param quantity: quantidade do produto
	 * @param provider: fornecedor que forneceu o produto
	 * @return o id do produto
	 */
	public String register(String name, BigDecimal price, LocalDate validity, int quantity, Providers provider) {
		Products newProduct = new Products (name, price, validity, quantity, provider);
		this.register(newProduct);
		addProductStock(newProduct);
		return newProduct.getId();
	}

	/**
	 * Metodo responsavel por editar as informacoes do produto
	 * 
	 * @param idPEdit: id a ser editado
	 * @param changedValue: nome do item a ser editado (nome, preco, validade, quantidade, fornecedor)
	 * @param newValue: valor novo a ser inserido
	 * 
	 * @throws IdDoesntExist: para quando o id nao existe
	 * @throws EntitiesNotRegistred: para quando a entidade nao for encontrada
	 */
	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
		Products productPEdit = (Products) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "nome":
				productPEdit.setName((String) newValue);
				break;
			case "preco":

				productPEdit.setPrice((BigDecimal) newValue);
				break;
			case "validade":
				productPEdit.setValidity((LocalDate) newValue);
				break;
			case "quantidade":
				productPEdit.setQuantity((int) newValue);

				break;
			case "fornecedor":
				productPEdit.setProvider((Providers) newValue);
				break;
		}
		
	}
	
	/**
	 * Metodo responsavel em adicionar produto no estoque
	 * 
	 * @param newProd: produto a ser inserido no estoque
	 */
	public void addProductStock(Products newProd) {
		boolean nameExist = this.stock.containsKey(newProd.getName());
		if (nameExist) {
			this.stock.get(newProd.getName()).add(newProd);
		} else {
			this.stock.put(newProd.getName(), new ArrayList<Products>());
			this.stock.get(newProd.getName()).add(newProd);
		}
		
	}
	
	/**
	 * Metodo responsavel por apagar item do estoque
	 * 
	 * @param prod: produto a ser apagado
	 */
	public void deleteProductStock(Products prod){
		boolean nameExist = this.stock.containsKey(prod.getName());
		if (nameExist) {
			this.stock.get(prod.getName()).remove(prod);
		}
	}
	
	/**
	 * 
	 * @param prod: produto a ser usado como busca
	 * @param quant: quantidade a ser removida
	 * 
	 * @throws NotEnoughProduct: para quando a quantidade de produtos nao forem suficientes
	 * @throws IdDoesntExist: para quando id nao existir
	 * @throws EntitiesNotRegistred: para quando entidade nao for encontrada
	 */
	public void removeQuantProd(Products prod, int quant) throws NotEnoughProduct, IdDoesntExist, EntitiesNotRegistred{
		// Remove a quantidade passada da quantidade do produto. Se o produto for zerado ele deletado.
		int quantBefore = prod.getQuantity();
		if (quantBefore <= quant) {
			this.delete(prod);
			throw new NotEnoughProduct(quant - quantBefore);
		} else {
			prod.setQuantity(quantBefore - quant);
		}
	}
	
	/**
	 * Metodo responsavel por remover grupo de itens
	 * 
	 * @param nameGroup: nome do item
	 * @param quant: quantidade do item
	 * 
	 * @throws NotEnoughStock: para quando a quantidade de produtos nao forem suficientes
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao for encontrada
	 */
	public void removeQuantGroup(String nameGroup, int quant) throws NotEnoughStock, IdDoesntExist, EntitiesNotRegistred{
		// Remove a quantidade "quant" passada dos produtos do conjunto.
		ArrayList<Products> prods = this.stock.get(nameGroup);
		int left = quant;
		while (left > 0) {
			try {
				this.removeQuantProd(prods.get(0), left);
				left = 0;
			} catch(NotEnoughProduct eProd) {
				left = eProd.getQuantLeft();
			}
		}
	}
	
	/**
	 * Metodo responsavel por mostrar a quantidade de itens
	 * 
	 * @param prodName: nome do produto
	 * @return a quantidade do produto
	 */
	public int getGroupQuantity(String prodName) {
		// Recebe o nome de um conjunto de produtos e retorna a 
		//quantidade total (soma da quantidade de todos os produtos desse conjunto).
		int totalQuantity = 0;
		ArrayList<Products> prods = this.stock.get(prodName);
		for(Products prod : prods) {
			totalQuantity += prod.getQuantity();
		};
		return totalQuantity;
	}
	
	/**
	 * Metodo responsavel por verificar se todos os produtos sao o suficiente
	 * 
	 * @param groupsNeed: lista contendo os produtos
	 * @throws NotEnoughStock: para quando a quantidade de produtos nao forem suficientes
	 */
	public void checkAllProductsEnough(HashMap<String, Integer> groupsNeed)throws NotEnoughStock{
		for (HashMap.Entry<String,Integer> nameQuant : groupsNeed.entrySet()) {
			if(this.getGroupQuantity(nameQuant.getKey()) < nameQuant.getValue()) {
				throw new NotEnoughStock();
			}
		};
	}
	
	/**
	 * Metodo responsavel por atualizar estoque
	 * 
	 * @param groupsUsed: lista contendo os produtos
	 * 
	 * @throws NotEnoughStock: para quando a quantidade de produtos nao forem suficientes
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando a entidade nao foi registrada
	 */
	public void updateStock(HashMap<String, Integer> groupsUsed) throws NotEnoughStock, IdDoesntExist, EntitiesNotRegistred{
		try {
			this.checkAllProductsEnough(groupsUsed);
		
			for (HashMap.Entry<String,Integer> nameQuant : groupsUsed.entrySet()) {
				this.removeQuantGroup(nameQuant.getKey(), nameQuant.getValue());
			};
		} catch(NotEnoughStock e){
				throw new NotEnoughStock();
		}
	}
	
	/**
	 * Metodo responsavel por deletar produtos
	 * 
	 * @param prod: produto a ser removito
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando a entidade nao foi registrada
	 */
	public void delete(Products prod) throws IdDoesntExist, EntitiesNotRegistred {
		this.deleteProductStock(prod);
		this.delete(prod.getId());
	}
	
	/**
	 * Metodo herdado da classe gerenciamento, responsavel por mostrar na tela os dados do produto
	 */
	@Override
	public void list(boolean allInformations) {
		this.getList().forEach(element -> {
			Products prod = (Products) element;
		    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
		    		.withResolverStyle(ResolverStyle.STRICT);    
			System.out.println("ID: " + prod.getId() + "\n" + 
							   "Nome: " + prod.getName()+ "\n" + 
							   "Preco: R$ " + prod.getPrice());
			
			if (allInformations) {
			System.out.println("Validade: " + prod.getValidity().format(dateTimeFormatter)+ "\n" +
							   "Quantidade: " + prod.getQuantity()+ " unidades \n");
			}
			
			System.out.println("Fornecedor: \n" + 
							   "\tID: " + prod.getProvider().getId() +
							   "\tNome: "+ prod.getProvider().getName());
			
			System.out.println("\n");
		});
	}
	
	/**
	 * 
	 * @return estoque
	 */
	public HashMap<String, ArrayList<Products>> getStock() {
		return stock;
	}
}
