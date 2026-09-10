package br.com.cotiinformatica.desafio_estagio.entities;

import br.com.cotiinformatica.desafio_estagio.enums.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Transacao {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String descricao;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
