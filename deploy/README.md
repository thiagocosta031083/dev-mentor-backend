# Publicação inicial na VM

Este diretório contém modelos para publicar a API atrás do Nginx. Substitua os placeholders antes de aplicar qualquer configuração.

## Pré-requisitos

- VM Ubuntu com Docker, Docker Compose e Nginx;
- DNS do subdomínio apontando para o IP público da VM;
- firewall permitindo HTTP/HTTPS e SSH conforme a política da VM;
- portas 8080 e 5432 não expostas publicamente.

## Aplicação e PostgreSQL

1. Clone o repositório na VM.
2. Gere o JAR com `./mvnw clean package`.
3. Copie `.env.example` para `.env` e configure uma senha forte.
4. Execute `docker compose up -d --build`.
5. Na própria VM, valide `curl http://127.0.0.1:8080/api/v1/health`.

## Nginx e HTTPS

1. Copie `nginx/dev-mentor.conf.example` para a configuração de sites do Nginx.
2. Substitua `api.seudominio.com` pelo subdomínio real.
3. Valide a configuração com `nginx -t` antes de recarregar o serviço.
4. Emita o certificado TLS com Certbot ou com o provedor DNS escolhido.
5. Confirme externamente o health, Swagger e OpenAPI usando HTTPS.

Não versione o arquivo `.env`, senhas, chaves privadas ou futuros segredos JWT.
