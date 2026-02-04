# Tabela Tarifária de Água 💧
API REST desenvolvida em Java com Spring Boot para gerenciamento de tabelas tarifárias e cálculo progressivo de consumo de água por categoria e faixas de consumo.

## Pré-requisitos

| Tecnologia | Versão mínima | Links |
|-----------|--------------|---------|
| Java | 21 | [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) |
| Maven | 3.8+ | [Download](https://maven.apache.org/download.cgi) |
| PostgreSQL | 12+ | [Download](https://www.postgresql.org/download/) |

## Configuração do Banco de Dados
Para criar o banco postgres, caso não tenha, utilize:

    CREATE DATABASE postgres;

## Geração de tabelas
### Migrations de Banco de Dados

Este projeto utiliza Flyway para versionamento do schema do banco de dados.

As migrations estão localizadas em:
`src/main/resources/db/migration`

Ao iniciar a aplicação, o Flyway executa automaticamente os scripts SQL.


## Usuário e Senha
Por padrão o usuário é "postgres" e a senha "root". Para trocar, antes de rodar a api, utilize:

    $env:DB_USER="seu_usuario"
e

    $env:DB_PASSWORD="sua_senha"
⚠️ Observação: o PostgreSQL não possui senha padrão. Caso o usuário ou senha configurados não existam no seu ambiente, ajuste-os antes de executar a aplicação.

## URL
Por padrão a aplicação utiliza: 

jdbc:postgresql://localhost:5432/postgres?currentSchema=tabela_tarifaria_api

Caso necessário, sobrescreva utilizando:

    $env:DB_URL="jdbc:postgresql://localhost:5432/postgres?currentSchema=tabela_tarifaria_api"

## Como Executar a Aplicação
Clone o repositório:

    git clone https://github.com/pedrogstrindade/tabela-tarifaria.git
    cd tabela-tarifaria
    
Compile o projeto:

    mvn clean install
Inicie a API:

    mvn spring-boot:run
A API estará disponível em http://localhost:8080.
