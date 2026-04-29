-- Bootstrap inicial para ambiente local com banco compartilhado.
-- Ajuste senhas antes de uso fora de ambiente local.

CREATE SCHEMA IF NOT EXISTS catalog;
CREATE SCHEMA IF NOT EXISTS orders;
CREATE SCHEMA IF NOT EXISTS inventory;
CREATE SCHEMA IF NOT EXISTS notification;

DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'catalog_user') THEN
        CREATE ROLE catalog_user LOGIN PASSWORD 'catalog123';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'orders_user') THEN
        CREATE ROLE orders_user LOGIN PASSWORD 'orders123';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'inventory_user') THEN
        CREATE ROLE inventory_user LOGIN PASSWORD 'inventory123';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'notification_user') THEN
        CREATE ROLE notification_user LOGIN PASSWORD 'notification123';
    END IF;
END
$$;

GRANT USAGE, CREATE ON SCHEMA catalog TO catalog_user;
GRANT USAGE, CREATE ON SCHEMA orders TO orders_user;
GRANT USAGE, CREATE ON SCHEMA inventory TO inventory_user;
GRANT USAGE, CREATE ON SCHEMA notification TO notification_user;
