package med.voll.api.Controller;

import med.voll.api.MedicoDTO.DadosCadastroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @PostMapping
    public void cadastrar(@RequestBody String json) {
        System.out.println(json);
    }

    public void cadastrar(@RequestBody DadosCadastroMedico dadosCadastroMedico) {
        System.out.println(dadosCadastroMedico);
    }
}
