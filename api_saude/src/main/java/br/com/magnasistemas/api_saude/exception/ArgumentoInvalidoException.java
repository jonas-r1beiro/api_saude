package br.com.magnasistemas.api_saude.exception;

public class ArgumentoInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArgumentoInvalidoException(String erro) {
		super(erro);
	}
}
