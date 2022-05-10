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

package modeling_models;

import main.Main;

/**
 * Classe das entidades presentes no sistema.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public abstract class Entities {
	
	/**
	 * Codigo inico para identificacao de cada entidade criada.
	 */
	private String id;

	/**
	 * Converte o codigo hashcode do objeto numa string, adiciona o 
	 * preFixo da classe, fornecido no construtor de cada uma delas, e retorna.
	 * @param preFixo: Letra que sera usada para identificacao da classe da Entidade
	 */
	public void generatorCode(String prefix) {
		String hash = String.valueOf(this.hashCode());
		hash = prefix + "-" + hash;
		this.id = validateCode(hash);
		Main.addId(this.getId());
	}

	/**
	 * Verifica se o ID passado ja existe e se sim, gera um novo a partir do primeiro.
	 * @param cod: ID que sera verificado.
	 * @return O proprio ID caso nao exista, um ID novo caso ele ja exista.
	 */
	public String validateCode(String cod) {
		int cont = 0;
		String newCod = cod;
		while (Main.idExist(newCod)){
			cont++;
			String[] codSeparate = cod.split("-");
			newCod = codSeparate[0] + "-" + String.valueOf(cont) + codSeparate[1];
		}
		return newCod;
	};
	
	/**
	 * @return ID
	 */
	public String getId() {
		return id;
	}
}
