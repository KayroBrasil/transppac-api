
package com.transppac.transppac_api.dto.equipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EquipeRequestDTO {

    @NotBlank(message = "O nome da equipe é obrigatório.")
    @Size(max = 100, message = "O nome da equipe deve ter no máximo 100 caracteres.")
    private String nome;

    public EquipeRequestDTO() {
    }

    public EquipeRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
