
package com.transppac.transppac_api.dto.usuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Long setorId;
    private String setorNome;
    private Long equipeId;
    private String equipeNome;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String email,
                              Long setorId, String setorNome,
                              Long equipeId, String equipeNome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.setorId = setorId;
        this.setorNome = setorNome;
        this.equipeId = equipeId;
        this.equipeNome = equipeNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    public String getEquipeNome() {
        return equipeNome;
    }

    public void setEquipeNome(String equipeNome) {
        this.equipeNome = equipeNome;
    }
}
