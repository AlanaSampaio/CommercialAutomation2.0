package exceptions;

@SuppressWarnings("serial")
public class LoginDoesntMatch extends Exception {
	
	public LoginDoesntMatch() {
		super("Login e/ou senha incorreto(s).");
	}
}
