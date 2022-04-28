package exceptions;

@SuppressWarnings("serial")
public class ExistentNicknameException extends Exception {
	
	public ExistentNicknameException() {
		super("Nickname já cadastrado. Tente outro.");
	}
}
