package med.voll.api.paciente.DTO;

import jakarta.validation.constraints.NotNull;
import med.voll.api.medico.DTO.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull Long id,
        String nome,
        String telefone,
        DadosEndereco dadosEndereco) {
}
