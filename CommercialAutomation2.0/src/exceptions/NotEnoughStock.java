package exceptions;


public class NotEnoughStock extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NotEnoughStock() {
		super("Não há produtos suficientes.");
	}

}
