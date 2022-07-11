package dev.aleoliv.microsservice.loja.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.aleoliv.microsservice.loja.models.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long> {
}
