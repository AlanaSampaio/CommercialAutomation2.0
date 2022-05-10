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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import modeling_models.Items;
import modeling_models.Sales;
 /**
  * Classe responsavel por gerir o CRUD das vendas
  * @author Vanderleicio Junior
  * @author Alana Sampaio
  *
  */
public class ManagementSales extends Management {
	/**
	 * Adiciona uma vendo ao ArrayList que os contem
	 * 
	 * @param day: dia que a compra foi feita
	 * @param hour: hora que a compra foi feita
	 * @param itemsPurchased: itens a serem inseridos no carrinho
	 * @param paymentMethod: forma de pagamento
	 * @return o id que foi cadastrado
	 */
	public String register(LocalDate day, LocalTime hour, ArrayList<Items> itemsPurchased, String paymentMethod) {
		Sales newSale = new Sales(day, hour, paymentMethod, itemsPurchased);
		this.register(newSale);
		return newSale.getId();
	}

	/**
	 * Metodo responsavel por editar informacoes de um fornecedor ja registrado
	 * 
	 * @param idPEdit: id da venda a ser editada
	 * @param changedValue: nome do item que sera editado (dia, hora, modo de pagamento)
	 * @param newValue: valor a substituir
	 * 
	 * @throws IdDoesntExist: para quando o id nao existe
	 * @throws EntitiesNotRegistred: para quando constar que a entidade nao foi registrada
	 */
	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		switch(changedValue) {
			case "dia":
				salesPEdit.setDay((LocalDate) newValue);
				break;
			case "horario":
				salesPEdit.setHour((LocalTime) newValue);
				break;
			case "modoDePagamento":
				salesPEdit.setPaymentMethod((String) newValue);
				break;
		}
	}
	
	/**
	 * Medoto que adiciona um item ao carrinho
	 * 
	 * @param idPEdit: id do item
	 * @param itemPAdd: item a ser adicionado
	 * 
	 * @throws IdDoesntExist: para quando o id n existir
	 * @throws EntitiesNotRegistred: para quando entidade nao for encontrada
	 */
	public void addItem(String idPEdit, Items itemPAdd) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEdit);
		salesPEdit.addItem(itemPAdd);
	}
	
	/**
	 * Metodo responsavel por apagar um item
	 * 
	 * @param idPEditar: id do item
	 * @param itemPDeletar: item a ser deletado
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao for encontrada
	 */
	public void deleteItem(String idPEditar, Items itemPDeletar) throws IdDoesntExist, EntitiesNotRegistred {
		Sales salesPEdit = (Sales) this.searchEntities(idPEditar);
		int size = salesPEdit.deleteItem(itemPDeletar);
		if (size == 0) {
			this.delete(idPEditar);
		}
	}
	
	/**
	 * Metodo responsavel por mostrar as vendas feitas
	 */
	@Override
	public void list(boolean allInformations) {
		this.getList().forEach(element -> {
			Sales sale = (Sales) element; 
			System.out.println("ID: " + sale.getId() + "\n" + 
							   "Dia: " + sale.getDay()+ "\n" + 
							   "Horario: " + sale.getHour()+ "\n" +
							   "Preco total: R$" + sale.getPriceTotal() + "\n" +
							   "Modo de Pagamento: " + sale.getPaymentMethod()+ "\n" +
							   "Itens vendidos: \n");
			
			sale.getItemsPurchased().forEach(item -> {
				System.out.println("\tID: " + item.getId() +
								   "\n\tNome: " + item.getName() +
								   "\n\tPreco: " + item.getPrice());
			});
			System.out.println("\n");
		});
		
	}
}
