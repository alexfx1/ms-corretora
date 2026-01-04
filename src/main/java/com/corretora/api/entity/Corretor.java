package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "TBL_CORRETOR")
@Getter
@Setter
public class Corretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_CORRETOR")
    private Long cdCorretor;

    @Column(name = "DS_NOME")
    private String dsNome;

    @NonNull
    @Column(name = "CD_CPF")
    private String cdCpf;

    @Column(name = "DS_BANCO")
    private String dsBanco;

    @Column(name = "CD_AGENCIA")
    private String cdAgencia;

    @Column(name = "CD_CONTA")
    private String cdConta;

    @Column(name = "DS_CIDADE")
    private String dsCidade;

    @Column(name = "DS_ESTADO")
    private String dsEstado;

    @Column(name = "DS_CHAVE_PIX")
    private String dsChavePix;
}
