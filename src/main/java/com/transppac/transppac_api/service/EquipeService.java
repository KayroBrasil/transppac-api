package com.transppac.transppac_api.service;

import com.transppac.transppac_api.dto.equipe.EquipeRequestDTO;
import com.transppac.transppac_api.dto.equipe.EquipeResponseDTO;
import com.transppac.transppac_api.model.Equipe;
import com.transppac.transppac_api.repository.EquipeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public EquipeResponseDTO cadastrar(EquipeRequestDTO dados) {
        Equipe equipe = new Equipe();
        equipe.setNome(dados.getNome());

        Equipe equipeSalva = equipeRepository.save(equipe);

        return converterParaResposta(equipeSalva);
    }

    public List<EquipeResponseDTO> listarTodos() {
        return equipeRepository.findAll()
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    private EquipeResponseDTO converterParaResposta(Equipe equipe) {
        return new EquipeResponseDTO(
                equipe.getId(),
                equipe.getNome()
        );
    }
}