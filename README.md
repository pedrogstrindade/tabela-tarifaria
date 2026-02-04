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

## Como Executar:
Certifique-se de que o PostgreSQL está rodando.

Clone o repositório:

    git clone https://github.com/pedrogstrindade/tabela-tarifaria.git
    cd tabela-tarifaria
Execute a aplicação:

    mvn clean install
    mvn spring-boot:run
    
## Testando a API
### Você pode testar os endpoints utilizando o Swagger UI integrado: [Swagger-UI](http://localhost:8080/swagger-ui/index.html)

### Exemplo de Requisição (POST)
Endpoint: /api/tabelas-tarifarias

Payload Completo (4 Categorias):

JSON
```
{
  "nomeTabelaTarifaria": "Tabela Progressiva Estadual - Edição Fevereiro 2026",
  "dataVigencia": "03/02/2026",
  "categoriasRelacao": [
    {
      "categoria": "PARTICULAR",
      "faixasConsumo": [
        { "inicio": 0, "fim": 10, "valorUnitario": 5.80 },
        { "inicio": 11, "fim": 15, "valorUnitario": 7.20 },
        { "inicio": 16, "fim": 20, "valorUnitario": 9.50 },
        { "inicio": 21, "fim": 30, "valorUnitario": 12.80 },
        { "inicio": 31, "fim": 999999, "valorUnitario": 18.00 }
      ]
    },
    {
      "categoria": "COMERCIAL",
      "faixasConsumo": [
        { "inicio": 0, "fim": 15, "valorUnitario": 14.50 },
        { "inicio": 16, "fim": 30, "valorUnitario": 19.80 },
        { "inicio": 31, "fim": 50, "valorUnitario": 26.40 },
        { "inicio": 51, "fim": 100, "valorUnitario": 35.00 },
        { "inicio": 101, "fim": 999999, "valorUnitario": 48.50 }
      ]
    },
    {
      "categoria": "INDUSTRIAL",
      "faixasConsumo": [
        { "inicio": 0, "fim": 50, "valorUnitario": 38.00 },
        { "inicio": 51, "fim": 150, "valorUnitario": 52.00 },
        { "inicio": 151, "fim": 300, "valorUnitario": 68.50 },
        { "inicio": 301, "fim": 500, "valorUnitario": 85.00 },
        { "inicio": 501, "fim": 999999, "valorUnitario": 110.00 }
      ]
    },
    {
      "categoria": "PÚBLICO",
      "faixasConsumo": [
        { "inicio": 0, "fim": 100, "valorUnitario": 10.00 },
        { "inicio": 101, "fim": 500, "valorUnitario": 15.00 },
        { "inicio": 501, "fim": 1000, "valorUnitario": 22.50 },
        { "inicio": 1001, "fim": 999999, "valorUnitario": 30.00 }
      ]
    }
  ]
}
    
    
