package com.transppac.transppac_api.controller;

import com.transppac.transppac_api.dto.setor.SetorRequestDTO;
import com.transppac.transppac_api.dto.setor.SetorResponseDTO;
import com.transppac.transppac_api.service.SetorService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetorResponseDTO cadastrar(
            @Valid @RequestBody SetorRequestDTO dados) {
        return setorService.cadastrar(dados);
    }

    @GetMapping
    public List<SetorResponseDTO> listar() {
        return setorService.listarTodos();
    }
}