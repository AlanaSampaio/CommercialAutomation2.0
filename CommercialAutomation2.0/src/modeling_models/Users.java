/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programa��o II
Concluido em: 09/05/2022
Declaro que este c�digo foi elaborado por mim de forma individual e n�o cont�m nenhum
trecho de c�digo de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e p�ginas ou documentos eletr�nicos da Internet. Qualquer trecho de c�digo
de outra autoria que n�o a minha est� destacado com uma cita��o para o autor e a fonte
do c�digo, e estou ciente que estes trechos n�o ser�o considerados para fins de avalia��o.
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
