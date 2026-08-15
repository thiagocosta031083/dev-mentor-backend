# Dev Mentor API - Documentação Swagger

## 🚀 Visão Geral

A **Dev Mentor API** é um backend para planejamento de estudos e acompanhamento da evolução técnica. Fornece endpoints REST para gerenciar usuários, tecnologias, conteúdos, planejamentos de estudo, registros de estudo, projetos pessoais e métricas de evolução.

## 🔐 Autenticação

A API usa autenticação HTTP Basic Auth. Todos os endpoints (exceto `/health` e Swagger UI) requerem autenticação.

### Credenciais Padrão:

- **Usuário**: `usuario` / `senha123` (Role: USER)
- **Admin**: `admin` / `admin123` (Role: ADMIN, USER)

## 📚 Endpoints Principais

### 1. **Usuários** (`/api/v1/usuarios`)

#### POST - Criar Usuário
```
POST /api/v1/usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com"
}

Response: 200 OK
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "dataCriacao": "2026-08-14T22:00:00"
}
```

#### GET - Obter Usuário por ID
```
GET /api/v1/usuarios/{id}

Response: 200 OK
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com"
}
```

#### PUT - Atualizar Usuário
```
PUT /api/v1/usuarios/{id}
Content-Type: application/json

{
  "nome": "João Silva Atualizado",
  "email": "joao.novo@example.com"
}
```

#### DELETE - Deletar Usuário
```
DELETE /api/v1/usuarios/{id}

Response: 204 No Content
```

---

### 2. **Tecnologias** (`/api/v1/tecnologias`)

#### POST - Criar Tecnologia
```
POST /api/v1/tecnologias
Content-Type: application/json

{
  "usuarioId": 1,
  "nome": "Java",
  "descricao": "Linguagem de programação"
}

Response: 200 OK
{
  "id": 1,
  "nome": "Java",
  "descricao": "Linguagem de programação",
  "status": "ATIVA",
  "usuario": {...}
}
```

#### GET - Listar Tecnologias do Usuário
```
GET /api/v1/tecnologias/usuario/{usuarioId}

Response: 200 OK
[
  {
    "id": 1,
    "nome": "Java",
    "status": "ATIVA"
  },
  {
    "id": 2,
    "nome": "Spring Boot",
    "status": "ATIVA"
  }
]
```

#### PUT - Ativar Tecnologia
```
PUT /api/v1/tecnologias/{id}/ativar

Response: 200 OK
```

#### PUT - Inativar Tecnologia
```
PUT /api/v1/tecnologias/{id}/inativar

Response: 200 OK
```

---

### 3. **Conteúdo Planejado** (`/api/v1/conteudos`)

#### POST - Criar Conteúdo
```
POST /api/v1/conteudos
Content-Type: application/json

{
  "tecnologiaId": 1,
  "titulo": "Collections em Java",
  "descricao": "Aprenda as principais coleções do Java"
}

Response: 200 OK
{
  "id": 1,
  "titulo": "Collections em Java",
  "descricao": "Aprenda as principais coleções do Java",
  "status": "NAO_INICIADO",
  "tecnologia": {...}
}
```

#### GET - Obter Conteúdo
```
GET /api/v1/conteudos/{id}

Response: 200 OK
{
  "id": 1,
  "titulo": "Collections em Java",
  "status": "EM_ESTUDO"
}
```

#### PUT - Iniciar Conteúdo
```
PUT /api/v1/conteudos/{id}/iniciar

Response: 200 OK
{
  "status": "EM_ESTUDO"
}
```

#### PUT - Concluir Conteúdo
```
PUT /api/v1/conteudos/{id}/concluir
Content-Type: application/json

{
  "nivelDominio": "NIVEL_3"
}

Response: 200 OK
{
  "status": "CONCLUIDO",
  "nivelDominio": "NIVEL_3"
}
```

---

### 4. **Planejamento de Estudo** (`/api/v1/planejamentos`)

#### POST - Criar Planejamento
```
POST /api/v1/planejamentos
Content-Type: application/json

{
  "conteudoId": 1,
  "horasPlanejadas": 10,
  "dataInicio": "2026-08-14",
  "dataFim": "2026-08-21"
}

Response: 200 OK
{
  "id": 1,
  "horasPlanejadas": 10,
  "dataInicio": "2026-08-14",
  "dataFim": "2026-08-21",
  "ativo": true
}
```

#### GET - Obter Planejamento
```
GET /api/v1/planejamentos/{id}

Response: 200 OK
{
  "id": 1,
  "horasPlanejadas": 10,
  "conteudo": {...}
}
```

---

### 5. **Registro de Estudo** (`/api/v1/registros-estudo`)

#### POST - Registrar Estudo
```
POST /api/v1/registros-estudo
Content-Type: application/json

{
  "conteudoId": 1,
  "data": "2026-08-14",
  "tempoMinutos": 120,
  "tipo": "PRATICO",
  "observacoes": "Implementei HashMap customizado"
}

Response: 200 OK
{
  "id": 1,
  "data": "2026-08-14",
  "tempoMinutos": 120,
  "tipo": "PRATICO"
}
```

#### GET - Obter Registro
```
GET /api/v1/registros-estudo/{id}

Response: 200 OK
{
  "id": 1,
  "tempoMinutos": 120,
  "tipo": "PRATICO"
}
```

#### GET - Listar por Período
```
GET /api/v1/registros-estudo/periodo/{dataInicio}/{dataFim}

Example: GET /api/v1/registros-estudo/periodo/2026-08-01/2026-08-31

Response: 200 OK
[
  {
    "id": 1,
    "data": "2026-08-14",
    "tempoMinutos": 120,
    "tipo": "PRATICO"
  }
]
```

---

### 6. **Projetos Pessoais** (`/api/v1/projetos`)

#### POST - Criar Projeto
```
POST /api/v1/projetos
Content-Type: application/json

{
  "usuarioId": 1,
  "nome": "E-commerce",
  "stack": "Java 8 + Spring Boot + PostgreSQL",
  "status": "IDEIA",
  "proximoPasso": "Configurar banco de dados"
}

Response: 200 OK
{
  "id": 1,
  "nome": "E-commerce",
  "status": "IDEIA"
}
```

#### GET - Listar Projetos do Usuário
```
GET /api/v1/projetos/usuario/{usuarioId}

Response: 200 OK
[
  {
    "id": 1,
    "nome": "E-commerce",
    "status": "IDEIA"
  }
]
```

#### PUT - Iniciar Projeto
```
PUT /api/v1/projetos/{id}/iniciar

Response: 200 OK
{
  "status": "EM_ANDAMENTO"
}
```

#### PUT - Concluir Projeto
```
PUT /api/v1/projetos/{id}/concluir

Response: 200 OK
{
  "status": "CONCLUIDO"
}
```

---

### 7. **Evolução Técnica** (`/api/v1/evolucao`)

#### GET - Calcular Evolução
```
GET /api/v1/evolucao/{usuarioId}

Response: 200 OK
{
  "usuarioId": 1,
  "evolucaoPercentual": 75.5,
  "nivelProficiencia": "Intermediário",
  "tecnologias": 5,
  "conteudosConcluidos": 12,
  "horasEstudadas": 48
}
```

---

### 8. **Health Check** (`/api/v1/health`)

#### GET - Status da API
```
GET /api/v1/health

Response: 200 OK
{
  "status": "UP",
  "timestamp": "2026-08-14T22:00:00"
}
```

---

## 🌐 Acessar Swagger UI

A documentação interativa Swagger está disponível em:

```
http://localhost:8080/swagger-ui.html
```

## 🔧 Configurações

### application.properties (Padrão - H2 In-Memory)
```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
```

### application-dev.properties (Desenvolvimento - PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dev_mentor
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

### application-prod.properties (Produção - PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

---

## 📦 Stack Tecnológico

- **Java 8**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring Security** - Autenticação HTTP Basic
- **Spring Cache** - Cache Caffeine
- **PostgreSQL** / H2 Database
- **Springdoc OpenAPI** - Swagger/OpenAPI 3.0
- **Maven 3.9.11**

---

## 🚀 Como Executar

### 1. Clonar Repositório
```bash
git clone https://github.com/thiagocosta031083/dev-mentor-backend.git
cd dev-mentor-backend
```

### 2. Compilar
```bash
./mvnw.cmd clean compile
```

### 3. Executar Testes
```bash
./mvnw.cmd test
```

### 4. Iniciar Aplicação
```bash
./mvnw.cmd spring-boot:run
```

A aplicação iniciará em: `http://localhost:8080`

### 5. Com Docker
```bash
docker-compose up
```

---

## 📖 Enums Disponíveis

### StatusTecnologia
- `ATIVA`
- `INATIVA`

### StatusConteudo
- `NAO_INICIADO`
- `EM_ESTUDO`
- `CONCLUIDO`

### StatusProjeto
- `IDEIA`
- `EM_ANDAMENTO`
- `CONCLUIDO`

### NivelDominio
- `NIVEL_1` - Conhecimento teórico inicial
- `NIVEL_2` - Consegue aplicar com ajuda
- `NIVEL_3` - Aplica sozinho
- `NIVEL_4` - Domina e consegue explicar

### TipoEstudo
- `TEORICO`
- `PRATICO`
- `PROJETO`

---

## 📝 Exemplo de Fluxo Completo

1. **Criar Usuário**
```
POST /api/v1/usuarios
{ "nome": "Maria", "email": "maria@example.com" }
```

2. **Criar Tecnologia**
```
POST /api/v1/tecnologias
{ "usuarioId": 1, "nome": "Java", "descricao": "Linguagem" }
```

3. **Criar Conteúdo**
```
POST /api/v1/conteudos
{ "tecnologiaId": 1, "titulo": "Collections", "descricao": "..." }
```

4. **Criar Planejamento**
```
POST /api/v1/planejamentos
{ "conteudoId": 1, "horasPlanejadas": 10, ... }
```

5. **Registrar Estudo**
```
POST /api/v1/registros-estudo
{ "conteudoId": 1, "data": "2026-08-14", "tempoMinutos": 120, ... }
```

6. **Concluir Conteúdo**
```
PUT /api/v1/conteudos/1/concluir
{ "nivelDominio": "NIVEL_3" }
```

7. **Consultar Evolução**
```
GET /api/v1/evolucao/1
```

---

## 🔒 Segurança

- ✅ Autenticação HTTP Basic (Spring Security)
- ✅ Proteção CSRF desabilitada para APIs (REST)
- ✅ Senhas criptografadas com BCrypt
- ✅ Acesso restrito aos endpoints da API

---

## 📊 Caching

- Implementado com **Caffeine Cache**
- Validade: 10 minutos
- Tamanho máximo: 100 itens
- Caches: `usuarios`, `tecnologias`, `conteudos`, `projetos`, `evolucao`

---

## 📞 Contato

**Thiago Costa**  
GitHub: [thiagocosta031083](https://github.com/thiagocosta031083)

---

**Desenvolvido com ❤️ para o Dev Mentor MVP**
