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

## ▶️ Como Executar (em breve)

Instruções de execução serão adicionadas conforme a implementação do projeto.

---

## 👨‍💻 Autor

Thiago Costa  
Desenvolvedor Java | Angular  

📎 LinkedIn: https://www.linkedin.com/in/thiago-de-almeida-costa/
