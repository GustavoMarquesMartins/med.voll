package med.voll.api.consulta.Service;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.DTO.DadosCancelamentoConsulta;
import med.voll.api.consulta.Domain.Consulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.infra.Exception.ValidacaoException;
import med.voll.api.medico.Domain.Medico;
import med.voll.api.medico.Repository.MedicoRepository;
import med.voll.api.paciente.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepositoryRepository;
    @Autowired
    private PacienteRepository pacienteRepositoryRepository;

    @Transactional
    public void agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepositoryRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id nao encontrado");
        }
        if (dados.idMedico() != null && !medicoRepositoryRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id nao encontrado");
        }

        var paciente = pacienteRepositoryRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepositoryRepository.findById(dados.idMedico()).get();
        var consulta = Consulta.builder().paciente(paciente).medico(medico).data(dados.data()).status(true).build();
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepositoryRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("especialidade e obrigatoria quando medico nao for escolhido");
        }
        return medicoRepositoryRepository.getMedicoAleatorioLivreNaData(dados.especialidade());
    }
}
