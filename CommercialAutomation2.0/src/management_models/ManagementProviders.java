package management_models;

import java.util.Scanner;

import modeling_models.Providers;

public class ManagementProviders extends Management {
	
	public void dataRegister() {
		// Método para receber os dados do usuário e fazer o cadastro a partir deles.
		String name, cnpj, address;
		Scanner input = new Scanner(System.in);
		
		System.out.println("CADASTRO DO FORNECEDOR");
		System.out.println("Insira o nome: ");
		name = input.next();
		System.out.println("Insira o cnpj: ");
		cnpj = input.next();
		System.out.println("Insira o endereço: ");
		address = input.next();
					
		this.register(name, cnpj, address);
	}
	
	public void register(String name, String cnpj, String address) {
		Providers newProvider = new Providers(name, cnpj, address);
		this.register(newProvider);
	}

	/**
	 * Método herdado de Gerenciamentos para editar um fornecedor.
	 */
	@Override
	public void edit(String idPEdit, String changedValue, Object newValue) {
		Providers providerPEdit = (Providers) this.searchEntities(idPEdit);
		switch(changedValue) {
		case "nome":
			providerPEdit.setName((String) newValue);
			break;
		case "endereco":
			providerPEdit.setAddress((String) newValue);
			break;
		case "cnpj":
			providerPEdit.setCnpj((String) newValue);
			break;
		}
	}

}
