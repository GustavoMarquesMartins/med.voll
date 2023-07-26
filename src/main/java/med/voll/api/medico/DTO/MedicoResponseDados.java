package med.voll.api.medico.DTO;

import med.voll.api.endereco.Domain.Endereco;
import med.voll.api.medico.Domain.Especialidade;
import med.voll.api.medico.Domain.Medico;

public record MedicoResponseDados(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {

    public MedicoResponseDados(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }

}