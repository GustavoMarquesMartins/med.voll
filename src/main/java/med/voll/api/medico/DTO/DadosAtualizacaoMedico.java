package med.voll.api.medico.DTO;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DTO.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco dadosEndereco) {
}
