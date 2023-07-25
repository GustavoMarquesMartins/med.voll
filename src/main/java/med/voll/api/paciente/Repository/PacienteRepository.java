package med.voll.api.paciente.Repository;

import med.voll.api.paciente.Domain.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByStatusTrue(Pageable pageable);
}
