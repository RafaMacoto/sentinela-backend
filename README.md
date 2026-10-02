# Sentinela Backend

Backend do **Sentinela**, uma plataforma de detecção e análise de fraudes em transações financeiras.

O projeto está sendo desenvolvido com foco em **Java, Spring Boot, arquitetura de software, Machine Learning e Inteligência Artificial**, simulando uma solução que poderia ser utilizada para identificar transações suspeitas e auxiliar na investigação de possíveis fraudes.

> 🚧 **Project under development**

## 🎯 Objective

O Sentinela tem como objetivo analisar transações financeiras, identificar padrões suspeitos e gerar uma análise de risco para cada operação.

A arquitetura foi planejada para combinar:

* **Java + Spring Boot** para o backend principal;
* **PostgreSQL** para persistência dos dados;
* **Python + FastAPI** para o serviço de Machine Learning;
* **Machine Learning** para detecção de padrões de fraude;
* **LLM** para interpretação dos sinais e geração de explicações;
* **React + TypeScript** para o frontend.

O backend Java será responsável por orquestrar esses componentes e disponibilizar a API principal da aplicação.

## 🏗️ Architecture

A arquitetura planejada do sistema é:

```text
                    ┌─────────────────┐
                    │    Frontend     │
                    │ React + TS      │
                    └────────┬────────┘
                             │
                          REST API
                             │
                             ▼
                    ┌─────────────────┐
                    │  Java Backend   │
                    │ Spring Boot     │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              ▼
        PostgreSQL      Python / ML      LLM API
                         FastAPI
                             │
                             ▼
                         ML Model
```

Atualmente, o repositório concentra o desenvolvimento do **backend Java**. Os serviços de Machine Learning e frontend serão integrados posteriormente.

## 🧩 Domain

O domínio principal da aplicação é composto por:

```text
Customer
   │
   ├── Account
   ├── Device
   └── Transaction
            │
            └── FraudAnalysis
```

### Customer

Representa o cliente da instituição financeira.

### Account

Representa uma conta pertencente a um cliente.

Um cliente pode possuir múltiplas contas.

### Device

Representa um dispositivo utilizado pelo cliente para realizar operações.

Um cliente pode possuir múltiplos dispositivos.

### Transaction

Representa uma transação financeira realizada pelo sistema.

A transação possui informações como valor, cliente, conta, dispositivo e indicadores que podem ser utilizados na identificação de comportamentos suspeitos.

### FraudAnalysis

Representa o resultado da análise de uma transação.

A entidade foi projetada para armazenar informações como:

* Score de risco;
* Nível de risco;
* Motivos identificados;
* Transação analisada;
* Data da análise.

Futuramente, essas informações serão alimentadas pelo pipeline de detecção de fraude e pela LLM.

## 🏛️ Architecture Pattern

O backend utiliza uma arquitetura em camadas:

```text
Controller
    ↓
Service Interface
    ↓
ServiceImpl
    ↓
Repository
    ↓
Entity
    ↓
PostgreSQL
```

### Controller

Responsável pelos endpoints HTTP e pela comunicação com o cliente da API.

### Service

Contém as regras de negócio da aplicação.

As interfaces de serviço definem os contratos, enquanto as implementações concentram a lógica de negócio.

### Repository

Responsável pelo acesso e persistência dos dados utilizando Spring Data JPA.

### Entity

Representa os objetos persistidos no banco de dados.

### DTO

Define os contratos de entrada e saída da API, separando a representação externa dos objetos de domínio.

## 📦 Technologies

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Jakarta Validation
* Lombok
* OpenAPI / Swagger
* Maven
* Docker

## 🔌 API

A API utiliza arquitetura REST.

Os endpoints estão organizados por domínio:

```text
/api/customers
/api/accounts
/api/devices
/api/transactions
/api/fraud-analysis
```

A documentação da API pode ser acessada através do Swagger quando a aplicação estiver em execução.

## 🧪 HTTP Requests

O projeto possui arquivos `.http` para facilitar os testes dos endpoints diretamente pela IDE:

```text
http/
├── customer.http
├── account.http
├── device.http
├── transaction.http
└── fraud-analysis.http
```

Esses arquivos permitem executar requisições HTTP sem depender de ferramentas externas como Postman.

## 🗄️ Database

O projeto utiliza **PostgreSQL** como banco de dados.

O ambiente de desenvolvimento pode ser executado utilizando Docker Compose.

As configurações de conexão são definidas através de variáveis de ambiente.

## 🤖 Artificial Intelligence

A camada de Inteligência Artificial será incorporada progressivamente ao projeto.

O fluxo planejado é:

```text
Transaction
      ↓
Machine Learning Model
      ↓
Risk Score + Fraud Signals
      ↓
LLM
      ↓
Explanation
      ↓
FraudAnalysis
```

O modelo de Machine Learning será responsável pela identificação de padrões associados a possíveis fraudes.

A LLM terá como função interpretar os sinais encontrados, fornecer uma explicação contextualizada e contribuir para a geração da análise final.

## 🚀 Roadmap

### Backend

* [x] Project setup
* [x] Customer domain
* [x] Account domain
* [x] Device domain
* [x] Transaction domain
* [x] FraudAnalysis domain
* [x] REST controllers
* [x] Service layer
* [x] Repository layer
* [x] PostgreSQL integration
* [x] Swagger documentation
* [x] HTTP test requests
* [ ] Automated tests
* [ ] ML service integration
* [ ] LLM integration

### Machine Learning

* [ ] Dataset preparation
* [ ] Data preprocessing
* [ ] Feature engineering
* [ ] Model training
* [ ] Model evaluation
* [ ] Fraud prediction API with FastAPI
* [ ] Integration with Java backend

### Frontend

* [ ] React application
* [ ] Transaction dashboard
* [ ] Fraud analysis dashboard
* [ ] Risk visualization
* [ ] Integration with Java backend

## 📚 Purpose

This project is being developed as a practical study of:

* Software Architecture
* Java and Spring Boot
* REST APIs
* Relational databases
* Microservice communication
* Machine Learning
* Large Language Models
* AI-powered fraud detection
* Full-stack development

The goal is to progressively evolve the project from a structured backend into a complete fraud detection and investigation platform.

## 👨‍💻 Author

**Rafael Macoto**

Software Developer focused on Java, .NET, Artificial Intelligence, Cloud and Software Architecture.
