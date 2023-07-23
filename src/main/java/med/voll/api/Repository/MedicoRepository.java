package med.voll.api.Repository;

import med.voll.api.Domain.Medico;
import med.voll.api.Domain.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
  Page<Medico> findAllByStatusTrue(Pageable pageable);
}
