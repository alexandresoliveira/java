package dev.aleoliv.microsservice.loja.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.microsservice.loja.controllers.dtos.CompraDto;
import dev.aleoliv.microsservice.loja.models.Compra;
import dev.aleoliv.microsservice.loja.services.RealizaCompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	private final RealizaCompraService compraService;

	public CompraController(RealizaCompraService compraService) {
		this.compraService = compraService;
	}

	@GetMapping("/{id}")
	public Compra getById(@PathVariable Long id) {
		return compraService.getById(id);
	}

	@PostMapping
	public Compra realizaCompra(@RequestBody CompraDto compraDto) {
		return compraService.execute(compraDto);
	}
}
