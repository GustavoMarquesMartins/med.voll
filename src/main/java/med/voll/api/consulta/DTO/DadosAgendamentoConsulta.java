package med.voll.api.consulta.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.medico.Domain.Especialidade;

import java.time.LocalDateTime;


public record DadosAgendamentoConsulta(@JsonAlias({"id_medico", "medico_id", "medicoid"}) Long idMedico,
                                       @NotNull @JsonAlias({"id_paciente", "paciente_id", "pacienteid"}) Long idPaciente,

                                       @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime data,
                                       Especialidade especialidade) {
}
