
package com.transppac.transppac_api.equipe;

import com.transppac.transppac_api.equipe.dto.EquipeRequestDTO;
import com.transppac.transppac_api.equipe.dto.EquipeResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final EquipeRepository equipeRepository;

    public EquipeController(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipeResponseDTO cadastrar(@Valid @RequestBody EquipeRequestDTO dados) {
        Equipe equipe = new Equipe();
        equipe.setNome(dados.getNome());

        Equipe equipeSalva = equipeRepository.save(equipe);

        return new EquipeResponseDTO(
            equipeSalva.getId(),
            equipeSalva.getNome()
        );
    }

    @GetMapping
    public List<EquipeResponseDTO> listar() {
        return equipeRepository.findAll()
            .stream()
            .map(equipe -> new EquipeResponseDTO(
                equipe.getId(),
                equipe.getNome()
            ))
            .toList();
    }
}
