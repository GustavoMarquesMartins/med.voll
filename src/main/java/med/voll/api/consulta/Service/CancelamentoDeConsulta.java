package med.voll.api.consulta.Service;

import med.voll.api.consulta.DTO.DadosCancelamentoConsulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.consulta.validacoes.ValidarAntecedenciaCancelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private ValidarAntecedenciaCancelamento cancelamento;

    @Transactional
    public void cancelar(DadosCancelamentoConsulta dados) {
        cancelamento.validarAntecedenciaCancelamento(dados);
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar();
    }
}
