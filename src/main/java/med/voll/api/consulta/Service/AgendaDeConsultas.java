package med.voll.api.consulta.Service;

import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.DTO.DadosDetalhamentoConsulta;
import med.voll.api.consulta.Domain.Consulta;
import med.voll.api.consulta.Respository.ConsultaRepository;
import med.voll.api.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.infra.Exception.ValidacaoException;
import med.voll.api.medico.Domain.Medico;
import med.voll.api.medico.Repository.MedicoRepository;
import med.voll.api.paciente.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepositoryRepository;
    @Autowired
    private PacienteRepository pacienteRepositoryRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Transactional
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepositoryRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Paciente com esse id não foi encontrado!");
        }
        if (dados.idMedico() != null && !medicoRepositoryRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Médico com esse id não foi encontrado!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepositoryRepository.findById(dados.idPaciente()).get();
        var medico = validar(dados);
        if (medico==null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");

        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), true);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepositoryRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("especialidade e obrigatoria quando medico nao for escolhido");
        }
        return medicoRepositoryRepository.getMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}