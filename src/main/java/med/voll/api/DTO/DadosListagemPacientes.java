package med.voll.api.DTO;

import med.voll.api.Domain.Especialidade;
import med.voll.api.Domain.Medico;
import med.voll.api.Domain.Paciente;

public record DadosListagemPacientes(String nome, String email, String cpf) {
    public DadosListagemPacientes(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
