package com.corretora.api.dto.contrato;

import com.corretora.api.dto.ClienteDto;
import com.corretora.api.dto.MercadoriaDto;
import com.corretora.api.dto.MotoristaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContratoDto {

    private Long cdCorretor;

    private ClienteDto comprador;

    private ClienteDto vendedor;

    private MotoristaDto motorista;

    private MercadoriaDto mercadoria;

    private BigDecimal vlQuantidade;

    private BigDecimal precoSaco;

    private BigDecimal vlKilo;

    private BigDecimal vlComissao;

    private Integer vlQuantidadeSaco;

    private String dsPadraoTolerancia;

    private String dsArmazenagem;

    private String dsEnderecoEntrega;

    private String dsEmbalagem;

    private String dsPesoQualidade;

    private String dsCargaConta;

    private String dsPagamento;

    private String dsFormaPagamento;

    private String dsDescricao;

    private String dtContrato;
}
