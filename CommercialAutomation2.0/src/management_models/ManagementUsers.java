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

import exceptions.*;
import modeling_models.Users;

/**
 * Classe responsavel por gerir o CRUD dos usuarios do sistema
 * 
 * @author Vanderleicio Junior
 * @author Alana Sampaio
 */
public class ManagementUsers extends Management{
	/**
	 * Usuario online
	 */
	private String idUserOn;
	
	/**
	 * Adiciona um usuario a uma ArrayList que os contem.
	 * @param nick: nickname do usuario, único para cada usuario
	 * @param password: senha de acesso ao sistema
	 * @param name: nome do usuario
	 * @param category: categoria do usuario (funcionario ou gerente)
	 * 
	 * @throws ExistentNicknameException: para quando o nick ja existe impossibilitando o cadastro com esse valor
	 */
	public String register(String nick, String password, String name, String category) throws ExistentNicknameException {
		Users userRegister = (Users) this.searchEntitiesNick(nick);
		Users newUser = new Users(nick, password, name, category);
		
		if (userRegister == null) {
			this.register(newUser);
		} else if (userRegister != null) {
			throw new ExistentNicknameException();
		}
		return newUser.getId();
	}
	
	/**
	 * Metodo responsavel por permitir o login do usuario ao sistema
	 * @param nick: nickname do usuario ja cadastrado no sistema
	 * @param pass: senha do usuario
	 */
	public void checkLogin(String nick, String pass) throws LoginDoesntMatch{
		Users userRegisterNick = (Users) this.searchEntitiesNick(nick);
		Users userRegisterPass = (Users) this.searchEntitiesPassword(pass);
		if (!(userRegisterNick != null && userRegisterPass != null)) {
			throw new LoginDoesntMatch();
		}
	}
	
	/**
	 * Metodo responsavel por informar se a lista esta vazia ou nao
	 */
	public boolean checkSizeList() {
		int check = this.sizeList();
		if(check == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Metodo responsavel por editar os dados dos usuarios
	 * @param idPEdit: id do usuario para edicao
	 * @param changedValue: nome do item que devera ser editado (nome, cargo, nick, senha)
	 * @param newValue: novo valor a substituir item ja existente 
	 * 
	 * @throws ExistentNicknameException: para quandoo nick ja foi cadastrado
	 * @throws IdDoesntExist: para quando o id nao existe
	 * @throws EntitiesNotRegistred: para quando a entidade nao foi registrada
	 */
	public void edit(String idPEdit, String changedValue, Object newValue) throws ExistentNicknameException, IdDoesntExist, EntitiesNotRegistred {
		Users userPEdit = (Users) this.searchEntities(idPEdit);
		String newString = (String) newValue;
		switch(changedValue) {
		case "nome":
			userPEdit.setName(newString);
			break;
		case "cargo":
			userPEdit.setCategory(newString);
			break;
		case "nickname":
			Users nickExist = (Users) this.searchEntitiesNick(newString);
			if (nickExist == null) {
				userPEdit.setNickname(newString);
			} else {
				throw new ExistentNicknameException();
			}
			break;
		case "senha":
			userPEdit.setPassword((String) newValue);
			break;
		}
	}
	
	/**
	 * Metodo gerdado de Management para listar os usuarios na tela
	 * @param allInformation
	 */
	@Override
	public void list(boolean allInformations) {
		this.getList().forEach(element -> {
			Users prov = (Users) element;
			System.out.println("ID: " + prov.getId() + "\n" + 
							   "Nome: " + prov.getName()+ "\n" + 
							   "Cargo: " + prov.getCategory()+ "\n" +
							   "NickName: " + prov.getNickname());
			System.out.println("\n");
		});
	}


	/**
	 * @return idUserOn mostra o valor de uruario online
	 */
	public String getIdUserOn() {
		return idUserOn;
	}


	/**
	 * @param idUserOn recebe o valor de usuario online
	 */
	public void setIdUserOn(String idUserOn) {
		this.idUserOn = idUserOn;
	}
}

