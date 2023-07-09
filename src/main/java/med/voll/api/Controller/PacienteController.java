package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.Domain.Paciente;
import med.voll.api.MedicoDTO.DadosCadastroMedico;
import med.voll.api.MedicoDTO.DadosCadastroPaciente;
import med.voll.api.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente) {
        pacienteRepository.save(new Paciente(dadosCadastroPaciente));
    }
}
