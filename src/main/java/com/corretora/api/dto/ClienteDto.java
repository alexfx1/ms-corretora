package com.corretora.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDto {
    private Long cdCliente;

    private String dsNome;

    private String cdCpfCnpj;

    private String dsEndereco;

    private String cdCep;

    private String dsCidade;

    private String dsEstado;

    private String dsIns;

    private String dsBanco;

    private String cdAgencia;

    private String cdConta;

    private String dsChavePix;

    private String dsTelefone;

    private String dsContato;
}
