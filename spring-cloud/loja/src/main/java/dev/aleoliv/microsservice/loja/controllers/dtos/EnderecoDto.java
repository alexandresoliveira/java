package dev.aleoliv.microsservice.loja.controllers.dtos;

public class EnderecoDto {

	private String rua;
	private Integer numero;
	private String estado;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "EnderecoDto [rua=" + rua + ", numero=" + numero + ", estado=" + estado + "]";
	}

}
