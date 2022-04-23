package main;

import java.util.Scanner;

import management_models.*;
import modeling_models.Users;

public class Main {
	public static void main(String args[]) {
		
		Scanner input = new Scanner(System.in);
		
		ManagementUsers managementUser = new ManagementUsers();
		
		int n = 0;
		while (n == 0) {
			
			//if (managementUser.checkSizeList() == false) {
				String nick, password, name, category;
				System.out.println("Sistema não possui nenhum registro, faça o primeiro cadastro no sistema!");
				System.out.println("Insira o nickname a ser registrado: ");
				nick = input.nextLine();
				System.out.println("Insira a senha a ser registrada: ");
				password = input.nextLine();
				System.out.println("Insira o nome do usuário: ");
				name = input.nextLine();
				System.out.println("Insira o cargo do funcionário: ");
				category = input.nextLine();
							
				managementUser.register(nick, password, name, category);
			//} 
			
			
			
			
			/*System.out.println("1. Acessar login \n2. Cadastrar login \n3. Sair");
			int answer = input.nextInt();
			n = answer;
			
			switch(answer) {
			case 3:
				System.out.println("Saindo...");
				break;
			case 2:
				System.out.println("Cadastrando...");
			case 1:
				System.out.println("Cadastrando..");
			}*/
		}
	}
	
	public void login() {
		Scanner input = new Scanner(System.in);
		String nick, password;
		
		System.out.println("LOGIN");
		System.out.println("Insira seu nickname: ");
		nick = input.nextLine();
		System.out.println("Insira sua senha: ");
		password = input.nextLine();
	}
	
	public static void datasUsers(int operation, ManagementUsers managementSimulator) {
		String id1;
		switch(operation) {
		case 1:
			managementSimulator.register("carlinho", "abc123", "nome1", "funcionario");
			System.out.println("Usuario cadastrado com sucesso.");
			break;
		case 2:
			id1 = managementSimulator.list().get(0).getId();
			managementSimulator.edit(id1, "nome", "nome2");
			System.out.println("Atributo do Usuario alterado com sucesso.");
			break;
		case 3:
			id1 = managementSimulator.list().get(0).getId();
			managementSimulator.delete(id1);
			break;
		case 4:
			managementSimulator.list().forEach(simulator -> System.out.println(((Users)simulator).getId() + "\n" + 
																((Users)simulator).getName()+ "\n" + 
																((Users)simulator).getCategory()));
			break;
		}
	}
}

