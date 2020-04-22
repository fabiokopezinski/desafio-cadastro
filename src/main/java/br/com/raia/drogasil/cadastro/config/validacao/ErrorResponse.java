package br.com.raia.drogasil.cadastro.config.validacao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	public ErrorResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }
  
    private String message;
    private String details;
}
