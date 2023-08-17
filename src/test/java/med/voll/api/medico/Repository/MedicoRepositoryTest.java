package med.voll.api.medico.Repository;

import med.voll.api.consulta.Domain.Consulta;
import med.voll.api.endereco.DTO.DadosEndereco;
import med.voll.api.medico.DTO.DadosCadastroMedico;
import med.voll.api.medico.Domain.Especialidade;
import med.voll.api.medico.Domain.Medico;
import med.voll.api.paciente.DTO.DadosCadastroPaciente;
import med.voll.api.paciente.Domain.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void getMedicoAleatorioLivreNaDataCenario1() {

        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@voll.med", "000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoReturn = medicoRepository.getMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoReturn).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void getMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoReturn = medicoRepository.getMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoReturn).isEqualTo(medicoReturn);
    }

    @Test
    @DisplayName("Status do medico deveria ser ativo")
    void getMedicoAleatorioLivreNaDataCenario3() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoReturn = medicoRepository.getMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoReturn.getStatus()).isEqualTo(true);
    }

    @Test
    @DisplayName("deveria retornar um medico com a especialidade passada")
    void getMedicoAleatorioLivreNaDataCenario4() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoReturn = medicoRepository.getMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoReturn.getEspecialidade()).isEqualTo(Especialidade.CARDIOLOGIA);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, true));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(nome, email, "61999999999", crm, especialidade, dadosEndereco());
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(nome, email, "61999999999", cpf, dadosEndereco());
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", null, null);
    }
}