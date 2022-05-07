package exceptions;


public class EntitiesNotRegistred extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntitiesNotRegistred() {
		super("Ainda nao temos entidades deste tipo registradas");
	}
}
