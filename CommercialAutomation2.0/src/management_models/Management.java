package management_models;

import java.util.ArrayList;

import exceptions.*;
import modeling_models.Entities;
import modeling_models.Users;

public abstract class Management {
	
private ArrayList<Entities> listManagement = new ArrayList<>();

	public void register(Entities element) {
		listManagement.add(element);
	}
	
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
	
	public Users searchEntitiesNick(String nick) {
		for (int i = 0; i < listManagement.size(); i++) {
			String currentNick = ((Users) listManagement.get(i)).getNickname();
			if (nick.equals(currentNick)) {
				return (Users) listManagement.get(i);
			}
		}
		return null;
	}
	
	public Users searchEntitiesPassword(String pass) {
		for (int i = 0; i < listManagement.size(); i++) {
			String currentPassword = ((Users) listManagement.get(i)).getPassword();
			if (pass.equals(currentPassword)) {
				return (Users) listManagement.get(i);
			}
		}
		return null;
	}
	
	//public abstract void edit(String idEntities, String changedValue, Object newValue);

	public ArrayList<Entities> getList(){
		return listManagement;
	}
	
	public int sizeList() {
		return listManagement.size();
	}
	
	public abstract void list(boolean allInformations) throws EntitiesNotRegistred;
	
	public void delete(String idEntities) throws IdDoesntExist, EntitiesNotRegistred {
		try {
			Entities element = searchEntities(idEntities);
			listManagement.remove(element);
		} catch(IdDoesntExist eId) {
			throw new IdDoesntExist();
		}
	}
}

