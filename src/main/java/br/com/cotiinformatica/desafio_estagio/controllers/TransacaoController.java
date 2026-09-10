package br.com.cotiinformatica.desafio_estagio.controllers;


import br.com.cotiinformatica.desafio_estagio.dtos.RelatorioGeralDTO;
import br.com.cotiinformatica.desafio_estagio.entities.Transacao;
import br.com.cotiinformatica.desafio_estagio.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Transacao transacao){
        try {
            Transacao novaTransacao = transacaoService.cadastrar(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTransacao);

        }
        // Retorna o erro 400 (Bad Request) com a mensagem caso o menor de idade tente cadastrar receita
        catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listar(){
        List<Transacao> transacoes = transacaoService.listar();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/totais")
    public ResponseEntity<RelatorioGeralDTO> consultarTotais(){
        RelatorioGeralDTO relatorio = transacaoService.gerarRelatoriosTotais();
        return ResponseEntity.ok(relatorio);
    }


}
