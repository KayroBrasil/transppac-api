package com.transppac.transppac_api.controller;

import com.transppac.transppac_api.dto.usuario.UsuarioRequestDTO;
import com.transppac.transppac_api.dto.usuario.UsuarioResponseDTO;
import com.transppac.transppac_api.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
        summary = "Cadastrar usuário",
        description = "Cadastra um usuário com senha protegida e associa setor e equipe."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Setor ou equipe não encontrados"),
        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO cadastrar(
            @Valid @RequestBody UsuarioRequestDTO dados) {
        return usuarioService.cadastrar(dados);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listarTodos();
    }
}