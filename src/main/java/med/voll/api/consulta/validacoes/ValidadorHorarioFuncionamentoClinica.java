package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.infra.Exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClina = dados.data().getHour() < 7;
        var depoisFechamentoDaClinica = dados.data().getHour() > 18;

        if (domingo || antesDaAberturaDaClina || depoisFechamentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}


