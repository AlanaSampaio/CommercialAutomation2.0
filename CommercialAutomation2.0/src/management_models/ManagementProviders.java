package management_models;


import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import modeling_models.Providers;

public class ManagementProviders extends Management {
	
	public String register(String name, String cnpj, String address) {
		Providers newProvider = new Providers(name, cnpj, address);
		this.register(newProvider);
		return newProvider.getId();
	}

	/**
	 * Método herdado de Gerenciamentos para editar um fornecedor.
	 * @throws IdDoesntExist 
	 * @throws EntitiesNotRegistred 
	 */
	public void edit(String idPEdit, String changedValue, Object newValue) throws IdDoesntExist, EntitiesNotRegistred {
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
	
	@Override
	public void list(boolean allInformations) throws EntitiesNotRegistred {
		
		if (this.getList().size() == 0) {
			throw new EntitiesNotRegistred();
		}
		
		this.getList().forEach(element -> {
			Providers prov = (Providers) element;
			System.out.println("ID: " + prov.getId() + "\n" + 
							   "Nome: " + prov.getName() + "\n" +
							   "CNPJ: " + prov.getCnpj());
			if (allInformations) {
				System.out.println("Endereço: " + prov.getAddress());
							   System.out.println("Produtos fornecidos:");
							   prov.listProdProvided();
			}
			System.out.println();
		});
	}
	

}
