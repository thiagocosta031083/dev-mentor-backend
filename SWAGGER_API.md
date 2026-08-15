# Contrato da API V1

Base URL local: `http://localhost:8080`

O contrato navegável e sempre atualizado é gerado em `/swagger-ui.html`; o JSON OpenAPI fica em `/v3/api-docs`.

## Endpoints públicos

| Método | Rota | Finalidade |
|---|---|---|
| GET | `/api/v1/health` | Disponibilidade da API |
| POST | `/api/v1/auth/login` | Obter token JWT |

## Endpoints autenticados

| Recurso | Rotas principais |
|---|---|
| Tecnologias | `POST/GET /api/v1/tecnologias`, `GET/PUT /api/v1/tecnologias/{id}`, `PUT .../{id}/ativar`, `PUT .../{id}/inativar` |
| Conteúdos | `POST /api/v1/conteudos`, `GET/PUT .../{id}`, `GET .../tecnologia/{tecnologiaId}`, `PUT .../{id}/iniciar`, `PUT .../{id}/concluir` |
| Planos | `POST /api/v1/planos`, `GET/PUT .../{id}`, `GET .../tecnologia/{tecnologiaId}` |
| Registros | `POST /api/v1/registros`, `GET .../{id}`, `GET .../tecnologia/{tecnologiaId}` |
| Projetos | `POST/GET /api/v1/projetos`, `GET/PUT .../{id}` |
| Dashboard | `GET /api/v1/dashboard/{tecnologiaId}` |

Use `Authorization: Bearer <token>`.

## Valores enumerados

- Tipo de tecnologia: `TECNOLOGIA`, `DISCIPLINA`
- Status da tecnologia: `ATIVA`, `INATIVA`
- Tipo de conteúdo: `CONCEITO`, `PRATICA`, `REVISAO`
- Status do conteúdo: `NAO_INICIADO`, `EM_ANDAMENTO`, `CONCLUIDO`
- Nível de domínio: `NIVEL_1`, `NIVEL_2`, `NIVEL_3`, `NIVEL_4`
- Tipo de estudo: `AULA`, `PRATICA`, `REVISAO`
- Status do projeto: `IDEIA`, `EM_ANDAMENTO`, `CONCLUIDO`

## Erros

Erros usam o formato abaixo; violações de validação incluem o objeto `fields`.

```json
{
  "timestamp": "2026-08-15T12:00:00",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Dados inválidos",
  "path": "/api/v1/tecnologias",
  "fields": {
    "nome": "Nome é obrigatório"
  }
}
```

Respostas usuais: `400` regra/validação, `401` autenticação, `404` recurso inexistente, `409` conflito e `500` erro inesperado.
