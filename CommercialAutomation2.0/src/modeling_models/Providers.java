package modeling_models;

public class Providers extends Entities {
	private String name;
	private String cnpj;
	private String address;

	public Providers(String name, String cnpj, String address) {
		super();
		this.name = name;
		this.cnpj = cnpj;
		this.address = address;
		generatorCode("P");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
