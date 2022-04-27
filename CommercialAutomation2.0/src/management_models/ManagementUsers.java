package management_models;

import java.util.Scanner;

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
					
		this.register(nick, password, name, category);
	}
	
	
	public void register(String nick, String password, String name, String category) {
		Users userRegister = (Users) this.searchEntitiesNick(nick);
		
		if (this.checkSizeList() == false) {
			Users newUser = new Users(nick, password, name, category);
			this.register(newUser);	
			System.out.println("\nUsuario cadastrado com sucesso.\n");
		} else {
			if (userRegister == null) {
				Users newUser = new Users(nick, password, name, category);
				this.register(newUser);
				System.out.println("\nUsuario cadastrado com sucesso.\n");
			} else if (userRegister != null) {
				System.out.println("\nNickname já existente, tente outro!\n");
			}
		}
	}
	
	public boolean checkLogin(String nick, String pass) {
		Users userRegisterNick = (Users) this.searchEntitiesNick(nick);
		Users userRegisterPass = (Users) this.searchEntitiesPassword(pass);
		if (userRegisterNick != null && userRegisterPass != null) {
			return true;
		}
		return false;
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
		case "nickname":
			userPEdit.setNickname((String) newValue);
			break;
		case "senha":
			userPEdit.setPassword((String) newValue);
			break;
		}
	}
}

