package com.corretora.api.dto.contrato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContratoResponseDto extends ContratoDto {
    private Long cdContrato;
    private LocalDateTime dataInicio;
    private String dsStatus;
}
