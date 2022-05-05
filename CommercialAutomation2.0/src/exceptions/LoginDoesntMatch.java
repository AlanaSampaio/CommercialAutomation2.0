package exceptions;


public class LoginDoesntMatch extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginDoesntMatch() {
		super("Login e/ou senha incorreto(s).");
	}
}
