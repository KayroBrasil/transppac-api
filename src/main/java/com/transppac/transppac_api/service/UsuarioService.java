package com.transppac.transppac_api.service;

import com.transppac.transppac_api.dto.usuario.UsuarioRequestDTO;
import com.transppac.transppac_api.dto.usuario.UsuarioResponseDTO;
import com.transppac.transppac_api.model.Equipe;
import com.transppac.transppac_api.model.Setor;
import com.transppac.transppac_api.model.Usuario;
import com.transppac.transppac_api.repository.EquipeRepository;
import com.transppac.transppac_api.repository.SetorRepository;
import com.transppac.transppac_api.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final EquipeRepository equipeRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            SetorRepository setorRepository,
            EquipeRepository equipeRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.equipeRepository = equipeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dados) {
        if (usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um usuário cadastrado com este e-mail.");
        }

        Setor setor = setorRepository.findById(dados.getSetorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Setor não encontrado."));

        Equipe equipe = null;

        if (dados.getEquipeId() != null) {
            equipe = equipeRepository.findById(dados.getEquipeId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Equipe não encontrada."));
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setSetor(setor);
        usuario.setEquipe(equipe);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return converterParaResposta(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    private UsuarioResponseDTO converterParaResposta(Usuario usuario) {
        UsuarioResponseDTO resposta = new UsuarioResponseDTO();

        resposta.setId(usuario.getId());
        resposta.setNome(usuario.getNome());
        resposta.setEmail(usuario.getEmail());
        resposta.setSetorId(usuario.getSetor().getId());
        resposta.setSetorNome(usuario.getSetor().getNome());

        if (usuario.getEquipe() != null) {
            resposta.setEquipeId(usuario.getEquipe().getId());
            resposta.setEquipeNome(usuario.getEquipe().getNome());
        }

        return resposta;
    }
}