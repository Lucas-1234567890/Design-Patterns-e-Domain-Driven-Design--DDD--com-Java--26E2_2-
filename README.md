#  MusicStream

API REST de streaming de música desenvolvida com **Spring Boot**, aplicando **Domain-Driven Design (DDD)**, princípios **SOLID** e **Clean Code**.

---

##  Como executar

**Pré-requisito:** JDK 21 instalado.

```bash
# Na raiz do projeto (onde está o pom.xml)
mvn spring-boot:run
```

- API: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:musicstreamdb`
  - User: `sa` | Password: *(vazio)*

---

##  Stack

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem |
| Spring Boot 3.2 | Framework |
| Spring Data JPA | Persistência |
| H2 | Banco in-memory |
| Lombok | Redução de boilerplate |
| Maven | Build |

---

##  Estrutura do projeto

```
src/main/java/com/musicstream/
│
├── domain/                        # Núcleo do negócio (sem dependências externas)
│   ├── account/
│   │   ├── model/                 # Account [AR], CreditCard [VO]
│   │   ├── repository/            # Interface AccountRepository
│   │   └── exception/
│   ├── antifraud/
│   │   ├── model/                 # Transaction, FraudViolation, TransactionStatus
│   │   ├── service/               # FraudRule [interface], FraudDetectionService [DS]
│   │   │                          # CardNotActiveRule, HighFrequencySmallIntervalRule
│   │   │                          # DoubledTransactionRule, TransactionRepository
│   │   └── exception/
│   ├── music/
│   │   ├── model/                 # Music, FavoriteMusic
│   │   └── repository/
│   ├── playlist/
│   │   ├── model/                 # Playlist [AR]
│   │   └── repository/
│   └── subscription/
│       ├── model/                 # Subscription [AR], PlanType [enum]
│       ├── repository/
│       └── service/               # SubscriptionService [DS]
│
├── application/                   # Casos de uso (orquestra domínio, sem lógica de negócio)
│   ├── account/                   # AccountApplicationService
│   ├── music/                     # MusicApplicationService
│   ├── playlist/                  # PlaylistApplicationService
│   └── subscription/              # SubscriptionApplicationService, TransactionApplicationService
│
├── infrastructure/                # Implementações concretas
│   ├── persistence/               # *RepositoryImpl + *JpaRepository (Spring Data)
│   └── config/                    # GlobalExceptionHandler
│
└── interfaces/
    └── rest/                      # Controllers REST
```

---

##  Endpoints

### Account
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/accounts` | Criar conta |
| `GET` | `/api/accounts/{id}` | Buscar conta por ID |
| `POST` | `/api/accounts/{id}/credit-card` | Associar cartão de crédito |

### Music
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/musics` | Cadastrar música |
| `GET` | `/api/musics` | Listar catálogo |
| `POST` | `/api/musics/{musicId}/favorite` | Favoritar música |
| `GET` | `/api/musics/favorites/{accountId}` | Listar favoritos do usuário |

### Playlist
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/playlists` | Criar playlist |
| `GET` | `/api/playlists/account/{accountId}` | Listar playlists do usuário |
| `POST` | `/api/playlists/{id}/musics/{musicId}` | Adicionar música à playlist |
| `DELETE` | `/api/playlists/{id}/musics/{musicId}` | Remover música da playlist |

### Subscription
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/subscriptions` | Assinar plano (`FREE`, `PREMIUM`, `FAMILY`) |
| `PATCH` | `/api/subscriptions/{accountId}/plan` | Alterar plano ativo |
| `DELETE` | `/api/subscriptions/{accountId}` | Cancelar assinatura |

### Transaction
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/transactions` | Autorizar transação (aplica antifraude) |

---

##  Regras de Antifraude

Aplicadas automaticamente em `POST /api/transactions`. Implementadas com **Open/Closed Principle** — cada regra é uma classe independente que implementa `FraudRule`.

| Violação | Código | Regra |
|---|---|---|
| Cartão inativo ou ausente | `cartao-nao-ativo` | `CardNotActiveRule` |
| Mais de 3 transações em 2 min | `alta-frequencia-pequeno-intervalo` | `HighFrequencySmallIntervalRule` |
| Mais de 2 transações iguais em 2 min | `transacao-duplicada` | `DoubledTransactionRule` |
| Plano duplicado | — | `SubscriptionService` |

**Resposta em caso de violação (`422`):**
```json
{
  "status": 422,
  "error": "Transação rejeitada",
  "violations": ["cartao-nao-ativo"]
}
```

---

##  Decisões de design

### DDD
- **Aggregate Roots com domínio rico:** lógica de negócio dentro das entidades (`Account`, `Subscription`, `Playlist`), não em services anêmicos
- **Value Object imutável:** `CreditCard` retorna nova instância em `activate()`/`deactivate()`
- **Intention Revealing Interfaces:** nomes de métodos comunicam intenção — `hasValidCreditCard()`, `findActiveSubscriptionByAccountId()`
- **Ubiquitous Language:** nomes de código espelham o vocabulário do negócio

### Context Mapping
| Relação | Padrão |
|---|---|
| Account → Antifraud | Anticorruption Layer |
| Account → Subscription | Customer-Supplier |
| Music ↔ Playlist | Partnership |

### SOLID
- **S** — cada classe tem uma única responsabilidade
- **O** — novas regras de fraude = nova classe, zero alteração no `FraudDetectionService`
- **L** — qualquer `FraudRule` é substituível sem quebrar o sistema
- **I** — repositórios expostos como interfaces com só os métodos necessários
- **D** — domínio depende de interfaces; infraestrutura implementa

---

##  Autor

**Lucas Amorim Porciuncula**  
5° Semestre — Engenharia de Dados e IA  
Bloco: Engenharia de Softwares Escaláveis
