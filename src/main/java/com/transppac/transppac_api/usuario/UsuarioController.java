package com.transppac.transppac_api.usuario;

import com.transppac.transppac_api.equipe.Equipe;
import com.transppac.transppac_api.equipe.EquipeRepository;
import com.transppac.transppac_api.setor.Setor;
import com.transppac.transppac_api.setor.SetorRepository;
import com.transppac.transppac_api.usuario.dto.UsuarioRequestDTO;
import com.transppac.transppac_api.usuario.dto.UsuarioResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final EquipeRepository equipeRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            SetorRepository setorRepository,
            EquipeRepository equipeRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.equipeRepository = equipeRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Operation(summary = "Cadastrar usuário",
        description = "Cadastra um usuário com senha protegida e associa setor e equipe.")
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
                if (usuarioRepository.existsByEmail(dados.getEmail())) {
    throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Já existe um usuário cadastrado com este e-mail."
    );
}

        Setor setor = setorRepository.findById(dados.getSetorId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Setor não encontrado."
            ));

        Equipe equipe = null;

        if (dados.getEquipeId() != null) {
            equipe = equipeRepository.findById(dados.getEquipeId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Equipe não encontrada."
                ));
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

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
            .stream()
            .map(this::converterParaResposta)
            .toList();
    }

    private UsuarioResponseDTO converterParaResposta(Usuario usuario) {
        Long setorId = usuario.getSetor().getId();
        String setorNome = usuario.getSetor().getNome();

        Long equipeId = usuario.getEquipe() != null
            ? usuario.getEquipe().getId()
            : null;

        String equipeNome = usuario.getEquipe() != null
            ? usuario.getEquipe().getNome()
            : null;

        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            setorId,
            setorNome,
            equipeId,
            equipeNome
        );
    }
}
