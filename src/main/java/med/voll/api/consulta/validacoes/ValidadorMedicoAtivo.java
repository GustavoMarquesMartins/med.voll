package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.infra.Exception.ValidacaoException;
import med.voll.api.medico.Repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }
        var status = medicoRepository.findAtivoById(dados.idMedico());
        if (!status) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo");
        }
    }
}
