# Dev Mentor Backend V1

API REST para organizar tecnologias, conteúdos, planos, registros de estudo e projetos pessoais, com um dashboard objetivo de evolução técnica.

## Estado da V1

A V1 está implementada localmente com:

- autenticação stateless por JWT e usuário único persistido;
- DTOs, Bean Validation e respostas de erro padronizadas;
- tecnologias/disciplinas, conteúdos, planos, registros e projetos;
- níveis de domínio de 1 a 4;
- dashboard com cobertura, esforço, prática ponderada e comparação com o progresso esperado;
- H2 no profile `dev`, PostgreSQL no profile `prod` e migrations Flyway;
- Swagger/OpenAPI, collection Postman, Docker Compose, Nginx e CI.

## Stack

- Java 25
- Spring Boot 4.1.0 e Spring Framework 7
- Spring Web, Data JPA, Security e Validation
- JWT (JJWT), Flyway e Springdoc OpenAPI
- H2 para desenvolvimento e PostgreSQL 18 para produção

## Executar localmente

Pré-requisitos: Java 25 e acesso à internet no primeiro build. O Maven Wrapper usa Maven 3.9.15.

Windows:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Linux/macOS:

```bash
./mvnw clean test
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

No profile `dev`, o usuário inicial é:

- e-mail: `thiago@devmentor.local`
- senha: `devmentor123`

Essas credenciais são somente locais. O profile de produção exige credenciais via ambiente.

Serviços locais:

- Health: `http://localhost:8080/api/v1/health`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Autenticação

Faça `POST /api/v1/auth/login`:

```json
{
  "email": "thiago@devmentor.local",
  "senha": "devmentor123"
}
```

Envie o token retornado nas demais requisições:

```text
Authorization: Bearer SEU_TOKEN
```

Health, login e documentação OpenAPI são públicos. Todos os demais endpoints exigem JWT.

## Cálculo da evolução

```text
cobertura = conteúdos concluídos / conteúdos planejados * 100
esforço = min(horas realizadas / horas planejadas * 100, 100)
prática = média do nível (1..4 convertido para percentual), ponderada pelo peso
evolução = cobertura * 0,40 + esforço * 0,30 + prática * 0,30
```

O resultado é comparado ao percentual temporal do plano ativo. Uma tolerância de 5 pontos classifica a evolução como `ABAIXO_DO_ESPERADO`, `DENTRO_DO_ESPERADO` ou `ACIMA_DO_ESPERADO`.

## Produção com Docker

```bash
cp .env.example .env
# edite todos os valores e use senhas/chave JWT fortes
docker compose up -d --build
```

Ao atualizar uma instalação existente do PostgreSQL 14 para o 18, faça backup e migre os dados com `pg_upgrade` ou dump/restore antes de reutilizar o volume. O PostgreSQL 18 usa o novo volume em `/var/lib/postgresql`.

O PostgreSQL fica apenas na rede interna e o backend é publicado em `127.0.0.1:8080`, pronto para o reverse proxy Nginx. Consulte [deploy/README.md](deploy/README.md).

## Qualidade

```bash
./mvnw clean verify
```

Os testes validam o contexto, Flyway, health, OpenAPI, segurança JWT, fluxo completo dos recursos, regras do plano e fórmula de evolução. A mesma verificação roda no GitHub Actions.

## Padrões de código

O projeto usa EditorConfig para UTF-8, finais de linha LF, espaços em branco consistentes
e indentação Java de 4 espaços. O Spotless 3.9.0 aplica `google-java-format` 1.36.0 no
estilo AOSP, organiza imports, remove espaços finais e garante uma linha final. A etapa
`spotless:check` também está vinculada à fase Maven `verify`.

```bash
# Formatar o Java
./mvnw spotless:apply

# Verificar formatação sem alterar arquivos
./mvnw spotless:check

# Executar testes e todas as verificações
./mvnw clean verify
```

No IntelliJ IDEA, habilite EditorConfig em `Settings > Editor > Code Style`, use
`Code > Reformat Code` para formatação básica e `Code > Optimize Imports` para imports.
O resultado determinístico oficial é o gerado por `spotless:apply`; os comandos Maven
podem ser executados pela janela Maven ou pelo terminal integrado. No VS Code, instale
EditorConfig e o Extension Pack for Java; o Prettier não deve ser usado em arquivos Java.

## Recursos de apoio

- Contrato resumido: [SWAGGER_API.md](SWAGGER_API.md)
- Collection: [postman/Dev-Mentor-API-V1.postman_collection.json](postman/Dev-Mentor-API-V1.postman_collection.json)
- Deploy: [deploy/README.md](deploy/README.md)
