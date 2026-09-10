package br.com.cotiinformatica.desafio_estagio.services;

import br.com.cotiinformatica.desafio_estagio.entities.Pessoa;
import br.com.cotiinformatica.desafio_estagio.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {


    @Autowired
    private PessoaRepository pessoaRepository;

    //Método para salvar uma pessoa
    public Pessoa salvar(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }


    //Método para deletar uma pessoa
    public void deletar(UUID id){
        if(!pessoaRepository.existsById(id)){
            throw new RuntimeException("Pessoa não encontrata.");
        }
        pessoaRepository.deleteById(id);
    }


    //Método para listar pessoas
    public List<Pessoa> listar(){
        return pessoaRepository.findAll();
    }

}
