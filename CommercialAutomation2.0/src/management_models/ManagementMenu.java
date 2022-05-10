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

import java.util.HashMap;

import exceptions.*;
import modeling_models.Items;
import modeling_models.Products;

/**
 * Classe responsavel por gerir o CRUD do cardapio
 * @author Vanderleicio Junior
 * @author Alana Sampaio 
 *
 */
public class ManagementMenu extends Management {

	/**
	 * Metodo responsavel por registrar um item no cardapio
	 * 
	 * @param name: nome do item
	 * @param description: descricao do item
	 * @param price: preco do item
	 * @param category: categoria do item
	 * @param composition: produtos que compoem o item
	 * @return o id do novo item cadastrado
	 */
	public String register(String name, String description, BigDecimal price, String category, HashMap<String, Integer> composition) {
		Items newItem = new Items(name, description, price, category, composition);
		this.register((Items) newItem);
		return newItem.getId();
	}

	/**
	 * Metodo responsavel por editar informacors dos itens no cardapio
	 * 
	 * @param idPEdit: id do item
	 * @param changedValue: nome do item a ser editado (nome, descricao, preco, categoria)
	 * @param newValue: valor a substituir 
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao foi registrada
	 */
	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		switch(changedValue) {
		case "nome":
			itemPEdit.setName((String) newValue);
			break;
		case "descricao":
			itemPEdit.setDescription((String) newValue);
			break;
		case "preco":

			itemPEdit.setPrice((BigDecimal) newValue);
			break;
		case "categoria":
			itemPEdit.setCategoryItems((String) newValue);
			break;
		}
	}
	
	/**
	 * Metodo responsavel por usar da classe Itens para adicionar um produto a um item que compoe o cardapio
	 * 
	 * @param idPEdit: id do item
	 * @param productPAdd: produto a ser adicionado
	 * @param quantity: quantidade 
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao foi registrada
	 */
	public void addProductsItems(String idPEdit, Products productPAdd, int quantity) throws IdDoesntExist, EntitiesNotRegistred{
		Items itemPEdit;
		itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.addProduct(quantity, productPAdd.getName());
	}
	
	/**
	 * Usa o metodo da classe Itens para remover um produto de um item do cardapio. Deleta o item, caso ele nao tenha mais produtos.
	 * 
	 * @param idPEdit: id do item que sera editado
	 * @param produtoPRemover: produto que sera removido
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao foi registrada
	 */
	public void removeProductFromItem(String idPEdit, Products produtoPRemover) throws IdDoesntExist, EntitiesNotRegistred {

		Items itemPEdit = (Items) this.searchEntities(idPEdit);
		itemPEdit.deleteProduct(produtoPRemover.getName());
		int size = itemPEdit.getCompositionSize();
		if (size == 0) {
			this.delete(idPEdit);
		}
	}

	/**
	 * Metodo herdado de gerenciamento responsavel por mostrar na tela os dados do cardapio
	 */
	@Override
	public void list(boolean allInformations) throws EntitiesNotRegistred{
		if (this.getList().size() == 0) {
			throw new EntitiesNotRegistred();
		}
		this.getList().forEach(element -> {
			Items item = (Items) element; 
			System.out.println("ID: " + item.getId() + "\n" + 
							   "Nome: " + item.getName()+ "\n" + 
							   "Preco: R$ " + item.getPrice());
			
			if (allInformations) {
				System.out.println("Descricao: " + item.getDescription()+ "\n" +
								   "Categoria: " + item.getCategoryItems()+ "\n" +
							       "Composto de: \n");
			
				item.getComposition().forEach((prod, quantity) ->{
					if (prod != null) {
					System.out.println("\tNome: " + prod +
								   	   "\n\tQntd por item: " + quantity);
					}
				});
			}
			System.out.println("\n");
		});
	}
}
