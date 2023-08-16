package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.infra.Exception.ValidacaoException;
import med.voll.api.paciente.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;
    public void validar(DadosAgendamentoConsulta dados) {
        var pacicenteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacicenteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo");
        }
    }
}
