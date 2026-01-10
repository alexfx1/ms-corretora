package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_CONTRATO")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_CONTRATO")
    private Long cdContrato;

    @Column(name = "DS_STATUS")
    private String dsStatus;

    @Column(name = "DT_INICIAL")
    private LocalDateTime dtInicial;

    @Column(name = "DT_UPDATE")
    private LocalDateTime dtUpdate;

    @Column(name = "CD_COMPRADOR", insertable = false, updatable = false)
    private Long cdComprador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_COMPRADOR")
    private Cliente comprador;

    @Column(name = "CD_VENDEDOR", insertable = false, updatable = false)
    private Long cdVendedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_VENDEDOR")
    private Cliente vendedor;

    @Column(name = "CD_MERCADORIA", insertable = false, updatable = false)
    private Long cdMercadoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_MERCADORIA")
    private Mercadoria mercadoria;

    @Column(name = "VL_QUANTIDADE")
    private BigDecimal vlQuantidade;

    @Column(name = "VL_QUANTIDADE_SACO")
    private Integer vlQuantidadeSaco;

    @Column(name = "VL_PRECO_SACO")
    private BigDecimal precoSaco;

    @Column(name = "VL_KILO")
    private BigDecimal vlKilo;

    @Column(name = "VL_COMISSAO")
    private BigDecimal vlComissao;

    @Column(name = "DS_PADRAO_TOLERANCIA")
    private String dsPadraoTolerancia;

    @Column(name = "DS_ARMAZENAGEM")
    private String dsArmazenagem;

    @Column(name = "DS_ENDERECO_ENTREGA")
    private String dsEnderecoEntrega;

    @Column(name = "DS_EMBALAGEM")
    private String dsEmbalagem;

    @Column(name = "DS_PESO_QUALIDADE")
    private String dsPesoQualidade;

    @Column(name = "DS_CARGA_CONTA")
    private String dsCargaConta;

    @Column(name = "DS_PAGAMENTO")
    private String dsPagamento;

    @Column(name = "DS_FORMA_PAGAMENTO")
    private String dsFormaPagamento;

    @Column(name = "CD_CORRETOR")
    private Long cdCorretor;

    @Column(name = "DS_DESCRICAO")
    private String dsDescricao;

    @Column(name = "CD_MOTORISTA", insertable = false, updatable = false)
    private Long cdMotorista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @Column(name = "DT_CONTRATO")
    private LocalDate dtContrato;
}
