package med.voll.api.Controller;

import jakarta.validation.Valid;
 import med.voll.api.DTO.DadosListagemPacientes;
import med.voll.api.Domain.Paciente;
import med.voll.api.DTO.DadosCadastroPaciente;
import med.voll.api.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<DadosListagemPacientes> listagem(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable){
        return pacienteRepository.findAll(pageable).map(DadosListagemPacientes::new);
    }
}
