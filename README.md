# 🚀 Projeto: change-governance-service

## 🎯 Objetivo

API para controle de mudanças (GMUD / Change Management), simulando fluxo empresarial:

- Solicitar mudança
- Aprovar mudança
- Consultar mudanças
- Alterar status
- Histórico básico

> [!NOTE]
> Conectando com Jenkins pipeline e deploy approval.

## 🏗️ Arquitetura inicial

```console
src/main/java/com/leonardo/devops/changerequest
│
├── controller
├── service
├── repository
├── entity
├── dto
├── enums
└── config
```


## 📦 Estrutura recomendada

```console
change-governance-service
├── controller
│   └── ChangeRequestController.java
│
├── service
│   └── ChangeRequestService.java
│
├── repository
│   └── ChangeRequestRepository.java
│
├── entity
│   └── ChangeRequest.java
│
├── dto
│   ├── ChangeRequestRequest.java
│   └── ChangeRequestResponse.java
│
├── enums
│   └── ChangeStatus.java
│
└── ChangeRequestServiceApplication.java
```

## 🧩 Entidade principal

`ChangeRequest.java`

Campos recomendados:

```java
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
```

## 🔖 Enum de Status

`ChangeStatus.java`

```java
PENDING
APPROVED
REJECTED
DEPLOYED
FAILED
ROLLED_BACK
```

## 🌐 Endpoints REST

Criar mudança

```bash
POST /api/change-requests
```

Body:

```json
{
  "title": "Deploy versão 1.0.3",
  "description": "Correção de bug login",
  "requester": "Leonardo",
  "systemName": "Portal Cliente",
  "riskLevel": "LOW"
}
```

Listar mudanças

```bash
GET /api/change-requests
```

Buscar por ID

```bash
GET /api/change-requests/{id}
```

Aprovar mudança

```bash
PUT /api/change-requests/{id}/approve
```

Body:

```json
{
  "approvedBy": "Gestor TI"
}
```

Rejeitar mudança

```bash
PUT /api/change-requests/{id}/reject
```

Marcar deploy realizado

```bash
PUT /api/change-requests/{id}/deploy
```

## 🔥 Como isso conecta com Jenkins

Pipeline pode chamar API:

1. Antes do deploy:

```console
Buscar mudança aprovada
```

Depois do deploy:

```console
Atualizar status para DEPLOYED
```

## 📄 Exemplo de fluxo real

```console
1. Usuário cria mudança
2. Gestor aprova
3. Jenkins consulta aprovação
4. Jenkins executa deploy
5. API marca DEPLOYED
6. Evidence.json gerado
```

## 🧠 Banco de dados

Inicialmente:

H2 ou PostgreSQL via Docker.
