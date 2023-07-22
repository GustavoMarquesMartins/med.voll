package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.DTO.DadosListagemMedicos;
import med.voll.api.Domain.Medico;
import med.voll.api.DTO.DadosCadastroMedico;
import med.voll.api.Repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        medicoRepository.save(new Medico(dadosCadastroMedico));
    }
    @GetMapping
    public Page<DadosListagemMedicos> listagem(@PageableDefault(size = 10,sort = {"nome"}) Pageable pageable) {
        return medicoRepository.findAll(pageable).map(DadosListagemMedicos::new);
    }
}
