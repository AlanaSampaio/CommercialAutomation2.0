package exceptions;


public class NotEnoughProduct extends Exception {
	private static final long serialVersionUID = 1L;
	private int quantLeft;

	public NotEnoughProduct(int quantLeft) {
		super("Produto não tem a quantidade suficiente.");
		this.quantLeft = quantLeft;
	}
	
	public int getQuantLeft() {
		return this.quantLeft;
	}
}
