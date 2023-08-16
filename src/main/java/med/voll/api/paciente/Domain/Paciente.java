package med.voll.api.paciente.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Domain.Endereco;
import med.voll.api.paciente.DTO.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DTO.DadosCadastroPaciente;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private Boolean status;

    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.status = true;
    }

    public void atulizar(DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        if (dadosAtualizacaoPaciente.nome() != null) {
            this.nome = dadosAtualizacaoPaciente.nome();
        }
        if (dadosAtualizacaoPaciente.telefone() != null) {
            this.telefone = dadosAtualizacaoPaciente.telefone();
        }
        if (dadosAtualizacaoPaciente.dadosEndereco() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizacaoPaciente.dadosEndereco());
        }
    }

    public void inativo() {
        this.status = false;
    }
}
