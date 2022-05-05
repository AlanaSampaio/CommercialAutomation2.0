package exceptions;

public class ExistentNicknameException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistentNicknameException() {
		super("Nickname já cadastrado. Tente outro.");
	}
}
