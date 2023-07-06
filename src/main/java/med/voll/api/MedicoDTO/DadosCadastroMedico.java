package med.voll.api.MedicoDTO;

import med.voll.api.EnderecoDTO.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, DadosEndereco endereco) {
}