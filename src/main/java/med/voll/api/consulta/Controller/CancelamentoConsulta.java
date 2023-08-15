package med.voll.api.consulta.Controller;

import jakarta.validation.Valid;
import med.voll.api.consulta.DTO.DadosCancelamentoConsulta;
import med.voll.api.consulta.Service.CancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cancelamentos")
public class CancelamentoConsulta {
    @Autowired
    private CancelamentoDeConsulta cancelamento;

    @PostMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        cancelamento.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
