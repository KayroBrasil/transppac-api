
package com.transppac.transppac_api.setor;

import com.transppac.transppac_api.setor.dto.SetorRequestDTO;
import com.transppac.transppac_api.setor.dto.SetorResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    private final SetorRepository setorRepository;

    public SetorController(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetorResponseDTO cadastrar(@Valid @RequestBody SetorRequestDTO dados) {
        Setor setor = new Setor();
        setor.setNome(dados.getNome());

        Setor setorSalvo = setorRepository.save(setor);

        return new SetorResponseDTO(
            setorSalvo.getId(),
            setorSalvo.getNome()
        );
    }

    @GetMapping
    public List<SetorResponseDTO> listar() {
        return setorRepository.findAll()
            .stream()
            .map(setor -> new SetorResponseDTO(
                setor.getId(),
                setor.getNome()
            ))
            .toList();
    }
}
