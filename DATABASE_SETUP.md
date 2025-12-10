 Configuración de la Base de Datos - Backend Chaski

## SQL Server Configuration

### application.properties
```properties
# Configuración de SQL Server
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TuPassword123

# Configuración del driver
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Configuración de JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

# Puerto del servidor
server.port=8080

# Configuración de logs
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
</properties>

## Crear la Base de Datos

```sql
-- Crear la base de datos
CREATE DATABASE chaski_db;
GO

USE chaski_db;
GO
```

## Scripts SQL para Datos de Prueba

### Insertar Categorías
```sql
INSERT INTO categorias (nombre, imagen_url) VALUES
('Hamburguesas', 'https://example.com/hamburguesa.png'),
('Pizza', 'https://example.com/pizza.png'),
('Sushi', 'https://example.com/sushi.png'),
('Vegano', 'https://example.com/vegano.png'),
('Postres', 'https://example.com/postres.png');
```

### Insertar Restaurantes
```sql
INSERT INTO restaurantes (nombre, descripcion, imagen_logo_url, imagen_portada_url, direccion, calificacion_promedio, esta_abierto, tiempo_espera_minutos, costo_envio_base) VALUES
('Burger King', 'Las mejores hamburguesas', 'https://example.com/bk-logo.png', 'https://example.com/bk-portada.jpg', 'Av. Arequipa 1234, Miraflores', 4.5, 1, 30, 5.00),
('Pizza Hut', 'Pizzas deliciosas', 'https://example.com/ph-logo.png', 'https://example.com/ph-portada.jpg', 'Av. Larco 456, Miraflores', 4.3, 1, 40, 7.50),
('Sushi Master', 'Sushi fresco y auténtico', 'https://example.com/sm-logo.png', 'https://example.com/sm-portada.jpg', 'Av. La Mar 789, Miraflores', 4.8, 1, 35, 8.00);
```

### Insertar Relación Restaurante-Categoría
```sql
-- Burger King -> Hamburguesas
INSERT INTO restaurante_categorias (restaurante_id, categoria_id) VALUES (1, 1);

-- Pizza Hut -> Pizza
INSERT INTO restaurante_categorias (restaurante_id, categoria_id) VALUES (2, 2);

-- Sushi Master -> Sushi
INSERT INTO restaurante_categorias (restaurante_id, categoria_id) VALUES (3, 3);
```

### Insertar Productos
```sql
-- Productos de Burger King
INSERT INTO productos (restaurante_id, nombre, descripcion, precio, imagen_url, disponible) VALUES
(1, 'Whopper Clásica', 'Hamburguesa con carne a la parrilla, tomate, lechuga y cebolla', 18.90, 'https://example.com/whopper.jpg', 1),
(1, 'Whopper Doble', 'Doble carne a la parrilla', 24.90, 'https://example.com/whopper-doble.jpg', 1),
(1, 'Papas Fritas Grandes', 'Papas fritas crujientes', 6.90, 'https://example.com/papas.jpg', 1);

-- Productos de Pizza Hut
INSERT INTO productos (restaurante_id, nombre, descripcion, precio, imagen_url, disponible) VALUES
(2, 'Pizza Pepperoni Personal', 'Pizza con pepperoni', 19.90, 'https://example.com/pepperoni.jpg', 1),
(2, 'Pizza Hawaiana Mediana', 'Pizza con jamón y piña', 29.90, 'https://example.com/hawaiana.jpg', 1);

-- Productos de Sushi Master
INSERT INTO productos (restaurante_id, nombre, descripcion, precio, imagen_url, disponible) VALUES
(3, 'Rolls Mixto 20 piezas', 'Variedad de rolls', 45.00, 'https://example.com/rolls.jpg', 1),
(3, 'Sashimi de Salmón', 'Sashimi fresco', 35.00, 'https://example.com/sashimi.jpg', 1);
```

### Insertar Grupos de Opciones
```sql
-- Opciones para Whopper Clásica
INSERT INTO grupos_opciones (producto_id, nombre, es_obligatorio, seleccion_minima, seleccion_maxima) VALUES
(1, 'Elige tu salsa', 0, 0, 2),
(1, 'Adicionales', 0, 0, 3);
```

### Insertar Opciones
```sql
-- Opciones para "Elige tu salsa"
INSERT INTO opciones (grupo_opcion_id, nombre, precio_extra) VALUES
(1, 'Ketchup', 0.00),
(1, 'Mayonesa', 0.00),
(1, 'Mostaza', 0.00),
(1, 'BBQ', 1.00);

-- Opciones para "Adicionales"
INSERT INTO opciones (grupo_opcion_id, nombre, precio_extra) VALUES
(2, 'Extra Queso', 2.00),
(2, 'Tocino', 3.00),
(2, 'Huevo Frito', 2.50);
```

### Insertar Usuario de Prueba
```sql
INSERT INTO usuarios (nombre, email, telefono, imagen_perfil_url, fecha_registro) VALUES
('Juan Pérez', 'juan@example.com', '987654321', 'https://example.com/juan.jpg', GETDATE());
```

### Insertar Dirección de Prueba
```sql
INSERT INTO direcciones (usuario_id, etiqueta, direccion_completa, referencia, es_predeterminada) VALUES
(1, 'Casa', 'Av. Benavides 1234, Miraflores', 'Edificio azul al lado del parque', 1);
```

## Consultas Útiles

### Ver todos los restaurantes con sus categorías
```sql
SELECT 
    r.nombre AS restaurante,
    c.nombre AS categoria,
    r.calificacion_promedio,
    r.esta_abierto,
    r.costo_envio_base
FROM restaurantes r
INNER JOIN restaurante_categorias rc ON r.id = rc.restaurante_id
INNER JOIN categorias c ON rc.categoria_id = c.id;
```

### Ver productos con sus opciones
```sql
SELECT 
    p.nombre AS producto,
    p.precio,
    go.nombre AS grupo_opcion,
    o.nombre AS opcion,
    o.precio_extra
FROM productos p
LEFT JOIN grupos_opciones go ON p.id = go.producto_id
LEFT JOIN opciones o ON go.id = o.grupo_opcion_id
WHERE p.restaurante_id = 1;
```

### Ver pedidos con detalles
```sql
SELECT 
    p.id AS pedido_id,
    u.nombre AS cliente,
    r.nombre AS restaurante,
    p.estado,
    p.total_final,
    p.fecha_creacion
FROM pedidos p
INNER JOIN usuarios u ON p.usuario_id = u.id
INNER JOIN restaurantes r ON p.restaurante_id = r.id
ORDER BY p.fecha_creacion DESC;
```

### Ver detalles de un pedido específico
```sql
SELECT 
    dp.id,
    pr.nombre AS producto,
    dp.cantidad,
    dp.precio_unitario,
    (dp.cantidad * dp.precio_unitario) AS subtotal
FROM detalles_pedido dp
INNER JOIN productos pr ON dp.producto_id = pr.id
WHERE dp.pedido_id = 1;
```

## Verificar Estructura de Tablas

```sql
-- Ver todas las tablas
SELECT TABLE_NAME 
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_TYPE = 'BASE TABLE'
ORDER BY TABLE_NAME;

-- Ver columnas de una tabla específica
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'pedidos'
ORDER BY ORDINAL_POSITION;
```

## Limpiar Datos (Para Testing)

```sql
-- CUIDADO: Esto eliminará todos los datos
DELETE FROM opciones_detalle_pedido;
DELETE FROM opciones;
DELETE FROM grupos_opciones;
DELETE FROM detalles_pedido;
DELETE FROM pagos;
DELETE FROM pedidos;
DELETE FROM productos;
DELETE FROM restaurante_categorias;
DELETE FROM direcciones;
DELETE FROM usuarios;
DELETE FROM restaurantes;
DELETE FROM categorias;

-- Reiniciar los contadores de identidad
DBCC CHECKIDENT ('opciones_detalle_pedido', RESEED, 0);
DBCC CHECKIDENT ('opciones', RESEED, 0);
DBCC CHECKIDENT ('grupos_opciones', RESEED, 0);
DBCC CHECKIDENT ('detalles_pedido', RESEED, 0);
DBCC CHECKIDENT ('pagos', RESEED, 0);
DBCC CHECKIDENT ('pedidos', RESEED, 0);
DBCC CHECKIDENT ('productos', RESEED, 0);
DBCC CHECKIDENT ('direcciones', RESEED, 0);
DBCC CHECKIDENT ('usuarios', RESEED, 0);
DBCC CHECKIDENT ('restaurantes', RESEED, 0);
DBCC CHECKIDENT ('categorias', RESEED, 0);
```

