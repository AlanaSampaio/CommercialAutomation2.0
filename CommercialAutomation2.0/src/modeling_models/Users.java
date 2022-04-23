package modeling_models;

public class Users extends Entities {
	private String nickname;
	private String password;
	private String name;
	private String category;
	
	public Users(String nickname, String password, String name, String category) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.name = name;
		this.category = category;
		generatorCode("U");
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
