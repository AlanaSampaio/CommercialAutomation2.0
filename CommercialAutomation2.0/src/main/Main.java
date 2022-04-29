package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import management_models.*;
import modeling_models.Providers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.*;

public class Main {
	private static ArrayList<String> ids = new ArrayList<String>();
	
	static ManagementUsers managementUser = new ManagementUsers();
	static ManagementProviders managementProvider = new ManagementProviders();
	
	//SETTADO PRA TESTES
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		int answer = 1;
		while (answer != 2) {
			System.out.println("AUTOMAÇÃO COMERCIAL \n1 - Logar no sistema \n2 - Sair do sistema");
			//answer = input.nextInt();
			
			answer = 1;
			
			if (answer == 1) {
				if (managementUser.checkSizeList() == false) {
					System.out.println("Sistema não possui nenhum registro, faça o primeiro cadastro no sistema!");
					datasUsers(1, managementUser, input);
				}
				login(input);
				optionsMenu(input);
			} else if (answer == 2) {
				System.out.println("SISTEMA ENCERRADO");
				answer = 2;
			} else {
				System.out.println("Opção inválida.");
			}
		}
		input.close();
	}
	
	public static void optionsMenu(Scanner inputOpt) {
		String[] options = { "cadastrar", "editar", "excluir", "listar", "gerar relatório", "encerrar"};
		String[] entities = {"usuario", "fornecedor", "item", "produto", "venda"};
		int option = 0;
		
		//Mostrar opções de operações
		do {
			System.out.println("\nO que você deseja fazer?");
			
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			//Opção escolhida
			option = inputOpt.nextInt();
			
			
			if (option >= 1 && option <= options.length) {
			System.out.println("O que você deseja " + options[option - 1] + "?");
			//Mostrar entidades que são alteráveis
			for (int i = 0; i < entities.length; i++) {
				System.out.println((i + 1) + " - P/ " + entities[i]);
			}
			
			//int nEntities = inputOpt.nextInt();
			int nEntities = 2;
			System.out.println("\n");
			
			switch (nEntities) {
				case 1:
					//Usuários
					datasUsers(option, managementUser, inputOpt);
					break;
				case 2:
					//Fornecedor
					datasProviders(option, managementProvider, inputOpt);
					break;
				/*case 3:
				 * Itens do cardápio
					menuSimulaItem(opcao, gerenCard);
					break;
				case 4:
					Produtos
					menuSimulaProduto(opcao, gerenProd);
					break;
				case 5:
					Vendas
					menuSimulaVenda(option, managementUser);
					break;*/
			}
			System.out.println("\n");
			} else if (option == 5) {
				System.out.println("\nGerando relatório\n");
				generatePDF();
			} else if (option != 6) {
				System.out.println("Opção inválida. Tente de novo.");
			}
			
		} while (option != 6);
		System.out.println("Deslogando do sistema.");
		}
	
	//SETTADO PRA TESTES
	public static void login(Scanner input) {
		String nick, password;
		boolean option = false;
		
		do {
		System.out.println("LOGIN");
		System.out.println("Insira seu nickname: ");
		//nick = input.nextLine();
		nick = "login";
		System.out.println("Insira sua senha: ");
		//password = input.nextLine();
		password = "senha";
		
		try {
			managementUser.checkLogin(nick, password);
			String idUser = managementUser.searchEntitiesNick(nick).getId();
			managementUser.setIdUserOn(idUser);
			option = true;
		} catch(LoginDoesntMatch eLog) {
			System.out.println(eLog.getMessage());
		}
		
		} while(!option);
	}
	
	public static void datasUsers(int operation, ManagementUsers managUser, Scanner inputUsers) {
		
		switch(operation) {
		case 1:
			// Recebe os dados do usuário e faz o cadastro a partir deles.
			String nick, password, name, category;
			int choice;
			
			System.out.println("Insira o nickname a ser registrado: ");
			nick = inputUsers.next();
			System.out.println("Insira a senha a ser registrado: ");
			//password = inputUsers.next();
			password = "senha";
			System.out.println("Insira o seu nome: ");
			//name = inputUsers.next();
			name = "nome";
		
			System.out.println("Qual o cargo do usuário?");
			System.out.println("1 - P/ Gerente\n"
							 + "2 - P/ Funcionário\n");
			//choice = inputUsers.nextInt();
			choice = 1;
			while (choice != 1 && choice != 2) {
				System.out.println("Resposta inválida. Tente novamente:");
				System.out.println("1 - P/ Gerente\n"
						 		 + "2 - P/ Funcionario\n");
				choice = inputUsers.nextInt();
			}
			
			category = (choice == 1)? "Gerente": "Funcionario";
			System.out.println("\n");
			
			try {
			managementUser.register(nick, password, name, category);
			System.out.println("Usuário cadastrado com sucesso!");
			} catch(ExistentNicknameException eNick) {
				System.out.println(eNick.getMessage());
			}
			break;
			
		case 2:
			// Edição dos dados
			String id, option2 = null, newData = null;
			int option1;
			
			//Mostrar todos para o usuário escolher
			managUser.list();
			
			System.out.println("Insira o ID do usuário que deseja alterar: ");
			id = inputUsers.next();
			if (!idExist(id)) {
				System.out.println("ID não econtrado! Tente novamente");
				break;
			};
			
			String[] options = { "nome", "nickname", "senha", "cargo"};
			System.out.println("\nO que você deseja alterar?");
			
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			option1 = inputUsers.nextInt();
			
			while (!(option1 >= 1 && option1 <= options.length)) {
				System.out.println("Opção inválida. Tente novamente.");
				System.out.println("Escolha: ");
				option1 = inputUsers.nextInt();
			}
			
			option2 = options[option1 - 1];
			
			if (option2 == "cargo") {
				System.out.println("Qual o novo cargo do usuário?");
				System.out.println("1 - P/ Gerente\n"
								 + "2 - P/ Funcionario\n");
				choice = inputUsers.nextInt();
				
				while (choice != 1 && choice != 2) {
					System.out.println("Resposta inválida. Tente novamente:");
					System.out.println("1 - P/ Gerente\n"
							 		 + "2 - P/ Funcionario\n");
					choice = inputUsers.nextInt();
				}
				category = (choice == 1)? "Gerente": "Funcionario";
				System.out.println("\n");
				newData = category;
			} else {
				System.out.println("Insira o atributo novo: ");
				newData = inputUsers.next();
			}

			try {
				managUser.edit(id, option2, newData);
			System.out.println("Atributo alterado com sucesso.");
			} catch(ExistentNicknameException eNick) {
				System.out.println(eNick.getMessage());
			}
			break;
			
		case 3: 
			// Deletar usuário
			int nUsers = managUser.getList().size();
			if (nUsers <= 1) {
				System.out.println("Você não pode excluir o único usuário cadastrado.");
			} else {
				managUser.list();
				
				System.out.println("Insira o ID do usuário que deseja deletar: ");
				id = inputUsers.next();
				if (!idExist(id)) {
					System.out.println("ID não econtrado! Tente novamente");
					break;
				};
				
				String idUser = managUser.getIdUserOn();
				if (idUser.equals(id)) {
					System.out.println("Você não pode excluir seu próprio usuário. Por favor, peça para outro usuário excluí-lo.");
				} else {
					managUser.delete(id);
					System.out.println("Usuário deletado com sucesso!");
				}
			}
			break;
		case 4:
			managUser.list();
			break;
		}
	}
	
	public static void datasProviders(int operation, ManagementProviders managProv, Scanner inputProv) {
		String name, cnpj, address, option2, newData, id;
		int option1;
		
		if (operation != 1 && managProv.getList().size() == 0) {
			System.out.println("Não há fornecedores cadastrados!");
		} else {
		
			switch(operation) {
			case 1:
				
				System.out.println("Insira o nome a ser registrado: ");
				inputProv.nextLine();
				name = inputProv.nextLine();
				System.out.println("Insira o CNPJ a ser registrada: ");
				cnpj = inputProv.nextLine();
				System.out.println("Insira o endereço a ser registrado: ");
				address = inputProv.nextLine();
				
				managProv.register(name, cnpj, address);
				System.out.println("Fornecedor cadastrado com sucesso.");
				System.out.println("AVISO: É necessário cadastrar os produtos fornecidos por esse fornecedor!!");
				break;
			case 2:
				managProv.list();
				
				System.out.println("Insira o ID do fornecedor que deseja alterar: ");
				id = inputProv.next();
	
				if (!idExist(id)) {
					System.out.println("ID não econtrado! Tente novamente");
					break;
				};
				
				String[] options = { "nome", "endereço", "cnpj"};
				System.out.println("\nO que você deseja alterar?");
				System.out.println("OBS: Para adicionar/remover produtos de um fornecedor, basta fazê-lo no menu do próprio produto");
				
				for (int i = 0; i < options.length; i++) {
					System.out.println((i + 1) + " - P/ " + options[i]);
				}
				option1 = inputProv.nextInt();
				
				while (!(option1 >= 1 && option1 <= options.length)) {
					System.out.println("Opção inválida. Tente novamente.");
					System.out.println("Escolha: ");
					option1 = inputProv.nextInt();
				}
				
				option2 = options[option1 - 1];
				
	
				System.out.println("Insira o atributo novo: ");
				newData = inputProv.next();
				
				managProv.edit(id, option2, newData);
				System.out.println("Atributo do fornecedor alterado com sucesso.");
				break;
			case 3:
				managProv.list();
				
				System.out.println("Insira o ID do fornecedor que deseja deletar: ");
				id = inputProv.next();
				
				if (!idExist(id)) {
					System.out.println("ID não econtrado! Tente novamente");
					break;
				};
				managProv.delete(id);
				System.out.println("Fornecedor deletado com sucesso.");
				break;
			case 4:
				managProv.list();
				break;
			}
		}
	}
	
	public static void generatePDF() {
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("documents.pdf"));
            document.open();

            // adicionando um parÃ¡grafo no documento
            Paragraph p = new Paragraph("RelatÃ³rio");
            p.setAlignment(1);
            document.add(p);
            p = new Paragraph(" ");
            document.add(p);
            p = new Paragraph("Vendas:");
            document.add(p);
            p = new Paragraph(" ");
            document.add(p);
            p = new Paragraph("Estoque:");
            document.add(p);
            p = new Paragraph(" ");
            document.add(p);
            p = new Paragraph("Fornecedores:");
            document.add(p);
        
            document.close();
            Desktop.getDesktop().open(new File("documents.pdf"));
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
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
}