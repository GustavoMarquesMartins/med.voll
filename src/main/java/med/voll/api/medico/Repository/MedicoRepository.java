package med.voll.api.medico.Repository;

import med.voll.api.medico.Domain.Especialidade;
import med.voll.api.medico.Domain.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByStatusTrue(Pageable pageable);

    @Query("""
            select m from Medico m 
            where 
            m.status = true
            and 
            m.especialidade = :especialidade
            and
             m.id not in(
             select c.medico.id from Consulta c
             where
             c.data =:data 
             )
            order by rand()
            limit 1
            """)
    Medico getMedicoAleatorioLivreNaData(Especialidade especialidade);
}
