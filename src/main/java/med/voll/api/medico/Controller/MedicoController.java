package med.voll.api.medico.Controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DTO.DadosCadastroMedico;
import med.voll.api.medico.Repository.MedicoRepository;
import med.voll.api.medico.DTO.DadosAtualizacaoMedico;
import med.voll.api.medico.DTO.DadosListagemMedicos;
import med.voll.api.medico.Domain.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        medicoRepository.save(new Medico(dadosCadastroMedico));
    }

    @GetMapping
    public Page<DadosListagemMedicos> listagem(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return medicoRepository.findAllByStatusTrue(pageable).map(DadosListagemMedicos::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        var medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizar(dadosAtualizacaoMedico);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.inativo();
    }
}
