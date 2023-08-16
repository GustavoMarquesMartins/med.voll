package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.infra.Exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteNaoTemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;
    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsulta) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada no mesmo dia ");
        }
    }
}
