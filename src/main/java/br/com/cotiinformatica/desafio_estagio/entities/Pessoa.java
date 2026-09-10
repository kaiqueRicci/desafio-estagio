package br.com.cotiinformatica.desafio_estagio.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Transacao> transacoes;
}
