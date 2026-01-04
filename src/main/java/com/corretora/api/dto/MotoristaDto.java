package com.corretora.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotoristaDto {
    private Long cdMotorista;

    private String cdCpf;

    private String dsNome;

    private String dsPlaca;

    private String dsCidade;

    private String dsEstado;

    private String dsTelefone;

    private String dsContato;

    public MotoristaDto(String cpf, String nome, String placa, String cidade, String estado) {
        this.cdCpf = cpf;
        this.dsNome = nome;
        this.dsPlaca = placa;
        this.dsCidade = cidade;
        this.dsEstado = estado;
    }
}
