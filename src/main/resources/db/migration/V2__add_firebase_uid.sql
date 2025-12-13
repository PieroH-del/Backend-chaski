-- Migration: Add Firebase UID and Active columns to usuarios table
-- Date: 2024
-- Description: Integración de Firebase Authentication

-- Agregar columna firebase_uid
ALTER TABLE usuarios
ADD firebase_uid VARCHAR(255);

-- Agregar columna activo
ALTER TABLE usuarios
ADD activo BIT DEFAULT 1;

-- Eliminar columna contrasena (ejecutar después de migrar todos los usuarios)
-- ALTER TABLE usuarios DROP COLUMN contrasena;

-- Agregar restricción unique después de migrar datos
-- ALTER TABLE usuarios
-- ADD CONSTRAINT uk_firebase_uid UNIQUE (firebase_uid);

-- Agregar índices para mejorar rendimiento
CREATE INDEX idx_firebase_uid ON usuarios(firebase_uid);
CREATE INDEX idx_email_lower ON usuarios(LOWER(email));

-- Nota: La columna contrasena se eliminará después de migrar todos los usuarios existentes
-- Para eliminarla manualmente después de la migración:
-- ALTER TABLE usuarios DROP COLUMN contrasena;

