package br.com.raia.drogasil.cadastro.config.validacao;

public class ResourceNotFoundException extends RuntimeException  {

	private static final long serialVersionUID = -8252595034152614126L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
