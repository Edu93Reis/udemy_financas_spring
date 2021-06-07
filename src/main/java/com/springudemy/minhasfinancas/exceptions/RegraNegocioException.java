package com.springudemy.minhasfinancas.exceptions;

//Runtime - em tempo de execucao
public class RegraNegocioException extends RuntimeException {

	public RegraNegocioException(String msg) {
		super(msg);
	}
	
}
