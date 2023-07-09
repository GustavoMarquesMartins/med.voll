package med.voll.api.Repository;

import med.voll.api.Domain.Medico;
import med.voll.api.Domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
