package com.thiagocosta.devmentor.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class DevMentorBackendApplicationTests {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Test
    void contextLoads() {}

    @Test
    void deveRetornarHealthPublico() throws Exception {
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.application").value("Dev Mentor Backend"));
    }

    @Test
    void deveDisponibilizarDocumentacaoOpenApi() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Dev Mentor API"))
                .andExpect(jsonPath("$.info.version").value("v1"));
    }

    @Test
    void deveExigirTokenNosRecursosProtegidos() throws Exception {
        mockMvc.perform(get("/api/v1/tecnologias"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void devePadronizarErrosDeValidacao() throws Exception {
        String token = login();
        mockMvc.perform(
                        post("/api/v1/tecnologias")
                                .header("Authorization", bearer(token))
                                .contentType("application/json")
                                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.nome").exists());
    }

    @Test
    void deveRejeitarSenhaIncorretaEUsuarioInexistente() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType("application/json")
                                .content(
                                        "{\"email\":\"thiago@devmentor.local\",\"senha\":\"incorreta\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("INVALID_CREDENTIALS"));
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType("application/json")
                                .content(
                                        "{\"email\":\"naoexiste@devmentor.local\",\"senha\":\"qualquer\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("INVALID_CREDENTIALS"));
    }

    @Test
    void deveRejeitarTokenInvalido() throws Exception {
        mockMvc.perform(get("/api/v1/tecnologias").header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveExecutarFluxoCompletoDaV1() throws Exception {
        String token = login();
        long tecnologiaId =
                id(
                        postJson(
                                "/api/v1/tecnologias",
                                token,
                                "{\"nome\":\"Java Integration\",\"tipo\":\"TECNOLOGIA\",\"descricao\":\"Backend\",\"cargaHorariaPlanejada\":20}"));

        mockMvc.perform(
                        get("/api/v1/tecnologias/{id}", tecnologiaId)
                                .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ATIVA"));

        long conteudoId =
                id(
                        postJson(
                                "/api/v1/conteudos",
                                token,
                                "{\"tecnologiaId\":"
                                        + tecnologiaId
                                        + ",\"titulo\":\"Spring\",\"tipo\":\"PRATICA\",\"peso\":2}"));
        postJson(
                "/api/v1/conteudos",
                token,
                "{\"tecnologiaId\":"
                        + tecnologiaId
                        + ",\"titulo\":\"JPA\",\"tipo\":\"CONCEITO\",\"peso\":1}");
        mockMvc.perform(
                        put("/api/v1/conteudos/{id}/concluir", conteudoId)
                                .header("Authorization", bearer(token))
                                .contentType("application/json")
                                .content("{\"nivelDominio\":\"NIVEL_4\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONCLUIDO"));

        LocalDate hoje = LocalDate.now();
        postJson(
                "/api/v1/planos",
                token,
                "{\"tecnologiaId\":"
                        + tecnologiaId
                        + ",\"dataInicio\":\""
                        + hoje.minusDays(5)
                        + "\",\"dataFim\":\""
                        + hoje.plusDays(5)
                        + "\",\"horasPlanejadasTotais\":20,\"horasSemanais\":10,\"observacao\":\"Plano V1\"}");
        postJson(
                "/api/v1/registros",
                token,
                "{\"tecnologiaId\":"
                        + tecnologiaId
                        + ",\"conteudoId\":"
                        + conteudoId
                        + ",\"data\":\""
                        + hoje
                        + "\",\"tipo\":\"PRATICA\",\"tempoMinutos\":600,\"observacoes\":\"API\"}");
        postJson(
                "/api/v1/projetos",
                token,
                "{\"nome\":\"Dev Mentor\",\"stack\":\"Java\",\"status\":\"EM_ANDAMENTO\",\"proximoPasso\":\"Testes\",\"tecnologiaId\":"
                        + tecnologiaId
                        + "}");

        mockMvc.perform(
                        get("/api/v1/dashboard/{id}", tecnologiaId)
                                .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudosPlanejados").value(2))
                .andExpect(jsonPath("$.conteudosConcluidos").value(1))
                .andExpect(jsonPath("$.cobertura").value(50.0))
                .andExpect(jsonPath("$.esforco").value(50.0))
                .andExpect(jsonPath("$.pratica").value(100.0))
                .andExpect(jsonPath("$.evolucao").value(65.0))
                .andExpect(jsonPath("$.status").value("ACIMA_DO_ESPERADO"));

        mockMvc.perform(
                        get("/api/v1/conteudos/tecnologia/{id}", tecnologiaId)
                                .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(
                        get("/api/v1/planos/tecnologia/{id}", tecnologiaId)
                                .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        mockMvc.perform(
                        get("/api/v1/registros/tecnologia/{id}", tecnologiaId)
                                .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        mockMvc.perform(get("/api/v1/projetos").header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    private String login() throws Exception {
        MvcResult result =
                mockMvc.perform(
                                post("/api/v1/auth/login")
                                        .contentType("application/json")
                                        .content(
                                                "{\"email\":\"thiago@devmentor.local\",\"senha\":\"devmentor123\"}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token").isNotEmpty())
                        .andReturn();
        return objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("token")
                .asText();
    }

    private JsonNode postJson(String path, String token, String body) throws Exception {
        MvcResult result =
                mockMvc.perform(
                                post(path)
                                        .header("Authorization", bearer(token))
                                        .contentType("application/json")
                                        .content(body))
                        .andExpect(status().isCreated())
                        .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private long id(JsonNode response) {
        return response.get("id").asLong();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
