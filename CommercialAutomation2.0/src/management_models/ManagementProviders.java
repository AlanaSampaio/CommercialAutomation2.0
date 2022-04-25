package management_models;

import modeling_models.Providers;

public class ManagementProviders extends Management {
	
	public void register(String name, String cnpj, String address) {
		Providers newProvider = new Providers(name, cnpj, address);
		this.register(newProvider);
	}

	/**
	 * M�todo herdado de Gerenciamentos para editar um fornecedor.
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
