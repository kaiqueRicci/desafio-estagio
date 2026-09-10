package br.com.cotiinformatica.desafio_estagio.services;

import br.com.cotiinformatica.desafio_estagio.dtos.RelatorioGeralDTO;
import br.com.cotiinformatica.desafio_estagio.dtos.TotalPessoaDTO;
import br.com.cotiinformatica.desafio_estagio.entities.Pessoa;
import br.com.cotiinformatica.desafio_estagio.entities.Transacao;
import br.com.cotiinformatica.desafio_estagio.enums.TipoTransacao;
import br.com.cotiinformatica.desafio_estagio.repositories.PessoaRepository;
import br.com.cotiinformatica.desafio_estagio.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    //MÉTODO PARA GERAR O RELATÓRIO DE TOTAIS
    public RelatorioGeralDTO gerarRelatoriosTotais(){
        List<Pessoa> pessoas = pessoaRepository.findAll();

        // Mapeia cada pessoa para o TotalPessoaDTO
        List<TotalPessoaDTO> totaisPessoas = pessoas.stream().map(pessoa -> {

            // Soma as receitas da pessoa
            BigDecimal totalReceitas = pessoa.getTransacoes().stream()
                    .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Soma as despesas da pessoa
            BigDecimal totalDespesas = pessoa.getTransacoes().stream()
                    .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calcula o saldo individual (Receitas - Despesas)
            BigDecimal saldo = totalReceitas.subtract(totalDespesas);

            // Retorna o DTO preenchido
            return new TotalPessoaDTO(
                    pessoa.getId(),
                    pessoa.getNome(),
                    pessoa.getIdade(),
                    totalReceitas,
                    totalDespesas,
                    saldo
            );
        }).toList();

        //  Calcula os totais gerais somando todos os DTOs individuais
        BigDecimal geralReceitas = totaisPessoas.stream()
                .map(TotalPessoaDTO::getTotalReceitas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal geralDespesas = totaisPessoas.stream()
                .map(TotalPessoaDTO::getTotalDespesas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoLiquidoGeral = geralReceitas.subtract(geralDespesas);

        //  Retorna o relatório geral completo
        return new RelatorioGeralDTO(
                totaisPessoas,
                geralReceitas,
                geralDespesas,
                saldoLiquidoGeral
        );
    }
}
