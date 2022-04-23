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
			if (id == listManagement.get(i).getId()) {
				return listManagement.get(i);
			}
		}
		return null;
	}
	
	public Users searchEntitiesNick(String nick) {
		for (int i = 0; i < listManagement.size(); i++) {
			//System.out.println(((Users) listManagement.get(i)).getNickname()));
			if (nick.equals((Users) listManagement.get(i)).getNickname()) {
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

