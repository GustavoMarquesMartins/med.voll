package med.voll.api.paciente.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.paciente.DTO.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DTO.DadosCadastroPaciente;
import med.voll.api.paciente.DTO.DadosListagemPacientes;
import med.voll.api.paciente.DTO.PacienteResponseDados;
import med.voll.api.paciente.Domain.Paciente;
import med.voll.api.paciente.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente, UriComponentsBuilder uriComponentsBuilder) {
        var paciente = pacienteRepository.save(new Paciente(dadosCadastroPaciente));
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(new PacienteResponseDados(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacientes>> listagem(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = pacienteRepository.findAllByStatusTrue(pageable).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atulizar(dadosAtualizacaoPaciente);
        return ResponseEntity.ok(new PacienteResponseDados(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativo();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getPaciente(@PathVariable Long id) {
        var peciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new PacienteResponseDados(peciente));
    }
}