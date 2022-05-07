package management_models;

import exceptions.*;
import modeling_models.Users;

public class ManagementUsers extends Management{
	
	private String idUserOn;
	
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
	
	
	public void checkLogin(String nick, String pass) throws LoginDoesntMatch{
		Users userRegisterNick = (Users) this.searchEntitiesNick(nick);
		Users userRegisterPass = (Users) this.searchEntitiesPassword(pass);
		if (!(userRegisterNick != null && userRegisterPass != null)) {
			throw new LoginDoesntMatch();
		}
	}
	
	
	public boolean checkSizeList() {
		int check = this.sizeList();
		if(check == 0) {
			return false;
		} else {
			return true;
		}
	}
	
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
	 * @return the idUserOn
	 */
	public String getIdUserOn() {
		return idUserOn;
	}


	/**
	 * @param idUserOn the idUserOn to set
	 */
	public void setIdUserOn(String idUserOn) {
		this.idUserOn = idUserOn;
	}
}

