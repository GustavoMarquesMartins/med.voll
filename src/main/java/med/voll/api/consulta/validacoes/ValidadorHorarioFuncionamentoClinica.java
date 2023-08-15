package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.infra.Exception.ValidacaoException;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamentoClinica {

    public void validadadorHorarioFuncionamentoClinica(DadosAgendamentoConsulta dados) {

        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClina = dados.data().getHour() < 7;
        var depoisFechamentoDaClinica = dados.data().getHour() > 18;

        if(domingo || antesDaAberturaDaClina || depoisFechamentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}


