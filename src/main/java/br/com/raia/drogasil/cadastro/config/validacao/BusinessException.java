package br.com.raia.drogasil.cadastro.config.validacao;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7115458404179999536L;

	public BusinessException() {
		super(); 
	}
	
	public BusinessException(String message) {
		super(message);
	}

}

