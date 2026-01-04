package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_CLIENTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_CLIENTE")
    private Long cdCliente;

    @Column(name = "DS_NOME")
    private String dsNome;

    @Column(name = "CD_CPF_CNPJ")
    private String cdCpfCnpj;

    @Column(name = "DS_ENDERECO")
    private String dsEndereco;

    @Column(name = "CD_CEP")
    private String cdCep;

    @Column(name = "DS_CIDADE")
    private String dsCidade;

    @Column(name = "DS_ESTADO")
    private String dsEstado;

    @Column(name = "DS_INS")
    private String dsIns;

    @Column(name = "DS_BANCO")
    private String dsBanco;

    @Column(name = "CD_AGENCIA")
    private String cdAgencia;

    @Column(name = "CD_CONTA")
    private String cdConta;

    @Column(name = "DS_CHAVE_PIX")
    private String dsChavePix;

    @Column(name = "DS_TELEFONE")
    private String dsTelefone;

    @Column(name = "DS_CONTATO")
    private String dsContato;
}
