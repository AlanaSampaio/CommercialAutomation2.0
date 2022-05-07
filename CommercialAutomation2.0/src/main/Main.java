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
	static Reports report = new Reports();
	
	//SETTADO PRA TESTES
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);

		int answer = 1;
		while (answer != 2) {
			System.out.println("AUTOMACAO COMERCIAL \n1 - Logar no sistema \n2 - Sair do sistema");
			//answer = input.nextInt();
			//input.nextLine();
			
			answer = 1;
			
			if (answer == 1) {
				if (managementUser.checkSizeList() == false) {
					System.out.println("Sistema nao possui nenhum registro, faca o primeiro cadastro no sistema!");
					datasUsers(1, managementUser, input);

				}
				login(input);
				optionsMenu(input);
			} else if (answer == 2) {
				System.out.println("SISTEMA ENCERRADO");
				answer = 2;
			} else {
				System.out.println("Opcao invalida.");
			}
		}
		input.close();
	}
	
	public static void optionsMenu(Scanner inputOpt) {
		String[] options = { "cadastrar", "editar", "excluir", "listar", "gerar relatorio", "encerrar"};
		String[] entities = {"usuario", "fornecedor", "produto", "item", "venda"};
		int option = 0;
		
		//Mostrar op��es de opera��es
		do {
			System.out.println("\nO que voce deseja fazer?");
			
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			//Op��o escolhida
			option = inputOpt.nextInt();
			inputOpt.nextLine();
			
			
			if (option >= 1 && option <= 4) {
				System.out.println("O que voce deseja " + options[option - 1] + "?");
				//Mostrar entidades que sao alteraveis
				for (int i = 0; i < entities.length; i++) {
					System.out.println((i + 1) + " - P/ " + entities[i]);
			}
			
				int nEntities = inputOpt.nextInt();
				inputOpt.nextLine();
				//int nEntities = 2;
				System.out.println("\n");
				
				switch (nEntities) {
					case 1:
						//Usuarios
						datasUsers(option, managementUser, inputOpt);
						break;
					case 2:
						//Fornecedor
						datasProviders(option, managementProvider, inputOpt);
						break;
					case 3:
						//Produtos
						datasProducts(option, managementProducts, inputOpt);
						break;
					case 4:
						//Itens
						datasItems(option, managementMenu, inputOpt);
						break;
			
					case 5:
						//Vendas
						datasSales(option, managementSales, inputOpt);
						break;
				}
			System.out.println("\n");
			} else if (option == 5) {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
				    		.withResolverStyle(ResolverStyle.STRICT);
				
				System.out.println("Insira a data de INÍCIO do período de vendas que deseja ver no relatório: ");
				String opt1 = inputOpt.nextLine();
				LocalDate date1 = LocalDate.parse(opt1, dateFormatter);
				System.out.println("Insira a data e do FIM do período de vendas que deseja ver no relatório: ");
				String opt2 = inputOpt.nextLine();
				LocalDate date2 = LocalDate.parse(opt2, dateFormatter);
				
				System.out.println("Insira o ID do prato que deseja ver no relatório: ");
				String idPlate = inputOpt.nextLine();
				
				System.out.println("Insira o ID do fornecedor que deseja ver no relatório: ");
				String idProvider = inputOpt.nextLine();
				
				report.generatePDF(managementSales, date1, date2, idPlate, idProvider);
				
			} else if (option != 6) {
				System.out.println("Opcao invalida. Tente de novo.");
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
			// Recebe os dados do usuario e faz o cadastro a partir deles.
			String nick, password, name, category;
			int choice;
			
			System.out.println("Insira o nickname a ser registrado: ");
			//nick = inputUsers.nextLine();
			
			nick = "login";
			System.out.println("Insira a senha a ser registrado: ");
			//password = inputUsers.nextLine();
			
			password = "senha";
			System.out.println("Insira o seu nome: ");
			//name = inputUsers.nextLine();
			name = "nome";
		
			System.out.println("Qual o cargo do usuario?");
			System.out.println("1 - P/ Gerente\n"
							 + "2 - P/ Funcionario\n");
			//choice = inputUsers.nextInt();
			//inputUsers.nextLine();
			
			choice = 1;
			while (choice != 1 && choice != 2) {
				System.out.println("Resposta invalida. Tente novamente:");
				System.out.println("1 - P/ Gerente\n"
						 		 + "2 - P/ Funcionario\n");
				choice = inputUsers.nextInt();
				inputUsers.nextLine();
			}
			
			category = (choice == 1)? "Gerente": "Funcionario";
			System.out.println("\n");
			
			try {
			managementUser.register(nick, password, name, category);
			System.out.println("Usuario cadastrado com sucesso!");
			} catch(ExistentNicknameException eNick) {
				System.out.println(eNick.getMessage());
			}
			break;
			
		case 2:
			// Edicaoo dos dados
			String id, option2 = null, newData = null;
			int option1;
			
			//Mostrar todos para o usuario escolher
			managUser.list();
			
			System.out.println("Insira o ID do usuario que deseja alterar: ");
			id = inputUsers.nextLine();
			if (!idExist(id) || (id.charAt(0) != 'U')) {
				System.out.println("ID nao econtrado! Tente novamente");
				break;
			};
			
			String[] options = { "nome", "nickname", "senha", "cargo"};
			System.out.println("\nO que voce deseja alterar?");
			
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			option1 = inputUsers.nextInt();
			inputUsers.nextLine();
			
			while (!(option1 >= 1 && option1 <= options.length)) {
				System.out.println("Opcao invalida. Tente novamente.");
				System.out.println("Escolha: ");
				option1 = inputUsers.nextInt();
				inputUsers.nextLine();
			}
			
			option2 = options[option1 - 1];
			
			if (option2 == "cargo") {
				System.out.println("Qual o novo cargo do usuario?");
				System.out.println("1 - P/ Gerente\n"
								 + "2 - P/ Funcionario\n");
				choice = inputUsers.nextInt();
				inputUsers.nextLine();
				
				while (choice != 1 && choice != 2) {
					System.out.println("Resposta invalida. Tente novamente:");
					System.out.println("1 - P/ Gerente\n"
							 		 + "2 - P/ Funcionario\n");
					choice = inputUsers.nextInt();
					inputUsers.nextLine();
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
			// Deletar usu�rio
			int nUsers = managUser.getList().size();
			if (nUsers <= 1) {
				System.out.println("Voce nao pode excluir o unico usuario cadastrado.");
			} else {
				managUser.list();
				
				System.out.println("Insira o ID do usuario que deseja deletar: ");
				id = inputUsers.next();
				if (!idExist(id) || (id.charAt(0) != 'U')) {
					System.out.println("ID nao econtrado! Tente novamente");
					break;
				};
				
				String idUser = managUser.getIdUserOn();
				if (idUser.equals(id)) {
					System.out.println("Voce nao pode excluir seu proprio usuario. Por favor, peca para outro usuario exclui-lo.");
				} else {
					managUser.delete(id);
					System.out.println("Usuario deletado com sucesso!");
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
			System.out.println("Nao ha fornecedores cadastrados!");
		} else {
		
			switch(operation) {
			case 1:
				
				System.out.println("Insira o nome a ser registrado: ");
				//name = inputProv.nextLine();
				name = "forn";
				System.out.println("Insira o CNPJ a ser registrada: ");
				//cnpj = inputProv.nextLine();
				cnpj = "cnpj";
				System.out.println("Insira o endereco a ser registrado: ");
				address = "endereco";
				//address = inputProv.nextLine();
				
				managProv.register(name, cnpj, address);
				System.out.println("Fornecedor cadastrado com sucesso.");
				System.out.println("AVISO: E necessario cadastrar os produtos fornecidos por esse fornecedor!!");
				break;
			case 2:
				managProv.list();
				
				System.out.println("Insira o ID do fornecedor que deseja alterar: ");
				id = inputProv.next();
	
				if (!idExist(id) || (id.charAt(0) != 'F')) {
					System.out.println("ID nao econtrado! Tente novamente");
					break;
				};
				
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
				
				managProv.edit(id, option2, newData);
				System.out.println("Atributo do fornecedor alterado com sucesso.");
				break;
			case 3:
				managProv.list();
				
				System.out.println("Insira o ID do fornecedor que deseja deletar: ");
				id = inputProv.next();
				
				if (!idExist(id) || (id.charAt(0) != 'F')) {
					System.out.println("ID nao econtrado! Tente novamente");
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
	
	public static void datasProducts(int operation, ManagementProducts managProd, Scanner inputProd) {
		String name, price, date, option2, id;
		LocalDate validity;
		BigDecimal priceDecimal;
		int quantity;
		int option1;
		
		if (operation != 1 && managProd.getList().size() == 0) {
			System.out.println("Nao ha produtos cadastrados!");
		} else {
		
			switch(operation) {
			case 1:
				
				System.out.println("Insira o nome do produto: ");
				//name = inputProd.nextLine();
				name = "batata";
				
				try {
					System.out.println("Insira o preco do produto (Use '.' no lugar de ','): ");
					//price = inputProd.nextLine();
					price = "15.10";
					priceDecimal = new BigDecimal(price);
					
					System.out.println("Insira a quantidade de produtos comprados: ");
					//quantity = inputProd.nextInt();
					inputProd.nextLine();
					quantity = 15;
					
					System.out.println("Insira a validade do produto: ");
					//date = inputProd.nextLine();
					date = "03/06/2022";
				    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
				    		.withResolverStyle(ResolverStyle.STRICT);
				    
				    validity = LocalDate.parse(date, dateTimeFormatter);
					
					System.out.println("Lista dos fornecedores cadastrados: ");
					managementProvider.list();
					
					System.out.println("Digite o id do fornecedor desse produto: ");
					String idProv = inputProd.next();
		
					if (!idExist(idProv) || (idProv.charAt(0) != 'F')) {
						System.out.println("ID nao econtrado! Tente novamente");
						break;
					};
					
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
				}
				
				break;
			case 2:
				managProd.list();
				
				System.out.println("Insira o ID do produto que deseja alterar: ");
				id = inputProd.next();
	
				if (!idExist(id) || (id.charAt(0) != 'P')) {
					System.out.println("ID nao econtrado! Tente novamente");
					break;
				};
				
				
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
				Object newDataProd;
				
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
							managementProvider.list();
						
							System.out.println("Digite o id do novo fornecedor desse produto: ");
							String idForn = inputProd.next();
			
							if (!idExist(idForn) || (idForn.charAt(0) != 'F')) {
							System.out.println("ID nao econtrado! Tente novamente");
							};
							newDataProd = (Providers) managementProvider.searchEntities(idForn);
							break;
						default:
							newDataProd = "";
					}
						managProd.edit(id, option2, newDataProd);
						System.out.println("Atributo do produto alterado com sucesso.");
						break;
					}catch(NumberFormatException eNum) {
					System.out.println("Formato invalido! Tente novamente.");
					} catch(DateTimeParseException eDate) {
					System.out.println("Data invalida! Tente novamente.");
					} catch(InputMismatchException eQnt) {
						System.out.println("Quantidade invalida! Tente novamente.");
					}

			case 3:
				managProd.list();
				
				System.out.println("Insira o ID do produto que deseja deletar: ");
				id = inputProd.next();
				
				if (!idExist(id) || (id.charAt(0) != 'P')) {
					System.out.println("ID nao econtrado! Tente novamente");
				};
				String idForn = ((Products) managProd.searchEntities(id)).getProvider().getId();
				Providers prov = (Providers) managementProvider.searchEntities(idForn);
				prov.removeProduct(id);
				managProd.delete(id);
				System.out.println("Produto deletado com sucesso.");
				break;
			case 4:
				managProd.list();
				break;
			}
		}
	}
	
	public static void datasItems(int operation, ManagementMenu managMenu, Scanner inputItem) {
		String name, description, price, category, option2, id;
		int quantity;
		BigDecimal priceDecimal;
		int option1;
		
		if (operation != 1 && managMenu.getList().size() == 0) {
			System.out.println("Nao ha itens cadastrados!");
		} else {
		
			switch(operation) {
				case 1:
					
					System.out.println("Insira o nome do item: ");
					//name = inputItem.nextLine();
					name = "batata";
					System.out.println("Insira a descricao do item: ");
					//description = inputItem.nextLine();
					description = "batatinha";
					System.out.println("Insira a categoria do item: ");
					//category = inputItem.nextLine();
					category = "acompanhamento";
					try {
						System.out.println("Insira o preco do item (Use '.' no lugar de ','): ");
						//price = inputItem.nextLine();
						price = "20.15";
						priceDecimal = new BigDecimal(price);
						
						String idNewItem = managMenu.register(name, description, priceDecimal, category, new HashMap<String, Integer>());
						Items newItem = (Items) managMenu.searchEntities(idNewItem);
						
						System.out.println("Lista dos produtos cadastrados: ");
						managementProducts.list();
						
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
							break;
						}
						
						System.out.println("Item cadastrado com sucesso.");
		
					} catch(NumberFormatException eNum) {
						System.out.println("Formato invalido! Tente novamente.");
					} catch(InputMismatchException eInp) {
						System.out.println("Formato invalido! Tente novamente.");
					}
					break;
					
				case 2:
					managMenu.list();
					
					System.out.println("Insira o ID do item que deseja alterar: ");
					id = inputItem.next();
		
					if (!idExist(id) || (id.charAt(0) != 'I')) {
						System.out.println("ID nao econtrado! Tente novamente");
						break;
					};
					
					
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
					
					Items item = (Items) managMenu.searchEntities(id);
					option2 = options[option1 - 1];
					Object newDataItem;
					
					try {
						switch(option2) {
							case "nome":
								System.out.println("Digite o novo nome:");
								newDataItem = inputItem.nextLine();
								managMenu.edit(id, option2, newDataItem);
								System.out.println("Atributo do produto alterado com sucesso.");
								break;
								
							case "descricao":
								System.out.println("Digite a nova descricao:");
								newDataItem = inputItem.nextLine();
								managMenu.edit(id, option2, newDataItem);
								System.out.println("Atributo do produto alterado com sucesso.");
								break;
								
							case "categoria":
								System.out.println("Digite a nova categoria:");
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
								
								while (!(option1 >= 1 && option1 <= options.length)) {
									System.out.println("Opcao invalida. Tente novamente.");
									System.out.println("Escolha: ");
									option1 = inputItem.nextInt();
								}
								
								if (option1 == 1) {
									System.out.println("ADICAO DE PRODUTOS:");
									System.out.println("Lista dos produtos cadastrados: ");
									managementProducts.list();
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
									managementProducts.list();
									System.out.println("Digite o id do primeiro produto a ser removido: ");
									System.out.println("OBS: Digite 0 para parar de remover produtos.");
									String idProd = inputItem.next();
									
									do {
										if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
											System.out.println("ID nao econtrado! Tente novamente");
										} else{
											Products prodRem = (Products) managMenu.searchEntities(idProd);
											managMenu.removeProductFromItem(id, prodRem);
										};
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
							break;
						}catch(NumberFormatException eNum) {
						System.out.println("Formato invalido! Tente novamente.");
						} catch(DateTimeParseException eDate) {
						System.out.println("Data invalida! Tente novamente.");
						}
					break;
					
				case 3:
					managMenu.list();
					
					System.out.println("Insira o ID do item que deseja deletar: ");
					id = inputItem.next();
					
					if (!idExist(id) || (id.charAt(0) != 'I')) {
						System.out.println("ID nao econtrado! Tente novamente");
					};
					managMenu.delete(id);
					System.out.println("Item deletado com sucesso.");
					break;
				case 4:
					
					managMenu.list();
					break;
					}
			}
	}

	public static void datasSales(int operation, ManagementSales managSales, Scanner inputSale) {
		String dateStr, timeStr, paymentMethod, option2, id;
		LocalDate date;
		LocalTime time;
		int option1;
		
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
	    		.withResolverStyle(ResolverStyle.STRICT); 
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
	    		.withResolverStyle(ResolverStyle.STRICT);
		
		if (operation != 1 && managSales.getList().size() == 0) {
			System.out.println("Nao ha vendas cadastradas!");
		} else {
		
			switch(operation) {
				case 1:
					
					System.out.println("Insira o metodo de pagamento da venda: ");
					paymentMethod = inputSale.nextLine();
					
					try {
						System.out.println("Insira o dia que a venda foi realizada: ");
						dateStr = inputSale.nextLine();
						date = LocalDate.parse(dateStr, dateFormatter);
						
						System.out.println("Insira a hora que a venda foi realizada: ");
						timeStr = inputSale.nextLine();
						time = LocalTime.parse(timeStr, timeFormatter);

						String idNewSale = managSales.register(date, time, new ArrayList<Items>(), paymentMethod);
						Sales newSale = (Sales) managSales.searchEntities(idNewSale);
					
						System.out.println("Lista dos itens cadastrados: ");
						managementMenu.list();
					
						System.out.println("ADICAO DOS ITENS QUE COMPOEM ESTA VENDA:");
						System.out.println("Digite o id do primeiro item comprado: ");
						System.out.println("OBS: Digite 0 para parar de inserir itens.");
						String idItem = inputSale.nextLine();
						
						do {
							if (!idExist(idItem) || (idItem.charAt(0) != 'I')) {
								System.out.println("ID nao econtrado! Tente novamente");
							} else{
								Items itemAdd = (Items) managementMenu.searchEntities(idItem);
								newSale.addItem(itemAdd);
							};
							System.out.println("Digite o id do proximo item comprado: ");
							idItem = inputSale.next();
						} while(!(idItem.equals("0")));
						
						if (newSale.getItemsPurchased().size() == 0) {
							System.out.println("Vendas nao podem estar vazios! Tente novamente!");
							managSales.delete(idNewSale);
							break;
						}
						
						System.out.println("Venda cadastrado com sucesso.");
	
					} catch(NumberFormatException eNum) {
						System.out.println("Formato invalido! Tente novamente.");
					} catch(DateTimeParseException eDate) {
						System.out.println("Formato invalido! Tente novamente.");
					}
					break;
					
				case 2:
					
					managSales.list();
					
					System.out.println("Insira o ID da venda que deseja alterar: ");
					id = inputSale.next();
		
					if (!idExist(id) || (id.charAt(0) != 'V')) {
						System.out.println("ID nao econtrado! Tente novamente");
						break;
					};
					
					
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
					
					
					Sales sale = (Sales) managSales.searchEntities(id);
					option2 = options[option1 - 1];
					Object newDataSale;
					
					try {
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
									System.out.println("ADICAO DE ITENSS:");
									System.out.println("Lista dos itens cadastrados: ");
									managementMenu.list();
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
									managementMenu.list();
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
							break;
						}catch(NumberFormatException eNum) {
						System.out.println("Formato invalido! Tente novamente.");
						} catch(DateTimeParseException eDate) {
						System.out.println("Data invalida! Tente novamente.");
						}
					break;
				case 3:
					managSales.list();
					
					System.out.println("Insira o ID da venda que deseja deletar: ");
					id = inputSale.next();
					
					if (!idExist(id) || (id.charAt(0) != 'V')) {
						System.out.println("ID nao econtrado! Tente novamente");
					};
					managSales.delete(id);
					System.out.println("Venda deletada com sucesso.");
					break;
				case 4:
					managSales.list();
					break;
			}
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