package br.com.cotiinformatica.desafio_estagio.controllers;


import br.com.cotiinformatica.desafio_estagio.entities.Pessoa;
import br.com.cotiinformatica.desafio_estagio.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa>  criar(@RequestBody Pessoa pessoa){
        Pessoa novaPessoa = pessoaService.salvar(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar(){
        List<Pessoa> pessoas = pessoaService.listar();
        return ResponseEntity.ok(pessoas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deletar(@PathVariable UUID id){
        try{
            pessoaService.deletar(id);
            return ResponseEntity.ok("Pessoa excluída com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

