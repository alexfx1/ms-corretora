package com.corretora.api.dto.contrato;

import com.corretora.api.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FilterDto(
        Integer cod,
        StatusEnum status,
        String client,
        String vendedor,
        String mercadoria,
        BigDecimal preco,
        String dtPeriod,
        Boolean ascDesc
) {
}
