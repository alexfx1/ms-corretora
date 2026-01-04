package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_MOTORISTA")
@Getter
@Setter
public class Motorista {
    @Id
    @Column(name = "CD_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdMotorista;

    @Column(name = "CD_CPF")
    private String cdCpf;

    @Column(name = "DS_NOME")
    private String dsNome;

    @Column(name = "DS_PLACA")
    private String dsPlaca;

    @Column(name = "DS_CIDADE")
    private String dsCidade;

    @Column(name = "DS_ESTADO")
    private String dsEstado;

    @Column(name = "DS_TELEFONE")
    private String dsTelefone;

    @Column(name = "DS_CONTATO")
    private String dsContato;
}
