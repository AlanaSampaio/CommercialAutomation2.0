package management_models;

import modeling_models.Users;

public class ManagementUsers extends Management{

	public void register(String nick, String password, String name, String category) {
		Users userRegister = (Users) this.searchEntitiesNick(nick);
		System.out.println(this.checkSizeList());
		System.out.println(userRegister);
		
		if (this.checkSizeList() == false) {
			Users newUser = new Users(nick, password, name, category);
			this.register(newUser);	
		} else {
			if (userRegister != null) {
				Users newUser = new Users(nick, password, name, category);
				this.register(newUser);
				System.out.println("Foi!");
			} else if (userRegister == null) {
				System.out.println("Nickname já existente, tente outro!");
			}
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
	
	@Override
	public void edit(String idPEdit, String changedValue, Object newValue) {
		Users userPEdit = (Users) this.searchEntities(idPEdit);
		switch(changedValue) {
		case "nome":
			userPEdit.setName((String) newValue);
			break;
		case "cargo":
			userPEdit.setCategory((String) newValue);
			break;
		}
	}
}

