package dev.aleoliv.microsservice.loja.controllers.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompraDto {

	@JsonIgnore
	private Long id;
	private List<ItemDaCompraDto> itens;
	private EnderecoDto endereco;

	public List<ItemDaCompraDto> getItens() {
		return itens;
	}

	public void setItens(List<ItemDaCompraDto> itens) {
		this.itens = itens;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
