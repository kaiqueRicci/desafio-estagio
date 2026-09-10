package br.com.cotiinformatica.desafio_estagio.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Pessoa {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String nome;
    private Integer idade;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoes;
}
