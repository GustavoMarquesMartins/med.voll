package med.voll.api.consulta.Respository;

import med.voll.api.consulta.Domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
