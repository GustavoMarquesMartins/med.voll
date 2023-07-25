package med.voll.api.paciente.Controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.DTO.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DTO.DadosCadastroPaciente;
import med.voll.api.paciente.DTO.DadosListagemPacientes;
import med.voll.api.paciente.Domain.Paciente;
import med.voll.api.paciente.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente) {
        pacienteRepository.save(new Paciente(dadosCadastroPaciente));
    }
    @GetMapping
    public Page<DadosListagemPacientes> listagem(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return pacienteRepository.findAllByStatusTrue(pageable).map(DadosListagemPacientes::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizacaoPaciente dadosAtualizacaoPaciente){
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atulizar(dadosAtualizacaoPaciente);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativo();
    }
}