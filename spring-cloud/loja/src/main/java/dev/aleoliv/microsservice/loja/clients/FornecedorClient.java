package dev.aleoliv.microsservice.loja.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.aleoliv.microsservice.loja.controllers.dtos.InfoFornecedorDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.InfoPedidoDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.ItemDaCompraDto;

@FeignClient("FORNECEDOR")
public interface FornecedorClient {

	@GetMapping("/info/{estado}")
	InfoFornecedorDto getInfoByEstado(@PathVariable String estado);

	@PostMapping("/pedido")
	InfoPedidoDto realizaPedido(List<ItemDaCompraDto> itens);
}
