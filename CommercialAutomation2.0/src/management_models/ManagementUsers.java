package management_models;

import java.util.Scanner;

import exceptions.*;
import modeling_models.Users;

public class ManagementUsers extends Management{
	
	public void dataRegister() {
		// Método para receber os dados do usuário e fazer o cadastro a partir deles.
		String nick, password, name, category;
		Scanner input = new Scanner(System.in);
		System.out.println("Insira o nickname a ser registrado: ");
		nick = input.next();
		System.out.println("Insira a senha a ser registrado: ");
		password = input.next();
		System.out.println("Insira o seu nome: ");
		name = input.next();
		System.out.println("Insira o seu cargo: ");
		category = input.next();
		System.out.println("\n");
		
		try {
		this.register(nick, password, name, category);
		System.out.println("Usuário cadastrado com sucesso!");
		} catch(ExistentNicknameException eNick) {
			System.out.println(eNick.getMessage());
		}
	}
	
	
	public void register(String nick, String password, String name, String category) throws ExistentNicknameException {
		Users userRegister = (Users) this.searchEntitiesNick(nick);
		
		if (this.checkSizeList() == false) {
			Users newUser = new Users(nick, password, name, category);
			this.register(newUser);	
		} else {
			if (userRegister == null) {
				Users newUser = new Users(nick, password, name, category);
				this.register(newUser);
			} else if (userRegister != null) {
				throw new ExistentNicknameException();
			}
		}
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
	
	public void edit(String idPEdit, String changedValue, Object newValue) throws ExistentNicknameException {
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
	public void list() {
		this.getList().forEach(element -> {
			Users prov = (Users) element;
			System.out.println("ID: " + prov.getId() + "\n" + 
							   "Nome: " + prov.getName()+ "\n" + 
							   "Cargo: " + prov.getCategory()+ "\n" +
							   "NickName: " + prov.getNickname());
		});
	}
}

