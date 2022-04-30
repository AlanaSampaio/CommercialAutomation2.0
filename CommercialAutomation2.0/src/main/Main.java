package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import management_models.*;
import modeling_models.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.*;

public class Main {
	private static ArrayList<String> ids = new ArrayList<String>();
	
	static ManagementUsers managementUser = new ManagementUsers();
	static ManagementProviders managementProvider = new ManagementProviders();
	static ManagementProducts managementProducts = new ManagementProducts();
	static ManagementMenu managementMenu = new ManagementMenu();
	
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
		String[] entities = {"usuario", "fornecedor", "produto", "item", "venda"};
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
			
			int nEntities = inputOpt.nextInt();
			//int nEntities = 2;
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
				case 3:
					//Produtos
					datasProducts(option, managementProducts, inputOpt);
					break;
				case 4:
					//Itens
					datasItems(option, managementMenu, inputOpt);
					break;
				/*
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
			//nick = inputUsers.next();
			nick = "login";
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
			if (!idExist(id) || (id.charAt(0) != 'U')) {
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
				if (!idExist(id) || (id.charAt(0) != 'U')) {
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
	
				if (!idExist(id) || (id.charAt(0) != 'F')) {
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
				
				if (!idExist(id) || (id.charAt(0) != 'F')) {
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
	
	public static void datasProducts(int operation, ManagementProducts managProd, Scanner inputProd) {
		String name, price, quantity, date, option2, id;
		LocalDate validity;
		BigDecimal priceDecimal, quantityDecimal;
		int option1;
		
		if (operation != 1 && managProd.getList().size() == 0) {
			System.out.println("Não há produtos cadastrados!");
		} else {
		
			switch(operation) {
			case 1:
				
				System.out.println("Insira o nome do produto: ");
				inputProd.nextLine();
				name = inputProd.nextLine();
				
				try {
				System.out.println("Insira o preço do produto (Use '.' no lugar de ','): ");
				price = inputProd.nextLine();
				priceDecimal = new BigDecimal(price);
				
				System.out.println("Insira a quantidade de produtos comprados: ");
				quantity = inputProd.nextLine();
				quantityDecimal = new BigDecimal(quantity);
				
				System.out.println("Insira a validade do produto: ");
				date = inputProd.nextLine();
			    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
			    		.withResolverStyle(ResolverStyle.STRICT);
			    
			    validity = LocalDate.parse(date, dateTimeFormatter);
				
				System.out.println("Lista dos fornecedores cadastrados: ");
				managementProvider.list();
				
				System.out.println("Digite o id do fornecedor desse produto: ");
				String idForn = inputProd.next();
	
				if (!idExist(idForn) || (idForn.charAt(0) != 'F')) {
					System.out.println("ID não econtrado! Tente novamente");
					break;
				};
				
				Providers prodProv = (Providers) managementProvider.searchEntities(idForn);
				String idNewProd = managProd.register(name, priceDecimal, validity, quantityDecimal, prodProv);
				prodProv.addProduct((Products) managProd.searchEntities(idNewProd));
				System.out.println("Produto cadastrado com sucesso.");

				} catch(NumberFormatException eNum) {
					System.out.println("Preço em formato inválido! Tente novamente.");
				} catch(DateTimeParseException eDate) {
					System.out.println("Data inválida! Tente novamente.");
				}
				
				break;
			case 2:
				managProd.list();
				
				System.out.println("Insira o ID do produto que deseja alterar: ");
				id = inputProd.next();
	
				if (!idExist(id) || (id.charAt(0) != 'P')) {
					System.out.println("ID não econtrado! Tente novamente");
					break;
				};
				
				
				String[] options = { "nome", "preco", "validade", "quantidade", "fornecedor"};
				System.out.println("\nO que você deseja alterar?");
				System.out.println("OBS: Para remover o fornecedor de um produto, basta deletar aquele.");
				
				for (int i = 0; i < options.length; i++) {
					System.out.println((i + 1) + " - P/ " + options[i]);
				}
				option1 = inputProd.nextInt();
				
				while (!(option1 >= 1 && option1 <= options.length)) {
					System.out.println("Opção inválida. Tente novamente.");
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
							System.out.println("Insira o novo preço do produto (Use '.' no lugar de ','): ");
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
							quantity = inputProd.nextLine();
							newDataProd = new BigDecimal(quantity);
							break;
						case "fornecedor":
							System.out.println("Lista dos fornecedores cadastrados: ");
							managementProvider.list();
						
							System.out.println("Digite o id do novo fornecedor desse produto: ");
							String idForn = inputProd.next();
			
							if (!idExist(idForn) || (idForn.charAt(0) != 'F')) {
							System.out.println("ID não econtrado! Tente novamente");
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
					System.out.println("Formato inválido! Tente novamente.");
					} catch(DateTimeParseException eDate) {
					System.out.println("Data inválida! Tente novamente.");
					}

			case 3:
				managProd.list();
				
				System.out.println("Insira o ID do produto que deseja deletar: ");
				id = inputProd.next();
				
				if (!idExist(id) || (id.charAt(0) != 'P')) {
					System.out.println("ID não econtrado! Tente novamente");
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
		String name, description, price, quantity, category, option2, id;
		BigDecimal priceDecimal, quantityDecimal;
		int option1;
		
		if (operation != 1 && managMenu.getList().size() == 0) {
			System.out.println("Não há itens cadastrados!");
		} else {
		
			switch(operation) {
				case 1:
					
					System.out.println("Insira o nome do item: ");
					inputItem.nextLine();
					name = inputItem.nextLine();
					
					System.out.println("Insira a descrição do item: ");
					description = inputItem.nextLine();
					
					System.out.println("Insira a categoria do item: ");
					category = inputItem.nextLine();
					
					try {
					System.out.println("Insira o preço do item (Use '.' no lugar de ','): ");
					price = inputItem.nextLine();
					priceDecimal = new BigDecimal(price);
					
					String idNewItem = managMenu.register(name, description, priceDecimal, category, new HashMap<Products, BigDecimal>());
					Items newItem = (Items) managMenu.searchEntities(idNewItem);
					
					System.out.println("Lista dos produtos cadastrados: ");
					managementProducts.list();
					
					System.out.println("ADIÇÃO DOS PRODUTOS QUE COMPÕEM ESSE ITEM:");
					System.out.println("Digite o id do primeiro produto usado neste item: ");
					System.out.println("OBS: Digite 0 para parar de inserir produtos.");
					String idProd = inputItem.nextLine();
					
					do {
						if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
							System.out.println("ID não econtrado! Tente novamente");
						} else{
							System.out.println("Insira a quantidade desse produto usada na preparação do item (Em unidades): ");
							quantity = inputItem.nextLine();
							quantityDecimal = new BigDecimal(quantity);
							Products prodAdd = (Products) managementProducts.searchEntities(idProd);
							newItem.addProduct(quantityDecimal, prodAdd);
						};
						System.out.println("Digite o id do próximo produto usado neste item: ");
						idProd = inputItem.next();
					} while(!(idProd.equals("0")));
					
					if (newItem.getComposition().size() == 0) {
						System.out.println("Itens não podem estar vazios! Tente novamente!");
						managMenu.delete(idNewItem);
						break;
					}
					
					System.out.println("Item cadastrado com sucesso.");
	
					} catch(NumberFormatException eNum) {
						System.out.println("Formato inválido! Tente novamente.");
					} 
					break;
					
				case 2:
					managMenu.list();
					
					System.out.println("Insira o ID do item que deseja alterar: ");
					id = inputItem.next();
		
					if (!idExist(id) || (id.charAt(0) != 'I')) {
						System.out.println("ID não econtrado! Tente novamente");
						break;
					};
					
					
					String[] options = { "nome", "descricao", "categoria", "preço", "composicao"};
					System.out.println("\nO que você deseja alterar?");
					
					for (int i = 0; i < options.length; i++) {
						System.out.println((i + 1) + " - P/ " + options[i]);
					}
					option1 = inputItem.nextInt();
					
					while (!(option1 >= 1 && option1 <= options.length)) {
						System.out.println("Opção inválida. Tente novamente.");
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
								System.out.println("Insira o novo preço do produto (Use '.' no lugar de ','): ");
								price = inputItem.nextLine();
								newDataItem = new BigDecimal(price);
								managMenu.edit(id, option2, newDataItem);
								System.out.println("Atributo do produto alterado com sucesso.");
								break;


							case "composicao":
								String[] options2 = { "adicionar", "remover"};
								System.out.println("\nO que você deseja fazer?");
								
								for (int i = 0; i < options2.length; i++) {
									System.out.println((i + 1) + " - P/ " + options2[i] + "produto");
								}
								option1 = inputItem.nextInt();
								
								while (!(option1 >= 1 && option1 <= options.length)) {
									System.out.println("Opção inválida. Tente novamente.");
									System.out.println("Escolha: ");
									option1 = inputItem.nextInt();
								}
								
								if (option1 == 1) {
									System.out.println("ADIÇÃO DE PRODUTOS:");
									System.out.println("Lista dos produtos cadastrados: ");
									managementProducts.list();
									System.out.println("Digite o id do primeiro produto: ");
									System.out.println("OBS: Digite 0 para parar de inserir produtos.");
									String idProd = inputItem.next();
									
									do {
										if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
											System.out.println("ID não econtrado! Tente novamente");
										} else{
											System.out.println("Insira a quantidade desse produto usada na preparação do item (Em unidades): ");
											quantity = inputItem.nextLine();
											quantityDecimal = new BigDecimal(quantity);
											Products prodAdd = (Products) managementProducts.searchEntities(idProd);
											managMenu.addProductsItems(id, prodAdd, quantityDecimal);
										};
										System.out.println("Produto adicionado com sucesso!");
										System.out.println("Digite o id do próximo produto: ");
										idProd = inputItem.next();
									} while(idProd != "0");
								} else {
									System.out.println("REMOÇÃO DE PRODUTOS:");
									System.out.println("Lista dos produtos cadastrados: ");
									managementProducts.list();
									System.out.println("Digite o id do primeiro produto a ser removido: ");
									System.out.println("OBS: Digite 0 para parar de remover produtos.");
									String idProd = inputItem.next();
									
									do {
										if (!idExist(idProd) || (idProd.charAt(0) != 'P')) {
											System.out.println("ID não econtrado! Tente novamente");
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
												System.out.println("Remover o último produto deletará o item.");
											}
											System.out.println("Digite o id do próximo produto: ");
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
						System.out.println("Formato inválido! Tente novamente.");
						} catch(DateTimeParseException eDate) {
						System.out.println("Data inválida! Tente novamente.");
						}
					break;
					
				case 3:
					managMenu.list();
					
					System.out.println("Insira o ID do item que deseja deletar: ");
					id = inputItem.next();
					
					if (!idExist(id) || (id.charAt(0) != 'I')) {
						System.out.println("ID não econtrado! Tente novamente");
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

	public static void datasVendas(int operation, ManagementSales managSales, Scanner inputSale) {

					
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