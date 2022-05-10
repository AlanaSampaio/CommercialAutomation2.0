/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programação II
Concluido em: 09/05/2022
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************/

package management_models;


import exceptions.EntitiesNotRegistred;
import exceptions.IdDoesntExist;
import modeling_models.Providers;

/**
 * Classe responsavel por gerir um CRUD dos fornecedores
 * @author Vanderleicio Junior
 * @author Alana Sampaio
 */
public class ManagementProviders extends Management {
	
	/**
	 * Metodo cria e cadastra um fornecedor a partir das informacoes passadas por parametro
	 * @param name: nome do fornecedor
	 * @param cnpj: cnpj do fornecedor
	 * @param address: endereco do fornecedor
	 */
	public String register(String name, String cnpj, String address) {
		Providers newProvider = new Providers(name, cnpj, address);
		this.register(newProvider);
		return newProvider.getId();
	}

	/**
	 * Metodo herdado de Gerenciamentos para editar um fornecedor.
	 * @throws IdDoesntExist: para quando o ID nao existe
	 * @throws EntitiesNotRegistred: para quando a entidade nao esta registrada
	 * 
	 * @param idPEdit: id do fornecedor a ser editado
	 * @param changedValue: nome do atributo a ser editado (nome, cnpj, endereco)
	 * @param newValue: novo valor a ser inserido
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
	
	/**
	 * Medoro lista os fornecedores cadastrados
	 * 
	 * @param allInformations: true quando necessario que informacoes adicionais do fornecedor sejam mostrados
	 * 
	 * @throws EntitiesNotRegistred: para quando a entidade nao foi registrada
	 */
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
				System.out.println("Endere�o: " + prov.getAddress());
							   System.out.println("Produtos fornecidos:");
							   prov.listProdProvided();
			}
			System.out.println();
		});
	}
	

}
