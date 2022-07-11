package dev.aleoliv.microsservice.loja.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.aleoliv.microsservice.loja.controllers.dtos.InfoEntregaDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.VoucherDto;

@FeignClient("TRANSPORTADOR")
public interface TransportadorClient {

	@PostMapping
	public VoucherDto reservaEntrega(@RequestBody InfoEntregaDto pedidoDTO);
}
