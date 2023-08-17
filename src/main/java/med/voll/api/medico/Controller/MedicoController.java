package med.voll.api.medico.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.medico.DTO.DadosCadastroMedico;
import med.voll.api.medico.DTO.MedicoResponseDados;
import med.voll.api.medico.Repository.MedicoRepository;
import med.voll.api.medico.DTO.DadosAtualizacaoMedico;
import med.voll.api.medico.DTO.DadosListagemMedicos;
import med.voll.api.medico.Domain.Medico;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoResponseDados(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listagem(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = medicoRepository.findAllByStatusTrue(pageable).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        var medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizar(dadosAtualizacaoMedico);
        return ResponseEntity.ok(new MedicoResponseDados(medico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.inativo();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity getMedico(@PathVariable Long id ){
        var medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new MedicoResponseDados(medico));
    }
}
