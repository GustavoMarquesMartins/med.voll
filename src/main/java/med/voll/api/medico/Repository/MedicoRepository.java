package med.voll.api.medico.Repository;

import med.voll.api.medico.Domain.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
  Page<Medico> findAllByStatusTrue(Pageable pageable);
}
