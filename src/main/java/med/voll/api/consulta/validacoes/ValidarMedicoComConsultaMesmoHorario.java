package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.infra.Exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoComConsultaMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiOutraConsultaMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaMesmoHorario) {
            throw new ValidacaoException("Médico já possui uma consulta agendada no mesmo horário ");
        }
    }
}
