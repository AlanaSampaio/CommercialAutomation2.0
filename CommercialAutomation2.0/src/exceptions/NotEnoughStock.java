package exceptions;


public class NotEnoughStock extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NotEnoughStock() {
		super("N�o h� produtos suficientes.");
	}

}
