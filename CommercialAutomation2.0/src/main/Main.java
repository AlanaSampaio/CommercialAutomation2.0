package main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import management_models.*;
import modeling_models.*;


import exceptions.*;

public class Main {
	private static ArrayList<String> ids = new ArrayList<String>();
	
	static ManagementUsers managementUser = new ManagementUsers();
	static ManagementProviders managementProvider = new ManagementProviders();
	static ManagementProducts managementProducts = new ManagementProducts();
	static ManagementMenu managementMenu = new ManagementMenu();
	static ManagementSales managementSales = new ManagementSales();
	
	/**
	 * Menu inicial do sistema. Leva o usuario para as opcoes possiveis, ou encerra o sistema.
	 * @param args
	 * @throws EntitiesNotRegistred 
	 * @throws IdDoesntExist 
	 */
	public static void main(String args[]) throws IdDoesntExist, EntitiesNotRegistred {
		Scanner input = new Scanner(System.in);

		int answer = 1;
		while (answer != 2) {
			System.out.println("AUTOMACAO COMERCIAL \n1 - Logar no sistema \n2 - Sair do sistema");
			answer = input.nextInt();
			input.nextLine();
			
			if (answer == 1) {
				if (managementUser.checkSizeList() == false) {
					System.out.println("Sistema nao possui nenhum registro, faca o primeiro cadastro no sistema!");
					usersMainMenu(1, managementUser, input);
				}
				
				login(input); // So passa para a proxima linha se o login for efetuado.
				optionsMenu(input); // Menu com as opï¿½pes principais.
				
			} else if (answer == 2) {
				System.out.println("SISTEMA ENCERRADO");
			} else {
				System.out.println("Opcao invalida. Tente novamente.");
			}
		}
		input.close();
	}
	
	/**
	 * Menu com as principais opcoes de operacoes.
	 * @param inputOpt: Objeto scanner para receber entradas do usuario.
	 * @throws EntitiesNotRegistred 
	 * @throws IdDoesntExist 
	 */
	public static void optionsMenu(Scanner inputOpt) throws IdDoesntExist, EntitiesNotRegistred {
		String[] options = { "cadastrar", "editar", "excluir", "listar", "gerar relatorio", "encerrar"};
		String[] entities = {"usuario", "fornecedor", "produto", "item", "venda"};
		int option, nEntities;
		
		
		do {
			option = optionMenuGenerator(options, "O que voce deseja fazer?", inputOpt);
			
			if  (option >= 1 && option <= 4) {
				nEntities = optionMenuGenerator(entities, "O que voce deseja " + options[option - 1] + "?", inputOpt);
				switch (nEntities) {
					case 1:
						//Usuarios
						usersMainMenu(option, managementUser, inputOpt);
						break;
					case 2:
						//Fornecedores
						providersMainMenu(option, managementProvider, inputOpt);
						break;
					case 3:
						//Produtos
						productsMainMenu(option, managementProducts, inputOpt);
						break;
					case 4:
						//Itens
						itemsMainMenu(option, managementMenu, inputOpt);
						break;
					case 5:
						//Vendas
						salesMainMenu(option, managementSales, inputOpt);
						break;
				
					} 
			} else if (option == 5) {
				reportsMenu(inputOpt);
			} 
		} while (option != 6);
		
		System.out.println("Deslogando do sistema.");
		
	}
	
	public static void reportsMenu(Scanner input) throws IdDoesntExist, EntitiesNotRegistred {
		ReportsSale rSale = new ReportsSale();
		ReportsProvider rProvider = new ReportsProvider();
		ReportsStock rStock = new ReportsStock();
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		
		String[] options = {"estoque", "vendas", "fornecedor"};
		int opt = optionMenuGenerator(options, "Deseja ver o relatório de:", input);
	
		if (opt == 1) {
			managementProducts.list(false);
			System.out.println("Insira o ID do produto que deseja ver no relatorio: ");
			String idProd = input.next();
			
			rStock.generatePDF(managementProducts, idProd);
		} else if (opt == 2) {
		
			System.out.println("Insira a data de INICIO do periodo de vendas que deseja ver no relatorio: ");
			String opt1 = input.next();
			LocalDate date1 = LocalDate.parse(opt1, dateFormatter);
			System.out.println("Insira a data e do FIM do periodo de vendas que deseja ver no relatorio: ");
			String opt2 = input.next();
			LocalDate date2 = LocalDate.parse(opt2, dateFormatter);
		
			System.out.println("Insira o ID do prato que deseja ver no relatorio: ");
			String idPlate = input.next();
		
			rSale.generatePDF(managementSales, managementMenu, date1, date2, idPlate);
		} else if (opt == 3) {
			rProvider.generatePDF(managementProvider, managementProducts);
		}

		System.out.println("\nGerando relatorio\n");
		}

	public static int optionMenuGenerator(String[] options, String msg1, Scanner input) {
		boolean optChoosed;
		int maxOpt = options.length;
		int optChoice = 0;
		
		//Mostrar opcoes de operacoes
		do {
			try {
				optChoice = 0;
				System.out.println("\n" + msg1);
				
				for (int i = 0; i < options.length; i++) {
					System.out.println((i + 1) + " - P/ " + options[i]);
				}
				
				//Opcao escolhida
				optChoice = input.nextInt();
				input.nextLine();
			}catch(InputMismatchException e) {
				input.next();
				System.out.println("Isso nao e um numero");
			}
			
			optChoosed = (optChoice >= 1 && optChoice <= maxOpt);
			
			if (!optChoosed) {
				System.out.println("Opcao invalida. Tente novamente");
			}
		} while(!optChoosed);
		
		return optChoice;
	}
	
	
	/**
	 * Recebe as informacoes de login do usuario e certifica
	 * que estejam certas.
	 * @param input: Objeto scanner para receber entradas do usuario.
	 */
	public static void login(Scanner input) {
		String nick, password;
		boolean option = false;
		
		do {
		System.out.println("LOGIN");
		System.out.println("Insira seu nickname: ");
		nick = input.nextLine();
		
		System.out.println("Insira sua senha: ");
		password = input.nextLine();
		
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
	
	public static void usersMainMenu(int operation, ManagementUsers managUser, Scanner inputUsers) {
		try {
			switch(operation) {
				case 1:
					usersRegistMenu(operation, managUser, inputUsers);
					break;
					
				case 2:		
					usersEditMenu(operation, managUser, inputUsers);
					break;
					
				case 3:
					usersDelMenu(operation, managUser, inputUsers);
					break;
					
				case 4:
					managUser.list(true);
					break;
			}
		} catch (EntitiesNotRegistred e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void usersRegistMenu(int operation, ManagementUsers managUser, Scanner inputUsers) {
		// Recebe os dados do usuario e faz o cadastro a partir deles.
		String nick, password, name, category;
		int choice;
		
		System.out.println("Insira o nickname a ser registrado: ");
		nick = inputUsers.nextLine();
		
		System.out.println("Insira a senha a ser registrado: ");
		password = inputUsers.nextLine();
		
		System.out.println("Insira o seu nome: ");
		name = inputUsers.nextLine();
		
		String[] roles = {"Gerente", "Funcionario"};
		choice = optionMenuGenerator(roles, "Qual o cargo do usuario?", inputUsers);
		
		category = (choice == 1)? "Gerente": "Funcionario";
		System.out.println("\n");
		
		try {
			managementUser.register(nick, password, name, category);
			System.out.println("Usuario cadastrado com sucesso!");
		} catch(ExistentNicknameException eNick) {
			System.out.println(eNick.getMessage());
		}
	}
	
	public static void usersEditMenu(int operation, ManagementUsers managUser, Scanner inputUsers) throws EntitiesNotRegistred {
		// Edicao dos dados dos usuarios.
		int option1, choice;
		String id, option2, newData, category;
		
		managUser.list(true);
		
		System.out.println("Insira o ID do usuario que deseja alterar: ");
		id = inputUsers.nextLine();
		
		String[] options = { "nome", "nickname", "senha", "cargo"};

		option1 = optionMenuGenerator(options, "O que voce deseja alterar?", inputUsers);
		
		option2 = options[option1 - 1];
		
		if (option2 == "cargo") {
			String[] roles = {"Gerente", "Funcionario"};
			choice = optionMenuGenerator(roles, "Qual o cargo do usuario?", inputUsers);
			
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
		} catch(IdDoesntExist eId) {
			System.out.println(eId.getMessage());
		}
	}
	
	public static void usersDelMenu(int operation, ManagementUsers managUser, Scanner inputUsers) throws EntitiesNotRegistred {
		// Deletar usuario
		String id;
		int nUsers = managUser.getList().size();
		
		if (nUsers <= 1) {
			System.out.println("Voce nao pode excluir o unico usuario cadastrado.");
		} else {
			managUser.list(true);
			
			System.out.println("Insira o ID do usuario que deseja deletar: ");
			id = inputUsers.next();
			
			String idUser = managUser.getIdUserOn();
			if (idUser.equals(id)) {
				System.out.println("Voce nao pode excluir seu proprio usuario. Por favor, peca para outro usuario exclui-lo.");
			} else {
				try {
					managUser.delete(id);
				} catch (IdDoesntExist e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Usuario deletado com sucesso!");
			}
		}
	}
	
	public static void providersMainMenu(int operation, ManagementProviders managProv, Scanner inputProv) {
		try {
			switch(operation) {
				case 1:
					providersRegistMenu(operation, managProv, inputProv);
					break;
				case 2:
					providersEditMenu(operation, managProv, inputProv);
					break;
				case 3:
					providersDelMenu(operation, managProv, inputProv);
					break;
				case 4:
					managProv.list(true);
					break;
			}
		} catch (EntitiesNotRegistred e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void providersRegistMenu(int operation, ManagementProviders managProv, Scanner inputProv) {
		String name, cnpj, address;
		
		if (operation != 1 && managProv.getList().size() == 0) {
			System.out.println("Nao ha fornecedores cadastrados!");
		} else {
			System.out.println("Insira o nome a ser registrado: ");
			name = inputProv.nextLine();
			
			System.out.println("Insira o CNPJ a ser registrada: ");
			cnpj = inputProv.nextLine();
			
			System.out.println("Insira o endereco a ser registrado: ");
			address = inputProv.nextLine();
			
			managProv.register(name, cnpj, address);
			System.out.println("Fornecedor cadastrado com sucesso.");
			System.out.println("AVISO: E necessario cadastrar os produtos fornecidos por esse fornecedor!!");
		}
	}
	
	public static void providersEditMenu(int operation, ManagementProviders managProv, Scanner inputProv) throws EntitiesNotRegistred {
		int option1;
		String option2, newData, id;
		
		
		managProv.list(true);
		
		System.out.println("Insira o ID do fornecedor que deseja alterar: ");
		id = inputProv.next();

		String[] options = { "nome", "endereco", "cnpj"};
		System.out.println("\nO que voce deseja alterar?");
		System.out.println("OBS: Para adicionar/remover produtos de um fornecedor, basta faze-lo no menu do proprio produto");
		
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + " - P/ " + options[i]);
		}
		option1 = inputProv.nextInt();
		
		while (!(option1 >= 1 && option1 <= options.length)) {
			System.out.println("Opcao invalida. Tente novamente.");
			System.out.println("Escolha: ");
			option1 = inputProv.nextInt();
		}
		
		option2 = options[option1 - 1];
		

		System.out.println("Insira o atributo novo: ");
		newData = inputProv.next();
		
		try {
			managProv.edit(id, option2, newData);
			System.out.println("Atributo do fornecedor alterado com sucesso.");
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void providersDelMenu(int operation, ManagementProviders managProv, Scanner inputProv) throws EntitiesNotRegistred {
		String id;
		managProv.list(true);
		
		System.out.println("Insira o ID do fornecedor que deseja deletar: ");
		id = inputProv.next();
		
		try {
			managProv.delete(id);
			System.out.println("Fornecedor deletado com sucesso.");
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void productsMainMenu(int operation, ManagementProducts managProd, Scanner inputProd) {
		try {
			switch(operation) {
				case 1:
					productsRegistMenu(operation, managProd, inputProd);
					break;
				case 2:
					productsEditMenu(operation, managProd, inputProd);
					break;
				case 3:
					productsDelMenu(operation, managProd, inputProd);
					break;
				case 4:
					managProd.list(true);
					break;
			}
		} catch (EntitiesNotRegistred e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void productsRegistMenu(int operation, ManagementProducts managProd, Scanner inputProd) throws EntitiesNotRegistred {
		String name, price, date;
		LocalDate validity;
		BigDecimal priceDecimal;
		int quantity;
		
		System.out.println("Insira o nome do produto: ");
		name = inputProd.nextLine();
		
		try {
			System.out.println("Insira o preco do produto (Use '.' no lugar de ','): ");
			price = inputProd.nextLine();
			priceDecimal = new BigDecimal(price);
			
			System.out.println("Insira a quantidade de produtos comprados: ");
			quantity = inputProd.nextInt();
			inputProd.nextLine();
			
			System.out.println("Insira a validade do produto: ");
			date = inputProd.nextLine();
		    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
		    		.withResolverStyle(ResolverStyle.STRICT);
		    
		    validity = LocalDate.parse(date, dateTimeFormatter);
			
			System.out.println("Lista dos fornecedores cadastrados: ");
			managementProvider.list(false);
			
			System.out.println("Digite o id do fornecedor desse produto: ");
			String idProv = inputProd.next();
			
			Providers prodProv = (Providers) managementProvider.searchEntities(idProv);
			String idNewProd = managProd.register(name, priceDecimal, validity, quantity, prodProv);
			prodProv.addProduct((Products) managProd.searchEntities(idNewProd));
			System.out.println("Produto cadastrado com sucesso.");

		} catch(NumberFormatException eNum) {
			System.out.println("Preco em formato invalido! Tente novamente.");
		} catch(DateTimeParseException eDate) {
			System.out.println("Data invalida! Tente novamente.");
		} catch(InputMismatchException eQnt) {
			System.out.println("Quantidade invalida! Tente novamente.");
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void productsEditMenu(int operation, ManagementProducts managProd, Scanner inputProd) throws EntitiesNotRegistred {
		String price, date, option2, id;
		Object newDataProd;
		int option1;
		
		managProd.list(true);
		
		System.out.println("Insira o ID do produto que deseja alterar: ");
		id = inputProd.next();
		
		String[] options = { "nome", "preco", "validade", "quantidade", "fornecedor"};
		System.out.println("\nO que voce deseja alterar?");
		System.out.println("OBS: Para remover o fornecedor de um produto, basta deletar aquele.");
		
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + " - P/ " + options[i]);
		}
		option1 = inputProd.nextInt();
		
		while (!(option1 >= 1 && option1 <= options.length)) {
			System.out.println("Opcao invalida. Tente novamente.");
			System.out.println("Escolha: ");
			option1 = inputProd.nextInt();
		}
		inputProd.nextLine();
		
		option2 = options[option1 - 1];
		
		
		try {
			switch(option2) {
				case "nome":
					System.out.println("Digite o novo nome:");
					newDataProd = inputProd.nextLine();
					break;
				case "preco":
					System.out.println("Insira o novo preco do produto (Use '.' no lugar de ','): ");
					price = inputProd.nextLine();
					newDataProd = new BigDecimal(price);
					break;
				case "validade":
					System.out.println("Insira a nova validade do produto: ");
					date = inputProd.nextLine();
					DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
							.withResolverStyle(ResolverStyle.STRICT);
			    
					newDataProd = LocalDate.parse(date, dateTimeFormatter);
					break;
				case "quantidade":
					System.out.println("Insira a nova quantidade de produtos: ");
					newDataProd = inputProd.nextInt();
					break;
				case "fornecedor":
					System.out.println("Lista dos fornecedores cadastrados: ");
					managementProvider.list(false);
				
					System.out.println("Digite o id do novo fornecedor desse produto: ");
					String idForn = inputProd.next();

					newDataProd = (Providers) managementProvider.searchEntities(idForn);
					break;
				default:
					newDataProd = "";
			}
				managProd.edit(id, option2, newDataProd);
				System.out.println("Atributo do produto alterado com sucesso.");
			}catch(NumberFormatException eNum) {
			System.out.println("Formato invalido! Tente novamente.");
			} catch(DateTimeParseException eDate) {
			System.out.println("Data invalida! Tente novamente.");
			} catch(InputMismatchException eQnt) {
				System.out.println("Quantidade invalida! Tente novamente.");
			} catch (IdDoesntExist e) {
				System.out.println(e.getMessage());
			}
	}
	
	public static void productsDelMenu(int operation, ManagementProducts managProd, Scanner inputProd) throws EntitiesNotRegistred {
		String id;
		managProd.list(true);
		
		System.out.println("Insira o ID do produto que deseja deletar: ");
		id = inputProd.next();
		
		if (!idExist(id) || (id.charAt(0) != 'P')) {
			System.out.println("ID nao econtrado! Tente novamente");
		};
		String idForn;
		try {
			idForn = ((Products) managProd.searchEntities(id)).getProvider().getId();
			Providers prov = (Providers) managementProvider.searchEntities(idForn);
			prov.removeProduct(id);
			managProd.delete(id);
			System.out.println("Produto deletado com sucesso.");
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void itemsMainMenu(int operation, ManagementMenu managMenu, Scanner inputItem) {
		try {
			switch(operation) {
				case 1:
					itemsRegistMenu(operation, managMenu, inputItem);
					break;
				case 2:
					itemsEditMenu(operation, managMenu, inputItem);
					break;
				case 3:
					itemsDelMenu(operation, managMenu, inputItem);
					break;
				case 4:
					managMenu.list(true);
					break;
			}
		} catch (EntitiesNotRegistred e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void itemsRegistMenu(int operation, ManagementMenu managMenu, Scanner inputItem) throws EntitiesNotRegistred {
		String name, description, price, category;
		int quantity;
		BigDecimal priceDecimal;
		
		System.out.println("Insira o nome do item: ");
		name = inputItem.nextLine();
		System.out.println("Insira a descricao do item: ");
		description = inputItem.nextLine();
		System.out.println("Insira a categoria do item: ");
		category = inputItem.nextLine();
		try {
			System.out.println("Insira o preco do item (Use '.' no lugar de ','): ");
			price = inputItem.nextLine();
			priceDecimal = new BigDecimal(price);
			
			String idNewItem = managMenu.register(name, description, priceDecimal, category, new HashMap<String, Integer>());
			Items newItem = (Items) managMenu.searchEntities(idNewItem);
			
			System.out.println("Lista dos produtos cadastrados: ");
			managementProducts.list(false);
			
			System.out.println("ADICAO DOS PRODUTOS QUE COMPOEM ESSE ITEM:");
			System.out.println("Digite o id do primeiro produto usado neste item: ");
			System.out.println("OBS: Digite 0 para parar de inserir produtos.");
			String idProd = inputItem.nextLine();
			
			do {
				if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
					System.out.println("ID nao econtrado! Tente novamente");
				} else{
					System.out.println("Insira a quantidade desse produto usada na preparacao do item (Em unidades): ");
					quantity = inputItem.nextInt();
					inputItem.nextLine();
					Products prodAdd = (Products) managementProducts.searchEntities(idProd);
					newItem.addProduct(quantity, prodAdd.getName());
				};
				System.out.println("Digite o id do proximo produto usado neste item: ");
				idProd = inputItem.next();
			} while(!(idProd.equals("0")));
			
			if (newItem.getComposition().size() == 0) {
				System.out.println("Itens nao podem estar vazios! Tente novamente!");
				managMenu.delete(idNewItem);
			}
			
			System.out.println("Item cadastrado com sucesso.");

		} catch(NumberFormatException eNum) {
			System.out.println("Formato invalido! Tente novamente.");
		} catch(InputMismatchException eInp) {
			System.out.println("Formato invalido! Tente novamente.");
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void itemsEditMenu(int operation, ManagementMenu managMenu, Scanner inputItem) throws EntitiesNotRegistred {
		String price, option2, id;
		int quantity;
		int option1;
		Object newDataItem;
		
		managMenu.list(true);
		
		System.out.println("Insira o ID do item que deseja alterar: ");
		id = inputItem.next();
		
		String[] options = { "nome", "descricao", "categoria", "preco", "composicao"};
		System.out.println("\nO que voce deseja alterar?");
		
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + " - P/ " + options[i]);
		}
		option1 = inputItem.nextInt();
		
		while (!(option1 >= 1 && option1 <= options.length)) {
			System.out.println("Opcao invalida. Tente novamente.");
			System.out.println("Escolha: ");
			option1 = inputItem.nextInt();
		}
		inputItem.nextLine();
		
		try {
		
			Items item = (Items) managMenu.searchEntities(id);
			option2 = options[option1 - 1];
		
			switch(option2) {
				case "nome":
					
				case "descricao":
					
				case "categoria":
					System.out.println("Digite " + option2 + ": ");
					newDataItem = inputItem.nextLine();
					managMenu.edit(id, option2, newDataItem);
					System.out.println("Atributo do produto alterado com sucesso.");
					break;
					
				case "preco":
					System.out.println("Insira o novo preco do produto (Use '.' no lugar de ','): ");
					price = inputItem.nextLine();
					newDataItem = new BigDecimal(price);
					managMenu.edit(id, option2, newDataItem);
					System.out.println("Atributo do produto alterado com sucesso.");
					break;


				case "composicao":
					String[] options2 = { "adicionar", "remover"};
					System.out.println("\nO que voce deseja fazer?");
					
					for (int i = 0; i < options2.length; i++) {
						System.out.println((i + 1) + " - P/ " + options2[i] + "produto");
					}
					option1 = inputItem.nextInt();
					inputItem.nextLine();
					
					while (!(option1 >= 1 && option1 <= options.length)) {
						System.out.println("Opcao invalida. Tente novamente.");
						System.out.println("Escolha: ");
						option1 = inputItem.nextInt();
					}
					
					if (option1 == 1) {
						System.out.println("ADICAO DE PRODUTOS:");
						System.out.println("Lista dos produtos cadastrados: ");
						managementProducts.list(false);
						System.out.println("Digite o id do primeiro produto: ");
						System.out.println("OBS: Digite 0 para parar de inserir produtos.");
						String idProd = inputItem.next();
						
						do {
							if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
								System.out.println("ID nao econtrado! Tente novamente");
							} else{
								System.out.println("Insira a quantidade desse produto usada na preparacao do item (Em unidades): ");
								quantity = inputItem.nextInt();
								Products prodAdd = (Products) managementProducts.searchEntities(idProd);
								managMenu.addProductsItems(id, prodAdd, quantity);
							};
							System.out.println("Produto adicionado com sucesso!");
							System.out.println("Digite o id do proximo produto: ");
							idProd = inputItem.next();
						} while(idProd != "0");
					} else {
						System.out.println("REMOCAO DE PRODUTOS:");
						System.out.println("Lista dos produtos cadastrados: ");
						managementProducts.list(false);
						System.out.println("Digite o id do primeiro produto a ser removido: ");
						System.out.println("OBS: Digite 0 para parar de remover produtos.");
						String idProd = inputItem.next();
						
						do {
							Products prodRem = (Products) managMenu.searchEntities(idProd);
							managMenu.removeProductFromItem(id, prodRem);

							System.out.println("Produto removido com sucesso!");
							if(item.getComposition().size() == 0) {
								System.out.println("O item foi deletado!");
								idProd = "0";
							} else {
								if (item.getComposition().size() == 1) {
									System.out.println("Remover o ultimo produto deletara o item.");
								}
								System.out.println("Digite o id do proximo produto: ");
								idProd = inputItem.next();	
							}
						} while(idProd != "0");
					}
					break;
				default:
					newDataItem = "";
				}
			}catch(NumberFormatException eNum) {
				System.out.println("Formato invalido! Tente novamente.");
			} catch(DateTimeParseException eDate) {
				System.out.println("Data invalida! Tente novamente.");
			} catch (IdDoesntExist e) {
				System.out.println(e.getMessage());
			}
	}
	
	public static void itemsDelMenu(int operation, ManagementMenu managMenu, Scanner inputItem) throws EntitiesNotRegistred {
		String id;
		
		managMenu.list(true);
		
		System.out.println("Insira o ID do item que deseja deletar: ");
		id = inputItem.next();
		
		try {
			managMenu.delete(id);
			System.out.println("Item deletado com sucesso.");
		} catch (IdDoesntExist e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void salesMainMenu(int operation, ManagementSales managSales, Scanner inputSale) {
		try {
			switch(operation) {
				case 1:
					salesRegistMenu(operation, managSales, inputSale);
					break;
				case 2:
					salesEditMenu(operation, managSales, inputSale);
					break;
				case 3:
					salesDelMenu(operation, managSales, inputSale);
					break;
				case 4:
					managSales.list(true);
					break;
			}
		} catch (EntitiesNotRegistred e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void salesRegistMenu(int operation, ManagementSales managSales, Scanner inputSale) throws EntitiesNotRegistred {
		String dateStr, timeStr, paymentMethod, idItem;
		LocalDate date;
		LocalTime time;
		
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
	    		.withResolverStyle(ResolverStyle.STRICT); 
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		
	    
		System.out.println("Insira o metodo de pagamento da venda: ");
		paymentMethod = inputSale.nextLine();
		
		try {
			System.out.println("Insira o dia que a venda foi realizada: ");
			dateStr = inputSale.nextLine();
			date = LocalDate.parse(dateStr, dateFormatter);
			
			System.out.println("Insira a hora que a venda foi realizada: ");
			timeStr = inputSale.nextLine();
			time = LocalTime.parse(timeStr, timeFormatter);
			
			ArrayList<Items> itemsPurchased = new ArrayList<Items>();

		
			System.out.println("Lista dos itens cadastrados: ");
			managementMenu.list(false);
		
			System.out.println("ADICAO DOS ITENS QUE COMPOEM ESTA VENDA:");
			System.out.println("OBS: Digite 0 para parar de inserir itens.");

			
			do {
				System.out.println("Digite o id do item comprado: ");
				idItem = inputSale.nextLine();
				
				if(!(idItem.equals("0"))) {
					Items itemAdd = (Items) managementMenu.searchEntities(idItem);
					managementProducts.checkAllProductsEnough(itemAdd.getComposition());
					
					itemsPurchased.add(itemAdd);
				}
			} while(!(idItem.equals("0")));
			
			if (itemsPurchased.size() == 0) {
				System.out.println("Vendas nao podem estar vazias! Tente novamente!");
			} else {
				System.out.println("Venda cadastrada com sucesso.");
				String idNewSale = managSales.register(date, time, itemsPurchased, paymentMethod);
				
				Sales newSale = (Sales) managSales.searchEntities(idNewSale);
				
				for(Items item: newSale.getItemsPurchased()) {
					managementProducts.updateStock(item.getComposition());
				}
				System.out.println("Estoque atualizado com sucesso.");
			}
			
		} catch(NumberFormatException eNum) {
			System.out.println("Formato invalido! Tente novamente.");
		} catch(DateTimeParseException eDate) {
			System.out.println("Formato invalido! Tente novamente.");
		} catch (IdDoesntExist eId) {
			System.out.println(eId.getMessage());
		} catch (NotEnoughStock eStc) {
			System.out.println(eStc.getMessage());
		}
	}
	
	public static void salesEditMenu(int operation, ManagementSales managSales, Scanner inputSale) throws EntitiesNotRegistred {
		String dateStr, timeStr, option2, id;
		int option1;
		
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
	    		.withResolverStyle(ResolverStyle.STRICT); 
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		
		managSales.list(true);
		
		System.out.println("Insira o ID da venda que deseja alterar: ");
		id = inputSale.next();

		String[] options = { "dia", "horario", "modoDePagamento", "itens"};
		System.out.println("\nO que voce deseja alterar?");
		
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + " - P/ " + options[i]);
		}
		option1 = inputSale.nextInt();
		inputSale.nextLine();
		
		while (!(option1 >= 1 && option1 <= options.length)) {
			System.out.println("Opcao invalida. Tente novamente.");
			System.out.println("Escolha: ");
			option1 = inputSale.nextInt();
			inputSale.nextLine();
		}
		
		try {
			Sales sale = (Sales) managSales.searchEntities(id);
			option2 = options[option1 - 1];
			Object newDataSale;
		
		
			switch(option2) {
				case "dia":
					System.out.println("Insira o novo dia da venda: ");
					dateStr = inputSale.nextLine();
					newDataSale = LocalDate.parse(dateStr, dateFormatter);
					managSales.edit(id, option2, newDataSale);
					System.out.println("Data da venda alterada com sucesso.");	
					break;
					
				case "horario":
					System.out.println("Insira a nova hora que da venda: ");
					timeStr = inputSale.nextLine();
					newDataSale = LocalTime.parse(timeStr, timeFormatter);
					managSales.edit(id, option2, newDataSale);
					System.out.println("Horario da venda alterado com sucesso.");
					break;
					
				case "modoDePagamento":
					System.out.println("Digite o novo metodo de pagamento desta venda:");
					newDataSale = inputSale.nextLine();
					managSales.edit(id, option2, newDataSale);
					System.out.println("Metodo de pagamento alterado com sucesso.");
					break;

				case "itens":
					String[] options2 = { "adicionar", "remover"};
					System.out.println("\nO que voce deseja fazer?");
					
					for (int i = 0; i < options2.length; i++) {
						System.out.println((i + 1) + " - P/ " + options2[i] + "itens");
					}
					option1 = inputSale.nextInt();
					inputSale.nextLine();
					
					while (!(option1 >= 1 && option1 <= options.length)) {
						System.out.println("Opcao invalida. Tente novamente.");
						System.out.println("Escolha: ");
						option1 = inputSale.nextInt();
						inputSale.nextLine();
					}
					
					if (option1 == 1) {
						System.out.println("ADICAO DE ITENS:");
						System.out.println("Lista dos itens cadastrados: ");
						managementMenu.list(false);
						System.out.println("Digite o id do primeiro item: ");
						System.out.println("OBS: Digite 0 para parar de inserir itens.");
						String idItem = inputSale.next();
						
						do {
							if (!idExist(idItem) || (idItem.charAt(0) != 'I')) {
								System.out.println("ID nao econtrado! Tente novamente");
							} else{
								Items itemAdd = (Items) managementMenu.searchEntities(idItem);
								managSales.addItem(id, itemAdd);
							};
							System.out.println("Item adicionado com sucesso!");
							System.out.println("Digite o id do proximo item: ");
							idItem = inputSale.next();
						} while(idItem != "0");
					} else {
						System.out.println("REMOCAO DE ITENS:");
						System.out.println("Lista dos itens cadastrados: ");
						managementMenu.list(false);
						System.out.println("Digite o id do primeiro item a ser removido: ");
						System.out.println("OBS: Digite 0 para parar de remover itens.");
						String idItem = inputSale.next();
						
						do {
							if (!idExist(idItem) || (idItem.charAt(0) != 'I')) {
								System.out.println("ID nao econtrado! Tente novamente");
							} else{
								Items itemRem = (Items) managSales.searchEntities(idItem);
								managSales.deleteItem(id, itemRem);
							};
							System.out.println("Item removido com sucesso!");
							
							if(sale.getItemsPurchased().size() == 0) {
								System.out.println("O item foi deletado!");
								idItem = "0";
							} else {
								if (sale.getItemsPurchased().size() == 1) {
									System.out.println("Remover o ultimo produto deletara o item.");
								}
								System.out.println("Digite o id do proximo produto: ");
								idItem = inputSale.next();	
							}
						} while(idItem != "0");
					}
					break;
				default:
					newDataSale = "";
				}
			}catch(NumberFormatException eNum) {
			System.out.println("Formato invalido! Tente novamente.");
			} catch(DateTimeParseException eDate) {
			System.out.println("Data invalida! Tente novamente.");
			} catch (IdDoesntExist e) {
				System.out.println(e.getMessage());
			}
	}
	

	public static void salesDelMenu(int operation, ManagementSales managSales, Scanner inputSale) throws EntitiesNotRegistred {
		String id;
		
		managSales.list(true);
		
		System.out.println("Insira o ID da venda que deseja deletar: ");
		id = inputSale.next();
		
		try {
			managSales.delete(id);
		} catch (IdDoesntExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Venda deletada com sucesso.");
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