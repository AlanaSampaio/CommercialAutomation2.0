package exceptions;

@SuppressWarnings("serial")
public class ExistentNicknameException extends Exception {
	
	public ExistentNicknameException() {
		super("Nickname j� cadastrado. Tente outro.");
	}
}
