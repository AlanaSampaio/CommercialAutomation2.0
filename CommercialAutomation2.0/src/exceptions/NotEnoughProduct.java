/***************************
Autores: Alana Sampaio e Vanderleicio Junior
Componente Curricular: Programação II
Concluido em: 09/05/2022
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************/

package exceptions;


/**
 * Exception gerada quando nao ha quantidade de produto suficiente para reduzí-lo.
 * @author Alana Sampaio
 * @author Vanderleicio Junior
 */
public class NotEnoughProduct extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Quantidade que faltou para o produto conseguir suprir o que foi pedido
	 */
	private int quantLeft;

	/**
	 * @param quantLeft: int quantidade que falta.
	 */
	public NotEnoughProduct(int quantLeft) {
		super("Produto não tem a quantidade suficiente.");
		this.quantLeft = quantLeft;
	}
	
	/**
	 * 
	 * @return a quantidade faltante do produto.
	 */
	public int getQuantLeft() {
		return this.quantLeft;
	}
}
