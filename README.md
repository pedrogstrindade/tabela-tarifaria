# Tabela Tarifária de Água 💧
### API REST desenvolvida em Java + Spring Boot para gerenciamento de tabelas tarifárias de água e cálculo progressivo de consumo por categoria e faixas.

## ⚙️ Funcionalidades
- Cadastro de tabelas tarifárias com múltiplas categorias
- Definição de faixas progressivas de consumo
- Cálculo automático e detalhado de faturamento
- Persistência em PostgreSQL
- Versionamento de schema com Flyway
- Documentação interativa com Swagger UI

⚠️ `A API não possui endpoint de Update para garantir a integridade histórica das cobranças. Novas tarifas devem ser inseridas como novas tabelas com novas datas de vigência.`

## 🖥️ Tecnologias Principais (core)
- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- Hibernate (via JPA)
- Flyway (versionamento de schema)
- PostgreSQL

## Estrutura do projeto
```
src/main/java/br/com/tabela_tarifaria_api
├── constants           # Constantes usadas no projeto
├── controller          # Controladores REST
├── dto                 # Objetos de transferência de dados
├── exception           # Tratamento de exceções customizadas
├── model               # Entidades do banco de dados
├── repository          # Interfaces JPA para acesso ao banco
├── service             # Regras de negócio (camada de serviço)

```
## 📋 Pré-requisitos

| Tecnologia | Versão mínima | Links |
|-----------|--------------|---------|
| Java | 21 | [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) |
| Maven | 3.8+ | [Download](https://maven.apache.org/download.cgi) |
| PostgreSQL | 12+ | [Download](https://www.postgresql.org/download/) |

## 🗄️ Configuração do Banco de Dados
Para criar o banco postgres, caso não tenha, utilize:

    CREATE DATABASE postgres;

## Geração de tabelas
### 🔄 Migrations (Flyway)

Este projeto utiliza Flyway para versionamento do schema do banco de dados.

As migrations estão localizadas em:
`src/main/resources/db/migration`

Ao iniciar a aplicação, o Flyway executa automaticamente os scripts SQL.


## 🔐 Usuário e Senha
Por padrão:

- Usuário: `postgres`

- Senha: `root`

Para sobrescrever via variável de ambiente (PowerShell):

```
$env:DB_USER="seu_usuario"
$env:DB_PASSWORD="sua_senha"
```
Ou sobrescreva em `src/main/resources/application.properties`:
<details>
    <summary>application.properties</summary>
    
 ```
    spring.application.name=tabela-tarifaria-api

    spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/postgres}
    spring.datasource.username=${DB_USER:USUARIO} ⬅️
    spring.datasource.password=${DB_PASSWORD:SENHA} ⬅️
    spring.datasource.driver-class-name=org.postgresql.Driver

    spring.jpa.hibernate.ddl-auto=none
    spring.flyway.enabled=true
    spring.flyway.schemas=tabela_tarifaria_api
    spring.flyway.create-schemas=true

    spring.jpa.show-sql=true 
    spring.jpa.properties.hibernate.format_sql=true
    
```
</details>


⚠️ Observação: o PostgreSQL não possui senha padrão. Caso o usuário ou senha configurados não existam no seu ambiente, ajuste-os antes de executar a aplicação.


## 🌐 URL do Banco de Dados
Por padrão a aplicação utiliza: 

jdbc:postgresql://localhost:5432/postgres?currentSchema=tabela_tarifaria_api

Caso necessário, sobrescreva utilizando:

    $env:DB_URL="jdbc:postgresql://localhost:5432/postgres?currentSchema=tabela_tarifaria_api"

## ▶️ Como Executar o Projeto:
1. Certifique-se de que o PostgreSQL está rodando.
2. Clone o repositório:
   ```
    git clone https://github.com/pedrogstrindade/tabela-tarifaria.git
    cd tabela-tarifaria
   ```
3. Execute a aplicação:
    ```
    mvn clean install
    mvn spring-boot:run
    ```
<details>
<summary>⚠️ Observação: Caso tenha dificuldade com o comando mvn no terminal clique aqui. </summary>

### ⚙️ Configuração do Maven no PATH

Em alguns sistemas, apenas instalar o Maven não é suficiente para que o comando `mvn` funcione no terminal.

Caso enfrente o erro:

`mvn não é reconhecido como um comando interno ou externo`

Consulte o tutorial oficial:
🔗 https://maven.apache.org/install.html

</details>


## 🧪 Testando a API

###  Swagger UI (Recomendado)
A API possui documentação interativa via Swagger, permitindo testar todos os endpoints diretamente pelo navegador:

👉 http://localhost:8080/swagger-ui/index.html

###  Postman / Insomnia

Caso prefira ferramentas de API Client como **Postman** ou **Insomnia**, basta configurar as requisições manualmente utilizando:

- **Base URL:** `http://localhost:8080`
- **Content-Type:** `application/json`

Os endpoints e payloads seguem exatamente o mesmo padrão documentado no Swagger.

## 📥 Requests e Responses (JSON): 
    
### ➕ Cadastro da Tabela Tarifária (POST)
Endpoint: /api/tabelas-tarifarias 

Payload Completo (4 Categorias):

<details>
<summary>Request</summary>
    
```
      [
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

```

</details>

<details>
<summary>Response (201 CREATED) </summary>
 
```JSON
{
  "nomeTabelaTarifaria": "Tabela Progressiva Estadual - Edição Fevereiro 2024",
  "dataVigencia": "03/02/2024",
  "categoriasRelacao": [
    {
      "categoria": "PARTICULAR",
      "faixasConsumo": [
        {
          "inicio": 0,
          "fim": 10,
          "valorUnitario": 5.8
        },
        {
          "inicio": 11,
          "fim": 15,
          "valorUnitario": 7.2
        },
        {
          "inicio": 16,
          "fim": 20,
          "valorUnitario": 9.5
        },
        {
          "inicio": 21,
          "fim": 30,
          "valorUnitario": 12.8
        },
        {
          "inicio": 31,
          "fim": 999999,
          "valorUnitario": 18
        }
      ]
    },
    {
      "categoria": "COMERCIAL",
      "faixasConsumo": [
        {
          "inicio": 0,
          "fim": 15,
          "valorUnitario": 14.5
        },
        {
          "inicio": 16,
          "fim": 30,
          "valorUnitario": 19.8
        },
        {
          "inicio": 31,
          "fim": 50,
          "valorUnitario": 26.4
        },
        {
          "inicio": 51,
          "fim": 100,
          "valorUnitario": 35
        },
        {
          "inicio": 101,
          "fim": 999999,
          "valorUnitario": 48.5
        }
      ]
    },
    {
      "categoria": "INDUSTRIAL",
      "faixasConsumo": [
        {
          "inicio": 0,
          "fim": 50,
          "valorUnitario": 38
        },
        {
          "inicio": 51,
          "fim": 150,
          "valorUnitario": 52
        },
        {
          "inicio": 151,
          "fim": 300,
          "valorUnitario": 68.5
        },
        {
          "inicio": 301,
          "fim": 500,
          "valorUnitario": 85
        },
        {
          "inicio": 501,
          "fim": 999999,
          "valorUnitario": 110
        }
      ]
    },
    {
      "categoria": "PÚBLICO",
      "faixasConsumo": [
        {
          "inicio": 0,
          "fim": 100,
          "valorUnitario": 10
        },
        {
          "inicio": 101,
          "fim": 500,
          "valorUnitario": 15
        },
        {
          "inicio": 501,
          "fim": 1000,
          "valorUnitario": 22.5
        },
        {
          "inicio": 1001,
          "fim": 999999,
          "valorUnitario": 30
        }
      ]
    }
  ]
}
```
</Details>

---

### 📄 Listagem de Tabelas (GET)
Endpoint: /api/tabelas-tarifarias


<details>
  <summary>Response (200 OK)</summary>

  ```json
  [
  {
    "nomeTabelaTarifaria": "Tabela Progressiva Estadual - Edição Fevereiro 2026",
    "dataVigencia": "03/02/2026",
    "categoriasRelacao": [
      {
        "categoria": "PARTICULAR",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 10,
            "valorUnitario": 5.8
          },
          {
            "inicio": 11,
            "fim": 15,
            "valorUnitario": 7.2
          },
          {
            "inicio": 16,
            "fim": 20,
            "valorUnitario": 9.5
          },
          {
            "inicio": 21,
            "fim": 30,
            "valorUnitario": 12.8
          },
          {
            "inicio": 31,
            "fim": 999999,
            "valorUnitario": 18
          }
        ]
      },
      {
        "categoria": "COMERCIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 15,
            "valorUnitario": 14.5
          },
          {
            "inicio": 16,
            "fim": 30,
            "valorUnitario": 19.8
          },
          {
            "inicio": 31,
            "fim": 50,
            "valorUnitario": 26.4
          },
          {
            "inicio": 51,
            "fim": 100,
            "valorUnitario": 35
          },
          {
            "inicio": 101,
            "fim": 999999,
            "valorUnitario": 48.5
          }
        ]
      },
      {
        "categoria": "INDUSTRIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 50,
            "valorUnitario": 38
          },
          {
            "inicio": 51,
            "fim": 150,
            "valorUnitario": 52
          },
          {
            "inicio": 151,
            "fim": 300,
            "valorUnitario": 68.5
          },
          {
            "inicio": 301,
            "fim": 500,
            "valorUnitario": 85
          },
          {
            "inicio": 501,
            "fim": 999999,
            "valorUnitario": 110
          }
        ]
      },
      {
        "categoria": "PÚBLICO",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 100,
            "valorUnitario": 10
          },
          {
            "inicio": 101,
            "fim": 500,
            "valorUnitario": 15
          },
          {
            "inicio": 501,
            "fim": 1000,
            "valorUnitario": 22.5
          },
          {
            "inicio": 1001,
            "fim": 999999,
            "valorUnitario": 30
          }
        ]
      }
    ]
  },
  {
    "nomeTabelaTarifaria": "Tabela Progressiva Nacional - Edição Fevereiro 2026",
    "dataVigencia": "03/02/2026",
    "categoriasRelacao": [
      {
        "categoria": "PARTICULAR",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 10,
            "valorUnitario": 5.8
          },
          {
            "inicio": 11,
            "fim": 15,
            "valorUnitario": 7.2
          },
          {
            "inicio": 16,
            "fim": 20,
            "valorUnitario": 9.5
          },
          {
            "inicio": 21,
            "fim": 30,
            "valorUnitario": 12.8
          },
          {
            "inicio": 31,
            "fim": 999999,
            "valorUnitario": 18
          }
        ]
      },
      {
        "categoria": "COMERCIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 15,
            "valorUnitario": 14.5
          },
          {
            "inicio": 16,
            "fim": 30,
            "valorUnitario": 19.8
          },
          {
            "inicio": 31,
            "fim": 50,
            "valorUnitario": 26.4
          },
          {
            "inicio": 51,
            "fim": 100,
            "valorUnitario": 35
          },
          {
            "inicio": 101,
            "fim": 999999,
            "valorUnitario": 48.5
          }
        ]
      },
      {
        "categoria": "INDUSTRIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 50,
            "valorUnitario": 38
          },
          {
            "inicio": 51,
            "fim": 150,
            "valorUnitario": 52
          },
          {
            "inicio": 151,
            "fim": 300,
            "valorUnitario": 68.5
          },
          {
            "inicio": 301,
            "fim": 500,
            "valorUnitario": 85
          },
          {
            "inicio": 501,
            "fim": 999999,
            "valorUnitario": 110
          }
        ]
      },
      {
        "categoria": "PÚBLICO",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 100,
            "valorUnitario": 10
          },
          {
            "inicio": 101,
            "fim": 500,
            "valorUnitario": 15
          },
          {
            "inicio": 501,
            "fim": 1000,
            "valorUnitario": 22.5
          },
          {
            "inicio": 1001,
            "fim": 999999,
            "valorUnitario": 30
          }
        ]
      }
    ]
  },
  {
    "nomeTabelaTarifaria": "Tabela Progressiva Estadual - Edição Fevereiro 2024",
    "dataVigencia": "03/02/2024",
    "categoriasRelacao": [
      {
        "categoria": "PARTICULAR",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 10,
            "valorUnitario": 5.8
          },
          {
            "inicio": 11,
            "fim": 15,
            "valorUnitario": 7.2
          },
          {
            "inicio": 16,
            "fim": 20,
            "valorUnitario": 9.5
          },
          {
            "inicio": 21,
            "fim": 30,
            "valorUnitario": 12.8
          },
          {
            "inicio": 31,
            "fim": 999999,
            "valorUnitario": 18
          }
        ]
      },
      {
        "categoria": "COMERCIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 15,
            "valorUnitario": 14.5
          },
          {
            "inicio": 16,
            "fim": 30,
            "valorUnitario": 19.8
          },
          {
            "inicio": 31,
            "fim": 50,
            "valorUnitario": 26.4
          },
          {
            "inicio": 51,
            "fim": 100,
            "valorUnitario": 35
          },
          {
            "inicio": 101,
            "fim": 999999,
            "valorUnitario": 48.5
          }
        ]
      },
      {
        "categoria": "INDUSTRIAL",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 50,
            "valorUnitario": 38
          },
          {
            "inicio": 51,
            "fim": 150,
            "valorUnitario": 52
          },
          {
            "inicio": 151,
            "fim": 300,
            "valorUnitario": 68.5
          },
          {
            "inicio": 301,
            "fim": 500,
            "valorUnitario": 85
          },
          {
            "inicio": 501,
            "fim": 999999,
            "valorUnitario": 110
          }
        ]
      },
      {
        "categoria": "PÚBLICO",
        "faixasConsumo": [
          {
            "inicio": 0,
            "fim": 100,
            "valorUnitario": 10
          },
          {
            "inicio": 101,
            "fim": 500,
            "valorUnitario": 15
          },
          {
            "inicio": 501,
            "fim": 1000,
            "valorUnitario": 22.5
          },
          {
            "inicio": 1001,
            "fim": 999999,
            "valorUnitario": 30
          }
        ]
      }
    ]
  }
]
```
</details>

---

### 🗑️ Exclusão de uma Tabela Tarifária (DELETE)
Endpoint: /api/tabelas-tarifarias/{id}

Request: Consulte o banco para ver o id que deseja apagar, por segurança, o id não é transacionado.

Response: (204 No Content) 

---

### 🧮 Calcular Faturamento (POST)
Endpoint: /api/calculos

<details>
<summary>Request</summary>

``` 
    {
      "categoria": "COMERCIAL",
      "consumo": 38
    }

```
</details>
<details>
<summary>Response (200 Ok)</summary>

```
{
  "categoria": "COMERCIAL",
  "consumoTotal": 38,
  "valorTotal": "713.80",
  "detalhamento": [
    {
      "faixa": {
        "inicio": 0,
        "fim": 15
      },
      "m3Cobrados": 16,
      "valorUnitario": "14.50",
      "subtotal": "232.00"
    },
    {
      "faixa": {
        "inicio": 16,
        "fim": 30
      },
      "m3Cobrados": 15,
      "valorUnitario": "19.80",
      "subtotal": "297.00"
    },
    {
      "faixa": {
        "inicio": 31,
        "fim": 50
      },
      "m3Cobrados": 7,
      "valorUnitario": "26.40",
      "subtotal": "184.80"
    }
  ]
}

```
</details>

## 🧑‍💻 Autor
Pedro Trindade  
📧 [dev.pedrogstrindade@gmail.com](mailto:dev.pedrogstrindade@gmail.com) · 🌐 [GitHub](https://github.com/pedrogstrindade) · 💼 [LinkedIn](https://www.linkedin.com/in/pedro-trindade-1a8351198/)
