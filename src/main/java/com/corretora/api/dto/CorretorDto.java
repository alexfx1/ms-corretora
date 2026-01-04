package com.corretora.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CorretorDto {
    private Long cdCorretor;
    @NonNull
    private String dsNome;
    @NonNull
    private String cdCpf;
    private String dsBanco;
    private String cdAgencia;
    private String cdConta;
    private String dsCidade;
    private String dsEstado;
    private String dsChavePix;
}
