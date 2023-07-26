package med.voll.api.paciente.DTO;

import med.voll.api.paciente.Domain.Paciente;

public record PacienteResponseDados(Long id, String nome, String email, String cpf) {

    public PacienteResponseDados(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
