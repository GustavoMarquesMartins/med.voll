package med.voll.api.DTO;

import med.voll.api.Domain.Especialidade;
import med.voll.api.Domain.Medico;

public record DadosListagemMedicos(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListagemMedicos(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
