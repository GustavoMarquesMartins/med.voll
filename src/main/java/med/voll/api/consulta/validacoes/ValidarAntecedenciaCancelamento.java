package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.DTO.DadosCancelamentoConsulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.infra.Exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarAntecedenciaCancelamento {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validarAntecedenciaCancelamento(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        if(!consulta.isStatus()){
            throw new ValidacaoException("A consulta já cancelada");
        }
        var diff = Duration.between(LocalDateTime.now(), consulta.getData());
        if (diff.toHours() < 24) {
            throw new ValidacaoException("O cancelamento deve ser feito com 24 horas de antecedência");
        }
    }
}


