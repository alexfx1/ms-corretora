package com.corretora.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_MERCADORIA")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Mercadoria {

    @Id
    @Column(name = "CD_MERCADORIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdMercadoria;

    @Column(name = "DS_MERCADORIA")
    private String dsMercadoria;

    @Column(name = "FL_ATIVO")
    private Boolean flAtivo;

    public Mercadoria(String dsMercadoria, Boolean flAtivo) {
        this.dsMercadoria = dsMercadoria;
        this.flAtivo = flAtivo;
    }

}
