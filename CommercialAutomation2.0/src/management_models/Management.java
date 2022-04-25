package management_models;

import java.util.ArrayList;
import modeling_models.Entities;
import modeling_models.Users;

public abstract class Management {
	
private ArrayList<Entities> listManagement = new ArrayList<>();

	public void register(Entities element) {
		listManagement.add(element);
	}
	
	public Entities searchEntities(String id) {
		for (int i = 0; i < listManagement.size(); i++) {
			String currentEntities = ((Users) listManagement.get(i)).getId();
			if (id.equals(currentEntities)) {
				return listManagement.get(i);
			}
		}
		return null;
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
	
	public abstract void edit(String idEntities, String changedValue, Object newValue);

	public ArrayList<Entities> list() {
		return listManagement;
	}
	
	public int sizeList() {
		return listManagement.size();
	}
	
	public void delete(String idEntities) {
		Entities element = searchEntities(idEntities);
		listManagement.remove(element);
	}
}

