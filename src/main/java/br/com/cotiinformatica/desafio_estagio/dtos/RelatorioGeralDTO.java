package br.com.cotiinformatica.desafio_estagio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioGeralDTO {

    private List<TotalPessoaDTO> pessoas;
    private BigDecimal geralReceitas;
    private BigDecimal geralDespesas;
    private BigDecimal saldoLiquidoGeral;
}
