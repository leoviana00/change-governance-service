# 🚀 change-governance-service

API de **Governança de Mudanças (GMUD / Change Management)** desenvolvida em Spring Boot, criada para integrar processos de aprovação corporativa com pipelines modernas de CI/CD.

Este projeto demonstra como mudanças podem ser **solicitadas, aprovadas, implantadas e auditadas** de forma automatizada.

---

# 🎯 Objetivo

Simular um fluxo empresarial real de Change Management:

- Solicitar mudança  
- Aprovar ou rejeitar mudança  
- Consultar mudanças  
- Liberar deploy somente após aprovação  
- Registrar deploy automatizado  
- Controlar redeploy com justificativa obrigatória  
- Manter rastreabilidade operacional  

---

# 🏗️ Arquitetura Atual

    src/main/java/io/viana/change_governance_service/

    ├── controller
    ├── service
    ├── repository
    ├── entity
    ├── dto
    ├── enums
    ├── handler
    └── config

---

# 🧩 Entidade Principal

## ChangeRequest

Campos atuais:

    UUID id;
    String title;
    String description;
    String requester;
    String systemName;
    String riskLevel;

    ChangeStatus status;

    LocalDateTime createdAt;
    LocalDateTime approvedAt;
    String approvedBy;

    LocalDateTime deployedAt;
    String deployedBy;

    Integer deployCount;
    String lastDeployReason;

---

# 🔖 Status da Mudança

    PENDING
    APPROVED
    REJECTED
    DEPLOYED

### Fluxo principal

    PENDING → APPROVED → DEPLOYED

ou

    PENDING → REJECTED

---

# 🌐 Endpoints REST

## Criar mudança

    POST /api/change-requests

### Body

    {
      "title": "Deploy versão 1.0.3",
      "description": "Correção de bug login",
      "requester": "Leonardo",
      "systemName": "payment-service",
      "riskLevel": "LOW"
    }

## Listar mudanças

    GET /api/change-requests

## Buscar por ID

    GET /api/change-requests/{id}

## Aprovar mudança

    PUT /api/change-requests/{id}/approve

### Body

    {
      "approvedBy": "Gestor TI"
    }

## Rejeitar mudança

    PUT /api/change-requests/{id}/reject

## Deploy / Redeploy

    PUT /api/change-requests/{id}/deploy

### Primeiro deploy

    {
      "executedBy": "Jenkins"
    }

### Redeploy (obrigatório informar motivo)

    {
      "executedBy": "Jenkins",
      "reason": "Container restart after maintenance"
    }

---

# 🛡️ Regras de Negócio

## Aprovação

Somente mudanças com status:

    PENDING

podem ser aprovadas ou rejeitadas.

## Deploy

Somente mudanças com status:

    APPROVED

podem ser implantadas pela primeira vez.

## Redeploy Controlado

Mudanças já implantadas (`DEPLOYED`) só podem receber novo deploy com justificativa obrigatória.

Exemplos:

    Container restart
    Rollback retry
    Replica rebuild
    Host maintenance

---

# 🔥 Caso Real Executado

Registro real obtido no sistema:

    {
      "status": "DEPLOYED",
      "approvedBy": "Gestor TI",
      "deployedBy": "Jenkins",
      "deployCount": 2,
      "lastDeployReason": "Container restart"
    }

Isso comprova:

- Aprovação humana  
- Deploy automatizado  
- Redeploy auditado  
- Histórico operacional  

---

# 🤖 Integração com Jenkins

Pipeline pode executar:

## Antes do deploy

    Consultar se mudança está APPROVED

## Após deploy

    Atualizar status para DEPLOYED
    Registrar executor
    Registrar motivo (se redeploy)

---

# 📄 Fluxo Completo

    1. Usuário cria mudança
    2. Gestor aprova
    3. GitHub mergeia release
    4. Jenkins inicia pipeline
    5. Jenkins consulta API
    6. Deploy executado
    7. API registra DEPLOYED
    8. Evidence.json gerado

---

# 🧠 Banco de Dados

## Ambiente Local

    H2 Database

## Ambiente Principal

    PostgreSQL

## Profiles

    local
    prod

---

# 🛠️ Stack Tecnológica

    Java 17
    Spring Boot 3
    Spring Web
    Spring Data JPA
    H2
    PostgreSQL
    Maven
    Swagger / OpenAPI
    Docker
    Jenkins
    GitHub Actions

---

# 📈 Roadmap

- Histórico detalhado de eventos  
- Janela de mudança  
- Aprovação por nível de risco  
- Dashboard de métricas  
- Multi-service governance  

---

# 🔗 Ecossistema Relacionado

- [devops-governance-platform](https://github.com/leoviana00/devops-governance-platform)
- [payment-service](https://github.com/leoviana00/payment-service)