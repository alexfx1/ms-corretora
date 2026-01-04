package com.corretora.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {
    OK("OK"),
    RASCUNHO("RASCUNHO");
    private String status;

}
