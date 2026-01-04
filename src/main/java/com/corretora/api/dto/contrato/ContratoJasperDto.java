package com.corretora.api.dto.contrato;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContratoJasperDto {
    private String idContrato;

    // Comprador
    private String compradorNome;
    private String compradorCpfCnpj;
    private String compradorEndereco;
    private String compradorCidade;
    private String compradorInsc;
    private String compradorEstado;

    // Vendedor
    private String vendedorNome;
    private String vendedorCpfCnpj;
    private String vendedorEndereco;
    private String vendedorCidade;
    private String vendedorInsc;
    private String vendedorEstado;

    // Mercadoria
    private String mercadoria;
    private String quantidade;
    private String quantidadeSaco;
    private String kiloSaco;
    private String padraoTolerancia;
    private String armazenagem;
    private String entrega;
    private String embalagem;
    private String pesoQualidade;
    private String preco;

    // Pagamento
    private String pagamento;
    private String favorecido;
    private String vendedorBanco;
    private String vendedorAgencia;
    private String vendedorConta;
    private String formaPagamento;

    // Comissão
    private String corretorNome;
    private String corretorBanco;
    private String corretorCidade;
    private String corretorCpf;
    private String corretorAgencia;
    private String corretorConta;
    private String corretorEstado;

    // Política / Observações
    private String politicaValores;
    private String observacoes;

    // Assinatura / Data
    private String cidadeData;

    // Motorista
    private String motoristaNome;
    private String motoristaPlaca;
    private String motoristaCidade;
    private String motoristaEstado;
    private String motoristaCpf;

}
