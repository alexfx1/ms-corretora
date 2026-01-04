package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_PARAMETRO")
@Getter
@Setter
public class Parametro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_PARAMETRO")
    private Long cdParametro;

    @Column(name = "DS_CHAVE_PARAMETRO")
    private String dsChaveParametro;

    @Column(name = "DS_PARAMETRO")
    private String dsParametro;

    @Column(name = "VL_PARAMETRO")
    private String vlParametro;
}
