package modeling_models;

import modeling.Modeling;

public abstract class Entities {
	
	private String id;

	public void generatorCode(String prefix) {
		String hash = String.valueOf(this.hashCode());
		hash = prefix + "-" + hash;
		this.id = validateCode(hash);
		Modeling.addId(this.getId());
	}

	public String validateCode(String cod) {
		int cont = 0;
		String newCod = cod;
		while (Modeling.idExist(newCod)){
			cont++;
			String[] codSeparate = cod.split("-");
			newCod = codSeparate[0] + "-" + String.valueOf(cont) + codSeparate[1];
		}
		return newCod;
	};
	
	public String getId() {
		return id;
	}
}
