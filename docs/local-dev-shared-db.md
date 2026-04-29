# Local Dev - Banco Compartilhado por Schemas

## Visao geral

Neste modelo, todos os servicos usam o mesmo PostgreSQL, com separacao logica por schema.

## Schemas iniciais sugeridos

- `catalog`
- `orders`
- `inventory`
- `notification`

## Estrategia de acesso

- Cada servico conecta com usuario dedicado.
- Cada usuario tem permissao total apenas no seu schema.
- O schema `public` fica sem ownership de dominio.

## Bootstrap SQL

Use o script [`docs/sql/init-schemas.sql`](./sql/init-schemas.sql) como base para:

- Criar schemas
- Criar roles/usuarios por servico
- Conceder grants minimos necessarios

## Convencoes recomendadas

1. Nome de tabela com semantica de dominio, sem prefixo redundante.
2. Chaves externas apenas dentro do mesmo schema.
3. Integracao entre servicos por API/evento.
4. Evitar consultas analiticas cross-schema dentro dos servicos.

## Como evoluir

- Cada repositorio de servico mantem suas migracoes Flyway no proprio codigo.
- O ambiente local aplica migracoes na subida de cada servico.
- Alteracoes de contrato entre schemas devem ser tratadas como evento/API, nao por leitura direta.
