package med.voll.api.medico.Domain;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.endereco.Domain.Endereco;
import med.voll.api.medico.DTO.DadosCadastroMedico;
import med.voll.api.medico.DTO.DadosAtualizacaoMedico;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean status;

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.status = true;
    }

    public void atualizar(DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        if (dadosAtualizacaoMedico.nome() != null) {
            this.nome = dadosAtualizacaoMedico.nome();
        }
        if (dadosAtualizacaoMedico.telefone() != null) {
            this.telefone = dadosAtualizacaoMedico.telefone();
        }
        if (dadosAtualizacaoMedico.dadosEndereco() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizacaoMedico.dadosEndereco());
        }
    }

    public void inativo() {
        this.status = false;
    }
}