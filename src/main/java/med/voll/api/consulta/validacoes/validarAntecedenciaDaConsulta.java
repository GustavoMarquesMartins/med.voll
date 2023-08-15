package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.Controller.ConsultaController;
import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.DTO.DadosDetalhamentoConsulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.infra.Exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class validarAntecedenciaDaConsulta {

    public void validarAntecedenciaConsulta(DadosDetalhamentoConsulta dados) {
        var diferencaEmMinutos = Duration.between(LocalDateTime.now(), dados.data()).toMinutes();
        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException(" O agendamento deve ser realizado com 30 minutos de antecedência");
        }
    }
}


