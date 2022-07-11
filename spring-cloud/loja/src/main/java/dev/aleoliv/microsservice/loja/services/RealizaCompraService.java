package dev.aleoliv.microsservice.loja.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import dev.aleoliv.microsservice.loja.clients.FornecedorClient;
import dev.aleoliv.microsservice.loja.clients.TransportadorClient;
import dev.aleoliv.microsservice.loja.controllers.dtos.CompraDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.InfoEntregaDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.InfoFornecedorDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.InfoPedidoDto;
import dev.aleoliv.microsservice.loja.controllers.dtos.VoucherDto;
import dev.aleoliv.microsservice.loja.models.Compra;
import dev.aleoliv.microsservice.loja.models.CompraState;
import dev.aleoliv.microsservice.loja.repositories.CompraRepository;

@Service
public class RealizaCompraService {

	private final FornecedorClient fornecedorClient;

	private final CompraRepository compraRepository;

	private final TransportadorClient transportadorClient;

	public RealizaCompraService(FornecedorClient fornecedorClient, TransportadorClient transportadorClient,
			CompraRepository compraRepository) {
		this.fornecedorClient = fornecedorClient;
		this.transportadorClient = transportadorClient;
		this.compraRepository = compraRepository;
	}

	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada"));
	}
	
	public Compra reprocessaCompra(Long id) {
		return null;
	}
	
	public Compra cancelaCompra(Long id) {
		return null;
	}

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra execute(CompraDto compraDto) {

		Compra compraSalva = new Compra();
		compraSalva.setEstado(CompraState.RECEBIDO);
		compraSalva.setEnderecoDestino(compraDto.getEndereco().toString());
		compraRepository.save(compraSalva);
		compraDto.setId(compraSalva.getId());
		
		InfoFornecedorDto info = fornecedorClient.getInfoByEstado(compraDto.getEndereco().getEstado());
		InfoPedidoDto pedido = fornecedorClient.realizaPedido(compraDto.getItens());
		compraSalva.setEstado(CompraState.PEDIDO_REALIZADO);
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraRepository.save(compraSalva);
		
		
		InfoEntregaDto entregaDto = new InfoEntregaDto();
		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(info.getEndereco());
		VoucherDto voucher = transportadorClient.reservaEntrega(entregaDto);
		compraSalva.setEstado(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		compraRepository.save(compraSalva);
		
		return compraSalva;
	}

	public Compra realizaCompraFallback(CompraDto compraDto) {
		if (compraDto.getId() != null) {
			Optional<Compra> optional = compraRepository.findById(compraDto.getId());
			return optional.orElseThrow(() -> new RuntimeException("Compra não encontrada"));
		}
		
		Compra compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compraDto.getEndereco().toString());
		return compraSalva;
	}
}
