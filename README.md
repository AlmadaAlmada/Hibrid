# Hibrid - POC

Prova de conceito do sistema Hibrid, voltado a recria de gado leiteiro. O objetivo
e demonstrar o fluxo proposto na Entrega 5: cadastro de animais e semen, dashboard
de producao e a "Analise de Dados", que recomenda o melhor cruzamento entre vaca e
semen para gerar femeas leiteiras e machos aproveitaveis para corte.

A parte de Inteligencia Artificial e simulada. A recomendacao e calculada de forma
deterministica no backend a partir dos atributos do animal e do semen, sem nenhum
modelo ou servico externo. Todos os dados sao mocados via seed no banco.

## Tecnologias

- Backend: Java 21 + Spring Boot (API REST)
- Banco de dados: PostgreSQL
- Frontend: JavaScript puro (vanilla), HTML e CSS, servido pelo proprio backend
- Orquestracao: Docker Compose

## Estrutura

```
poc-hibrid/
  docker-compose.yml
  backend/
    Dockerfile
    pom.xml
    src/main/java/com/hibrid/poc/   API, entidades, repositorios e servico de predicao
    src/main/resources/
      schema.sql                    criacao das tabelas
      data.sql                      dados mocados
      static/                       frontend (index.html, css, js)
```

## Como executar

### Opcao 1 - Docker (recomendada)

Requer apenas Docker e Docker Compose.

```
cd poc-hibrid
docker compose up --build
```

Acesse: http://localhost:8080

O banco sobe junto, e o schema e os dados mocados sao recriados a cada inicializacao.

### Opcao 2 - Local (Java + Maven + PostgreSQL)

1. Suba um PostgreSQL com banco, usuario e senha `hibrid`:

```
docker run --name hibrid-db -e POSTGRES_DB=hibrid -e POSTGRES_USER=hibrid -e POSTGRES_PASSWORD=hibrid -p 5432:5432 -d postgres:16-alpine
```

2. Rode a aplicacao:

```
cd poc-hibrid/backend
mvn spring-boot:run
```

3. Acesse: http://localhost:8080

As credenciais do banco podem ser sobrescritas pelas variaveis de ambiente
`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER` e `DB_PASSWORD`.

## Telas

- Inicio: dashboard com totais de vacas, bois, bezerros e producao de leite
- Listagem de Animais: tabela com cadastro, edicao e exclusao (abas Geral e Producao)
- Listagem de Semen: catalogo do material genetico disponivel
- Analise de Dados: selecao de vaca e semen, geracao da predicao simulada
  (score genetico, producao de leite estimada, ganho de peso, fertilidade,
  composicao genetica, probabilidade de caracteristicas e recomendacao) com
  historico dos cruzamentos
- Meus dados: edicao do usuario logado

## Endpoints principais

```
GET    /api/dashboard
GET    /api/animais            (filtro opcional ?tipo=VACA|BOI|BEZERRO)
GET    /api/animais/{id}
POST   /api/animais
PUT    /api/animais/{id}
DELETE /api/animais/{id}
GET    /api/semen
GET    /api/usuario/atual
PUT    /api/usuario/{id}
POST   /api/predicao           body { "vacaId": 1, "semenId": 1 }
GET    /api/predicao/historico
```

## Observacoes

- POC de demonstracao. Sem autenticacao real, sem validacoes completas e com
  dados ficticios.
- A predicao nao usa IA: e uma simulacao deterministica que substitui o provedor
  de IA previsto na arquitetura final.
