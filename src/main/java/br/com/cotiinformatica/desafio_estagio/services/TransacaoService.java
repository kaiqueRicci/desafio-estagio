package br.com.cotiinformatica.desafio_estagio.services;

import br.com.cotiinformatica.desafio_estagio.entities.Pessoa;
import br.com.cotiinformatica.desafio_estagio.entities.Transacao;
import br.com.cotiinformatica.desafio_estagio.enums.TipoTransacao;
import br.com.cotiinformatica.desafio_estagio.repositories.PessoaRepository;
import br.com.cotiinformatica.desafio_estagio.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Transacao cadastrar(Transacao transacao){

        //Valida se a pessoa informada realmente existe no banco
        Pessoa pessoa = pessoaRepository.findById(transacao.getPessoa().getId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com o id informado."));


        //Menor de 18 anos só pode cadastrar despesas
        if(pessoa.getIdade() < 18 && transacao.getTipo() == TipoTransacao.RECEITA){
            throw new IllegalArgumentException("Menores de 18 anos não podem cadastrar receitas, apenas despesas.");
        }


        //Associa a pessoa completa a transacão
        transacao.setPessoa(pessoa);

        return transacaoRepository.save(transacao);
    }



    public List<Transacao> listar(){
        return transacaoRepository.findAll();
    }
}
