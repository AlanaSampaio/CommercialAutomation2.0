package main;

import java.util.ArrayList;
import java.util.Scanner;

import management_models.*;
import modeling_models.Users;

public class Main {
	private static ArrayList<String> ids = new ArrayList<String>();
	
	static ManagementUsers managementUser = new ManagementUsers();
	
	public static void main(String args[]) {
		
		Scanner input = new Scanner(System.in);
		
		int n = 0;
		while (n == 0) {
			System.out.println("AUTOMAÇÃO COMERCIAL \n1 - Logar no sistema \n2 - Sair do sistema");
			int answer = input.nextInt();
			
			if (answer == 1) {
				String nick, password, name, category;
				if (managementUser.checkSizeList() == false) {
					System.out.println("Sistema não possui nenhum registro, faça o primeiro cadastro no sistema!");
					System.out.println("Insira o nickname a ser registrado: ");
					nick = input.next();
					System.out.println("Insira a senha a ser registrada: ");
					password = input.next();
					System.out.println("Insira o seu nome: ");
					name = input.next();
					System.out.println("Insira o seu cargo: ");
					category = input.next();
								
					managementUser.register(nick, password, name, category);
					login();
				} else {
					System.out.println("Insira o nickname a ser registrado: ");
					input.next();
					nick = input.nextLine();
					System.out.println("Insira a senha a ser registrada: ");
					password = input.nextLine();
					System.out.println("Insira o seu nome: ");
					name = input.nextLine();
					System.out.println("Insira o seu cargo: ");
					category = input.nextLine();
								
					managementUser.register(nick, password, name, category);
					login();
				}
			} else if (answer == 2) {
				System.out.println("SISTEMA ENCERRADO");
				break;
			} else {
				System.out.println("Opção inválida.");
			}
		}
	}
	
	public static void login() {
		Scanner input = new Scanner(System.in);
		String nick, password;
		
		System.out.println("LOGIN");
		System.out.println("Insira seu nickname: ");
		nick = input.nextLine();
		System.out.println("Insira sua senha: ");
		password = input.nextLine();
		
		boolean option = managementUser.checkLogin(nick, password);
		
		if (option == false) {
			System.out.println("\nNickname ou senha incorreto, tente novamente!\n");
		} else {
			optionsMenu();
		}
	}
	
	public static void optionsMenu() {
		String[] options = { "cadastrar", "editar", "excluir", "listar", "gerar relatório", "encerrar"};
		String[] entities = {"usuario", "fornecedor", "item", "produto", "venda"};
		int option = 0;
		Scanner input = new Scanner(System.in);
	
		do {
			System.out.println("\nO que você deseja fazer?");
			
			for (int i = 0; i < 6; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			option = input.nextInt();
			
			if (option >= 1 && option <= 4) {
			System.out.println("O que você deseja " + options[option - 1] + "?");
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + " - P/ " + entities[i]);
			}
			int nEntities = input.nextInt();
			System.out.println("\n");
			switch (nEntities) {
				case 1:
					datasUsers(option, managementUser);
					break;
				/*case 2:
					menuSimulaFornecedor(opcao, gerenForn);
					break;
				case 3:
					menuSimulaItem(opcao, gerenCard);
					break;
				case 4:
					menuSimulaProduto(opcao, gerenProd);
					break;
				case 5:
					menuSimulaVenda(option, managementUser);
					break;*/
			}
			System.out.println("\n");
			} else if (option == 5) {
				System.out.println("\nGerando relatório\n");
			} else if (option != 6) {
				System.out.println("Opição inválida. Tente de novo.");
			}
		} while (option != 6);
		System.out.println("Deslogando do sistema.");
		}
	
	public static boolean idExist(String idCheck) {
		return ids.contains(idCheck);
	}
	
	public static void addId(String id) {
		ids.add(id);
	}

	public ArrayList<String> getIds() {
		return ids;
	}
	
	public static void datasUsers(int operation, ManagementUsers managementSimulator) {
		Scanner input = new Scanner(System.in);
		String nick, password, name, category;
		String id1;
		
		switch(operation) {
		case 1:
			System.out.println("Insira o nickname a ser registrado: ");
			nick = input.nextLine();
			System.out.println("Insira a senha a ser registrada: ");
			password = input.nextLine();
			System.out.println("Insira o seu nome: ");
			name = input.nextLine();
			System.out.println("Insira o seu cargo: ");
			category = input.nextLine();
			
			managementSimulator.register(nick, password, name, category);
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
																((Users)simulator).getNickname() + "\n" +
																((Users)simulator).getPassword() + "\n" +
																((Users)simulator).getCategory()));
			break;
		}
	}
}

