package br.com.cotiinformatica.desafio_estagio.repositories;

import br.com.cotiinformatica.desafio_estagio.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}
