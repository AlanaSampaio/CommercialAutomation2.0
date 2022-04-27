package modeling;

import java.util.ArrayList;
import java.util.Scanner;

import management_models.*;
import modeling_models.*;

public class Modeling {
private static ArrayList<String> ids = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		ManagementUsers managementUser = new ManagementUsers();
		//GerenciaFornecedores gerenForn= new GerenciaFornecedores();
		//GerenciaProdutos gerenProd = new GerenciaProdutos();
		//GerenciaCardapio gerenCard = new GerenciaCardapio();
		//GerenciaVendas gerenVenda = new GerenciaVendas();
		
		String[] options = { "cadastrar", "editar", "excluir", "listar", "encerrar"};
		String[] entidades = {"usuario", "fornecedor", "item", "produto", "venda"};
		int option = 0;
		Scanner input = new Scanner(System.in);
	
		do {
			System.out.println("O que você deseja fazer?");
			
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + " - P/ " + options[i]);
			}
			option = input.nextInt();
			
			if (option >= 1 && option <= 4) {
			System.out.println("O que voc� deseja " + options[option - 1] + "?");
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + " - P/ " + entidades[i]);
			}
			int nEntidade = input.nextInt();
			System.out.println("\n");
			switch (nEntidade) {
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
					menuSimulaVenda(opcao, gerenVenda);
					break;*/
			}
			System.out.println("\n");
			} else if (option != 5) {
				System.out.println("Opição inválida. Tente de novo.");
			}
		} while (option != 5);
		System.out.println("SISTEMA ENCERRADO");
		input.close();
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
	
	/*
	public static void menuSimulaFornecedor(int operacao, GerenciaFornecedores gerSimu) {
		String id1;
		switch(operacao) {
		case 1:
			gerSimu.cadastrar("nome1", "12345", "pra�a");
			System.out.println("Fornecedor cadastrado com sucesso.");
			break;
		case 2:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.editar(id1, "nome", "nome2");
			System.out.println("Atributo do fornecedor alterado com sucesso.");
			break;
		case 3:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.deletar(id1);
			System.out.println("Fornecedor deletado com sucesso.");
			break;
		case 4:
			gerSimu.listar().forEach(simu -> System.out.println(((Fornecedores)simu).getId() + "\n" + 
																((Fornecedores)simu).getNome()+ "\n" + 
																((Fornecedores)simu).getCnpj()+ "\n"+
																((Fornecedores)simu).getEndereco()));
			break;
		}
	}
	
	public static void menuSimulaItem(int operacao, GerenciaCardapio gerSimu) {
		String id1;
		ArrayList<Produtos> composicao = new ArrayList<Produtos>();
		
		switch(operacao) {
		case 1:
			gerSimu.cadastrar("nome1", "descricao1", "10.90", "categoria1", composicao);
			System.out.println("Item cadastrado com sucesso.");
			break;
		case 2:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.editar(id1, "nome", "nome2");
			System.out.println("Atributo do item alterado com sucesso.");
			break;
		case 3:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.deletar(id1);
			System.out.println("Item deletado com sucesso.");
			break;
		case 4:
			gerSimu.listar().forEach(simu -> System.out.println(((Itens)simu).getId() + "\n" + 
																((Itens)simu).getNome()+ "\n" + 
																((Itens)simu).getDescricao()+ "\n"+
																((Itens)simu).getPreco()+ "\n"+
																((Itens)simu).getCategoria()));
			break;
		}
	}
	
	public static void menuSimulaProduto(int operacao, GerenciaProdutos gerSimu) {
		String id1;
		Fornecedores fornecedor = new Fornecedores("nome1", "12345", "pra�a");
		switch(operacao) {
		case 1:
			gerSimu.cadastrar("nome", new BigDecimal("12.34"), "01/01/2023", fornecedor);
			System.out.println("Produto cadastrado com sucesso.");
			break;
		case 2:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.editar(id1, "nome", "nome2");
			System.out.println("Atributo do produto alterado com sucesso.");
			break;
		case 3:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.deletar(id1);
			System.out.println("Produto deletado com sucesso.");
			break;
		case 4:
			gerSimu.listar().forEach(simu -> System.out.println(((Produtos)simu).getId() + "\n" + 
																((Produtos)simu).getNome()+ "\n" + 
																((Produtos)simu).getPreco()+ "\n"+
																((Produtos)simu).getValidade()));
			break;
		}
	}
	
	public static void menuSimulaVenda(int operacao, GerenciaVendas gerSimu) {
		String id1;
		ArrayList<Itens> listaItens = new ArrayList<Itens>();
		switch(operacao) {
		case 1:
			gerSimu.cadastrar("01/01/2023", "12:34", listaItens, "PIX");
			System.out.println("Venda cadastrada com sucesso.");
			break;
		case 2:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.editar(id1, "dia", "31/12/2022");
			System.out.println("Atributo do Venda alterada com sucesso.");
			break;
		case 3:
			id1 = gerSimu.listar().get(0).getId();
			gerSimu.deletar(id1);
			System.out.println("Venda deletada com sucesso.");
			break;
		case 4:
			gerSimu.listar().forEach(simu -> System.out.println(((Vendas)simu).getId() + "\n" + 
																((Vendas)simu).getDia()+ "\n" + 
																((Vendas)simu).getHorario()+ "\n"+
																((Vendas)simu).getModoDePagamento()));
			break;
		}*/
	}

}
