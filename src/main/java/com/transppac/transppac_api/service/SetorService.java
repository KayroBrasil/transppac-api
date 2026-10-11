package com.transppac.transppac_api.service;

import com.transppac.transppac_api.dto.setor.SetorRequestDTO;
import com.transppac.transppac_api.dto.setor.SetorResponseDTO;
import com.transppac.transppac_api.model.Setor;
import com.transppac.transppac_api.repository.SetorRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public SetorResponseDTO cadastrar(SetorRequestDTO dados) {
        Setor setor = new Setor();
        setor.setNome(dados.getNome());

        Setor setorSalvo = setorRepository.save(setor);

        return converterParaResposta(setorSalvo);
    }

    public List<SetorResponseDTO> listarTodos() {
        return setorRepository.findAll()
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    private SetorResponseDTO converterParaResposta(Setor setor) {
        return new SetorResponseDTO(
                setor.getId(),
                setor.getNome()
        );
    }
}