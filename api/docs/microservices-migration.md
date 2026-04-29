# OrderFlow - Plano de Migracao para Microsservicos

## Objetivo

Evoluir o monolito atual para microsservicos com desenvolvimento local via Docker, mantendo:

- Organizacao atual: **monorepo** com `api/` (Spring Boot) e `web/` (frontend) neste repositorio; extracao futura pode voltar a **multi-repo** (um repositorio por servico) conforme as fases abaixo
- Banco PostgreSQL unico compartilhado
- Isolamento por schema/tabela (ownership por servico)
- Integracao assincorna via RabbitMQ

## Estado atual resumido

- API monolitica Spring Boot com CRUD de catalogo.
- Publicacao de evento `OrderCreated` ja existente.
- Consumers de inventario e notificacao ainda no mesmo processo.
- Docker Compose na raiz do monorepo sobe app + web + Postgres + RabbitMQ.

## Alvo arquitetural

Servicos previstos:

- `orderflow-catalog-api`
- `orderflow-order-api`
- `orderflow-inventory-worker`
- `orderflow-notification-worker`
- `orderflow-contracts` (schemas/eventos)
- `orderflow-local-dev` (compose e scripts)

### Banco compartilhado (regra oficial)

Cada servico possui ownership claro dos seus dados:

- `catalog` schema: produtos e categorias
- `orders` schema: pedidos e itens
- `inventory` schema: reserva/baixa de estoque
- `notification` schema: log/outbox de notificacoes

Regras:

1. Cada servico escreve apenas no proprio schema.
2. Leitura cross-service por API/evento, nao por join direto.
3. Usuario SQL por servico com grants restritos.
4. Migracoes versionadas por servico (Flyway recomendado).

## Fases de implementacao

1. Baseline tecnico
   - Corrigir inconsistencias de build.
   - Alinhar README com estado real e roadmap.
2. Contratos
   - Criar schemas de eventos no repo `orderflow-contracts`.
3. Extracao de workers
   - Mover consumers para `inventory-worker` e `notification-worker`.
4. Extracao de APIs
   - Separar catalogo e pedidos em servicos independentes.
5. Hardening
   - DLQ, idempotencia, observabilidade e pipeline CI.

## Docker dev local (conceito)

Hoje o `docker-compose.yml` na **raiz deste monorepo** sobe Postgres, RabbitMQ, API e web. No alvo multi-repo, um repositorio `orderflow-local-dev` pode centralizar:

- `docker-compose.yml` para todos os servicos
- `postgres` e `rabbitmq`
- `.env.example`
- scripts de bootstrap (`up`, `down`, `logs`)

## Criterios de pronto por fase

- Build local verde (`mvn test`).
- Subida local unica com Compose.
- Evento `order.created` processado por workers externos.
- Documentacao atualizada em cada PR.
