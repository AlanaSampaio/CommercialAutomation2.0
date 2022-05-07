package exceptions;


public class IdDoesntExist extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IdDoesntExist() {
		super("ID nao econtrado! Tente novamente");
	}

}
