# Dev Mentor – Backend

Backend do sistema **Dev Mentor**, responsável pelas regras de negócio, cálculos de evolução técnica e exposição da API REST.

O sistema foi projetado para ajudar desenvolvedores juniores a planejar estudos, registrar aprendizados e acompanhar sua evolução técnica ao longo do tempo.

---

## 📌 Contexto

Desenvolvedores iniciantes costumam estudar várias tecnologias simultaneamente, mas enfrentam dificuldades para mensurar seu progresso real.

O Dev Mentor resolve esse problema ao combinar:
- Planejamento de conteúdos
- Planejamento de horas de estudo
- Registro de estudos realizados
- Avaliação de nível de domínio técnico
- Cálculo de evolução com base em métricas objetivas

---

## 🎯 Responsabilidades do Backend

- Gerenciar tecnologias e disciplinas
- Gerenciar conteúdos planejados
- Registrar estudos realizados
- Calcular indicadores de evolução técnica
- Expor endpoints REST para consumo pelo frontend

---

## 🛠️ Stack Utilizada

- Java 8
- Spring Boot
- Spring Data JPA
- H2 Database (desenvolvimento)
- PostgreSQL (produção)
- JWT (autenticação simples)

---

## 🏗️ Arquitetura

O backend segue uma arquitetura em camadas:

- Controller: exposição da API REST
- Service: regras de negócio e cálculos de evolução
- Repository: persistência de dados
- Domain: entidades e modelos

Toda a lógica de cálculo da evolução técnica está centralizada na camada de serviço.

---

## 🚧 Status do Projeto

🛠️ MVP V1 em desenvolvimento

Funcionalidades planejadas para a V1:
- Cadastro de tecnologias e disciplinas
- Planejamento de conteúdos
- Planejamento de horas de estudo
- Registro de estudos
- Avaliação de nível de domínio (1 a 4)
- Cálculo automático de evolução técnica
- Dashboard de acompanhamento (via frontend)

---

## ▶️ Como executar

### Desenvolvimento com H2

Pré-requisitos: Java 8 ou superior e acesso à internet no primeiro build.

No Windows:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

No Linux/macOS:

```bash
./mvnw clean test
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Após iniciar a aplicação:

- Health: `http://localhost:8080/api/v1/health`
- Swagger: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Console H2: `http://localhost:8080/h2-console`

### Produção com PostgreSQL

O profile `prod` exige as variáveis `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`. Nenhuma credencial real deve ser versionada.

Para execução com Docker, copie `.env.example` para `.env`, substitua a senha e execute:

```bash
docker compose up -d --build
```

O PostgreSQL não é publicado no host. O backend escuta somente em `127.0.0.1:8080`, para ser exposto por um reverse proxy Nginx.

Os modelos de publicação estão no diretório `deploy/` e a collection inicial do Postman está em `postman/`.

---

## 👨‍💻 Autor

Thiago Costa  
Desenvolvedor Java | Angular  

📎 LinkedIn: https://www.linkedin.com/in/thiago-de-almeida-costa/
