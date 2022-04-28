package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import management_models.*;
import modeling_models.Providers;
import modeling_models.Users;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import exceptions.*;

public class Main {
	private static ArrayList<String> ids = new ArrayList<String>();
	
	static ManagementUsers managementUser = new ManagementUsers();
	static ManagementProviders managementProvider = new ManagementProviders();
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		int n = 0;
		while (n == 0) {
			System.out.println("AUTOMA«√O COMERCIAL \n1 - Logar no sistema \n2 - Sair do sistema");
			int answer = input.nextInt();
			
			if (answer == 1) {
				if (managementUser.checkSizeList() == false) {
					System.out.println("Sistema n„o possui nenhum registro, faÁa o primeiro cadastro no sistema!");
					managementUser.dataRegister();
				}
				login();
				optionsMenu();
			} else if (answer == 2) {
				System.out.println("SISTEMA ENCERRADO");
				break;
			} else {
				System.out.println("OpÁ„o inv·lida.");
			}
		}
	}
	
	public static void login() {
		String nick, password;
		Scanner input = new Scanner(System.in);
		boolean option = false;
		
		do {
		System.out.println("LOGIN");
		System.out.println("Insira seu nickname: ");
		nick = input.nextLine();
		System.out.println("Insira sua senha: ");
		password = input.nextLine();
		
		try {
			managementUser.checkLogin(nick, password);
			option = true;
		} catch(LoginDoesntMatch eLog) {
			System.out.println(eLog.getMessage());
		}
		
		} while(!option);
	}
	
	public static void optionsMenu() {
		String[] options = { "cadastrar", "editar", "excluir", "listar", "gerar relatÛrio", "encerrar"};
		String[] entities = {"usuario", "fornecedor", "item", "produto", "venda"};
		int option = 0;
		Scanner input = new Scanner(System.in);
	
		do {
			System.out.println("\nO que vocÍ deseja fazer?");
			
			for (int i = 0; i < 6; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			option = input.nextInt();
			
			if (option >= 1 && option <= 4) {
			System.out.println("O que vocÍ deseja " + options[option - 1] + "?");
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + " - P/ " + entities[i]);
			}
			int nEntities = input.nextInt();
			System.out.println("\n");
			switch (nEntities) {
				case 1:
					datasUsers(option, managementUser);
					break;
				case 2:
					menuSimulaFornecedor(option, managementProvider);
					break;
				/*case 3:
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
				System.out.println("\nGerando relatÛrio\n");
				generatePDF();
			} else if (option != 6) {
				System.out.println("OpÁ„o inv·lida. Tente de novo.");
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
		
		switch(operation) {
		case 1:
			managementSimulator.dataRegister();
			break;
		case 2:
			String id, option1 = null, newName = null;
			managementSimulator.list();
			
			System.out.println("Insira o ID do usu·rio que deseja alterar: ");
			id = input.next();
			System.out.println("Deseja alterar nome, nickname, senha ou cargo? ");
			option1 = input.next();
			System.out.println("Insira o atributo novo: ");
			newName = input.next();
			
			try {
			managementSimulator.edit(id, option1, newName);
			System.out.println("Atributo alterado com sucesso.");
			} catch(ExistentNicknameException eNick) {
				System.out.println(eNick.getMessage());
			}
			break;
		case 3: //ERRO n√£o apagou
			managementSimulator.list();
			
			System.out.println("Insira o ID do usu√°rio que deseja deletar: ");
			id = input.next();
			
			managementSimulator.delete(id);
			break;
		case 4:
			managementSimulator.list();
			break;
		}
	}
	
	public static void menuSimulaFornecedor(int operation, ManagementProviders managementSimulator) {
		String name, cnpj, address, option1, newName, id;
		Scanner input = new Scanner(System.in);
		
		switch(operation) {
		case 1:
			System.out.println("Insira o nome a ser registrado: ");
			name = input.nextLine();
			System.out.println("Insira o CNPJ a ser registrada: ");
			cnpj = input.nextLine();
			System.out.println("Insira o endere√ßo a ser registrado: ");
			address = input.nextLine();
			
			managementSimulator.register(name, cnpj, address);
			System.out.println("Fornecedor cadastrado com sucesso.");
			break;
		case 2:
			managementSimulator.list();
			
			System.out.println("Insita o ID do fornecedor que deseja alterar: ");
			id = input.next();
			System.out.println("Deseja alterar nome, endereco ou cnpj? ");
			option1 = input.next();
			System.out.println("Insira o atributo novo: ");
			newName = input.next();
			
			managementSimulator.edit(id, option1, newName);
			System.out.println("Atributo do fornecedor alterado com sucesso.");
			break;
		case 3:
			managementSimulator.list();
			
			System.out.println("Insira o ID do fornecedor que deseja deletar: ");
			id = input.next();
			
			managementSimulator.delete(id);
			System.out.println("Fornecedor deletado com sucesso.");
			break;
		case 4:
			managementSimulator.list();
			break;
		}
	}
	
	public static void generatePDF() {
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("documents.pdf"));
            document.open();

            // adicionando um par√°grafo no documento
            Paragraph p = new Paragraph("Relat√≥rio");
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
}