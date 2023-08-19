package med.voll.api.medico.Controller;

import med.voll.api.endereco.DTO.DadosEndereco;
import med.voll.api.endereco.Domain.Endereco;
import med.voll.api.medico.DTO.DadosCadastroMedico;
import med.voll.api.medico.DTO.MedicoResponseDados;
import med.voll.api.medico.Domain.Especialidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @MockBean
    private MedicoController medicoController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;
    @Autowired
    private JacksonTester<MedicoResponseDados> medicoResponseDadosJson;

    @Test
    @DisplayName("quando passado os dados corretamente, deveria ser retornado um DTO com os dados do médico cadastrado")
    @WithMockUser
    void cadastrarCenario1() throws Exception {

        // Criando dados simulados para o teste
        var enderecoDto = new DadosEndereco("Rua das Flores", "Centro", "12345678", "123", "Apartmento 456", "Cidade Exemplo", "SP");
        var endereco = new Endereco("Rua das Flores", "Centro", "12345678", "123", "Apartmento 456", "Cidade Exemplo", "SP");
        var responseMedicoDto = new MedicoResponseDados(null, "Julio", "julio@gmail.com", "123456", "123456", Especialidade.CARDIOLOGIA, endereco);

        // Configurando o comportamento simulado do medicoController.cadastrar
        Mockito.when(medicoController.cadastrar(Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.created(new URI("/medicos/1")).body(responseMedicoDto));

        // Fazendo uma requisição simulada ao endpoint /medicos
        var response = mvc.perform(
                        MockMvcRequestBuilders.post("/medicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosCadastroMedicoJson.write(
                                                new DadosCadastroMedico("Julio", "julio@gmail.com", "123456", "123456", Especialidade.CARDIOLOGIA, enderecoDto)
                                        ).getJson()
                                )
                )
                .andReturn().getResponse();

        // Verificando se o código de resposta é o esperado (status HTTP 201 - CREATED)
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        // Serializando o objeto de resposta esperado para JSON
        var jsonEsperado = medicoResponseDadosJson.write(responseMedicoDto).getJson();

        // Verificando se o conteúdo da resposta é igual ao JSON esperado
        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}
