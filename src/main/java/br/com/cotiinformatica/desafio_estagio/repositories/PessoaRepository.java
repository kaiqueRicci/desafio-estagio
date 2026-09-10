package br.com.cotiinformatica.desafio_estagio.repositories;

import br.com.cotiinformatica.desafio_estagio.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
}
