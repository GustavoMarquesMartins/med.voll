package med.voll.api.consulta.Controller;


import med.voll.api.consulta.DTO.DadosAgendamentoConsulta;
import med.voll.api.consulta.DTO.DadosDetalhamentoConsulta;
import med.voll.api.consulta.Service.AgendaDeConsultas;
import med.voll.api.medico.Domain.Especialidade;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest  // Anotação indicando que este é um teste de unidade Spring Boot
@AutoConfigureMockMvc  // Configura automaticamente o MockMvc
@AutoConfigureJsonTesters  // Configura automaticamente JacksonTesters
class ConsultaControllerTest {

    @Autowired  // Injeta MockMvc para simular chamadas HTTP
    private MockMvc mvc;

    @Autowired  // Injeta JacksonTesters para testar a serialização JSON
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired  // Injeta JacksonTesters para testar a serialização JSON
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;

    @MockBean  // Cria um mock do serviço AgendaDeConsultas
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informaçoes estao invalidas")
    @WithMockUser
        // Simula um usuário autenticado para o teste
    void agendarCenario1() throws Exception {
        // Definindo os parâmetros que serão usados no teste
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        // Moldando o comportamento do método "agendar" para o teste
        var detalhamentoConsultaDTO = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        Mockito.when(agendaDeConsultas.agendar(Mockito.any())).thenReturn(detalhamentoConsultaDTO);

        // Simulando uma chamada POST para o endpoint "/consultas"
        var response = mvc.perform(
                        MockMvcRequestBuilders.post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(
                                        new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        // Verificando o código de status da resposta
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Preparando o JSON esperado para a resposta
        var jsonEsperado = dadosDetalhamentoConsulta.write(
                detalhamentoConsultaDTO).getJson();

        // Verificando se o JSON da resposta é igual ao JSON esperado
        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}