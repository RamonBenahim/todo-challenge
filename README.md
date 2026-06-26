# To-Do Application (Desafio Técnico Java Sênior)

Esta é uma solução para o desafio técnico focado em qualidade de código, arquitetura moderna e boas práticas.

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 21, Micronaut, PostgreSQL, Flyway, Kafka, JUnit 5, Mockito.
- **Frontend:** React, TypeScript, Vite.
- **Infraestrutura:** Docker, Docker Compose, GitHub Actions.

## 🏛️ Arquitetura

A aplicação backend foi desenhada utilizando uma **Arquitetura em Camadas** com foco em baixo acoplamento:
1. **API / Controller (`api`)**: Lida com requisições HTTP e DTOs, mantendo validações básicas.
2. **Application / Service (`application`)**: Orquestra as regras de negócio e integrações (ex.: enviar eventos).
3. **Domain (`domain`)**: Contém o coração da aplicação, como as entidades e enums, sem depender de frameworks externos quando possível.
4. **Infrastructure (`infrastructure`)**: Lida com persistência (Data JPA) e mensageria (Kafka).

**Decisões Técnicas Principais:**
- Escolha do **Micronaut** devido à sua performance (baixo consumo de memória e rápida inicialização) e por ser considerado um "diferencial" no desafio.
- Integração de um fluxo orientado a eventos (Event-Driven) via **Kafka**, publicando eventos (`task-events`) em cada criação, atualização ou remoção.
- Estilização de interface moderna em React via **CSS puro (Glassmorphism)**, priorizando performance, ausência de bibliotecas externas complexas e UI premium.

## 🚀 Como Executar Localmente

### Pré-requisitos
- Docker e Docker Compose instalados.
- JDK 21 (caso queira rodar o backend fora do container Docker).
- Node.js (caso queira rodar o frontend separadamente).

### Subindo os Serviços Base (PostgreSQL e Kafka)

```bash
docker-compose up -d
```

### Executando o Backend

Acesse o diretório do backend e execute a aplicação via Gradle:

```bash
cd backend
./gradlew run
```

A API estará disponível em `http://localhost:8080`.

### Executando o Frontend

Acesse o diretório do frontend, instale as dependências e inicie o servidor:

```bash
cd frontend
npm install
npm run dev
```

Acesse o frontend em `http://localhost:5173`.

## 🧪 Qualidade e Testes

- Há testes automatizados utilizando **JUnit 5** e **Mockito** (ex.: `TaskServiceTest`).
- Configurado endpoint de **Healthcheck** via Micronaut Management, além de logging estruturado.

## 🤖 Uso de Inteligência Artificial

A inteligência artificial (Gemini) foi utilizada para guiar a modelagem da arquitetura do projeto e agilizar a criação de configurações boilerplate (como Docker Compose e Micronaut annotations), garantindo que as diretrizes modernas de Engenharia de Software fossem implementadas rapidamente.
