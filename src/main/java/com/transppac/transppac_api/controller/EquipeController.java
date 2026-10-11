package com.transppac.transppac_api.controller;

import com.transppac.transppac_api.dto.equipe.EquipeRequestDTO;
import com.transppac.transppac_api.dto.equipe.EquipeResponseDTO;
import com.transppac.transppac_api.service.EquipeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipeResponseDTO cadastrar(
            @Valid @RequestBody EquipeRequestDTO dados) {
        return equipeService.cadastrar(dados);
    }

    @GetMapping
    public List<EquipeResponseDTO> listar() {
        return equipeService.listarTodos();
    }
}