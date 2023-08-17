package med.voll.api.consulta.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.consulta.Domain.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull(message = "a consulta e um campo obrigatório") @Valid  @JsonAlias({"id_consulta", "consulta_id", "consulta"}) Long idConsulta,
        @NotNull(message = "motivo do cancelamento e obrigatório") MotivoCancelamento motivo) {
}
