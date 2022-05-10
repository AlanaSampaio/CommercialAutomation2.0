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

/**
 * Classe que representa os usuarios do sistema.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class Users extends Entities {
	/**
	 * Nickname para login no sistema
	 */
	private String nickname;
	/**
	 * Senha para login no sistema
	 */
	private String password;
	/**
	 * Nome do usuario
	 */
	private String name;
	/**
	 * Cargo do usuario
	 */
	private String category;
	
	/**
	 * @param nickname: Nickname do usuario
	 * @param password: Senha do usuario
	 * @param name: Nome do usuario
	 * @param category: Cargo do usuario
	 */
	public Users(String nickname, String password, String name, String category) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.name = name;
		this.category = category;
		generatorCode("U");
	}
	
	
	/**
	 * @return nome
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name: Novo nome
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return cargo
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category: Novo cargo 
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return nickname 
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname: Novo nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return senha
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password: Nova senha
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
