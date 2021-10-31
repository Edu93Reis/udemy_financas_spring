package com.springudemy.minhasfinancas.api.dto;

public class AtualizaStatusDTO {
	
	public String status;

	public AtualizaStatusDTO() {
		super();
	}

	public AtualizaStatusDTO(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
