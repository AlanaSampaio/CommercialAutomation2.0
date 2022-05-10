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

import java.util.ArrayList;

import exceptions.*;
import modeling_models.Entities;
import modeling_models.Users;
/**
 *Classe abstrata que sera herdada por todos os gerenciamentos.
 *@author Vanderleicio Junior
 *@author Alana Sampaio
 */
public abstract class Management {
	/**
	 * ArrayList que guardara as Entidades cadastradas pelos gerenciamentos.
	 */
private ArrayList<Entities> listManagement = new ArrayList<>();

	/**
	 *Metodo geral para adicionar a entidade cadastrada na ArrayList.
	 *@param element: Entidade a ser cadastrada
	 */
	public void register(Entities element) {
		listManagement.add(element);
	}
	/**
	 * Procura e retorna uma Entidade a partir do seu ID, que sera convertida na subclasse que a chamar.
	 * 
	 * @param id: Id da entidade a ser procurada
	 * @return o id
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao foi registrada
	 */
	public Entities searchEntities(String id) throws IdDoesntExist, EntitiesNotRegistred{
		
		if (listManagement.size() == 0) {
			throw new EntitiesNotRegistred();
		}
		
		for (int i = 0; i < listManagement.size(); i++) {
			String currentEntities = ((Entities) listManagement.get(i)).getId();
			if (id.equals(currentEntities)) {
				return listManagement.get(i);
			}
		}
		throw new IdDoesntExist();
	}
	
	/**
	 * Procura e retorna uma Entidade a partir do seu nickname
	 * @param nick: nickname 
	 * @return item da lista
	 */
	public Users searchEntitiesNick(String nick) {
		for (int i = 0; i < listManagement.size(); i++) {
			String currentNick = ((Users) listManagement.get(i)).getNickname();
			if (nick.equals(currentNick)) {
				return (Users) listManagement.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Procura e retorna uma Entidade a partir de sua senha
	 * 
	 * @param pass: senha
	 * @return item da lista
	 */
	public Users searchEntitiesPassword(String pass) {
		for (int i = 0; i < listManagement.size(); i++) {
			String currentPassword = ((Users) listManagement.get(i)).getPassword();
			if (pass.equals(currentPassword)) {
				return (Users) listManagement.get(i);
			}
		}
		return null;
	}
	

	/**
	 * Responsavel por mostrar a lista
	 * 
	 * @return lista
	 */
	public ArrayList<Entities> getList(){
		return listManagement;
	}
	
	/**
	 * Responsavel por mostrar o tamanho da lista
	 * 
	 * @return tamanho da lista
	 */
	public int sizeList() {
		return listManagement.size();
	}
	 /**
	  * Metodo abstrato para listar informacoes
	  * @param allInformations: selecionar quais informacoes serao divulgadas
	  * @throws EntitiesNotRegistred EntitiesNotRegistred: para quando entidade nao foi registrada
	  */
	public abstract void list(boolean allInformations) throws EntitiesNotRegistred;
	
	/**
	 * Metodo responsavel por apagar da ArrayList
	 * 
	 * @param idEntities: id da entidade que sera deletada
	 * 
	 * @throws IdDoesntExist: para quando id nao existe
	 * @throws EntitiesNotRegistred: para quando entidade nao foi registrada
	 */
	public void delete(String idEntities) throws IdDoesntExist, EntitiesNotRegistred {
		try {
			Entities element = searchEntities(idEntities);
			listManagement.remove(element);
		} catch(IdDoesntExist eId) {
			throw new IdDoesntExist();
		}
	}
}

