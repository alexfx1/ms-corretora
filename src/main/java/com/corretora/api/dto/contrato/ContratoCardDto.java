package com.corretora.api.dto.contrato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoCardDto {
    private Long cdContrato;
    private String dsStatus;
    private LocalDateTime dtInicial;
    private LocalDateTime dtUpdate;
    private String mercadoria;
    private String comprador;
    private BigDecimal precoSaco;

}
