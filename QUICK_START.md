 🚀 Guía de Inicio Rápido - Backend Chaski

## ⚡ Setup en 5 Minutos

### 1️⃣ Configurar Base de Datos

```sql
-- Abrir SQL Server Management Studio y ejecutar:
CREATE DATABASE chaski_db;
GO
USE chaski_db;
```

### 2️⃣ Configurar application.properties

Editar: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TU_PASSWORD_AQUI
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Iniciar el Backend

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### 4️⃣ Verificar que funciona

Abrir navegador en: http://localhost:8080/api/categorias

Deberías ver: `[]` (lista vacía pero funcionando)

---

## 🧪 Probar con Datos de Ejemplo

### Crear una Categoría

```bash
curl -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Hamburguesas",
    "imagenUrl": "https://example.com/hamburguesa.png"
  }'
```

### Crear un Restaurante

```bash
curl -X POST http://localhost:8080/api/restaurantes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Burger King",
    "descripcion": "Las mejores hamburguesas",
    "direccion": "Av. Arequipa 1234",
    "costoEnvioBase": 5.00,
    "tiempoEsperaMinutos": 30
  }'
```

### Registrar un Usuario

```bash
curl -X POST http://localhost:8080/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "telefono": "987654321"
  }'
```

### Ver Restaurantes

```bash
curl http://localhost:8080/api/restaurantes
```

---

## 📱 Endpoints Más Usados

| Funcionalidad | Método | Endpoint |
|---------------|--------|----------|
| Registrar usuario | POST | `/api/usuarios/registro` |
| Login | POST | `/api/usuarios/login/email?email=...` |
| Ver restaurantes | GET | `/api/restaurantes/abiertos` |
| Ver menú | GET | `/api/productos/restaurante/{id}/disponibles` |
| Crear pedido | POST | `/api/pedidos` |
| Ver estado pedido | GET | `/api/pedidos/{id}` |
| Procesar pago | POST | `/api/pagos/procesar` |

---

## 🔧 Comandos Útiles

### Compilar
```bash
.\mvnw.cmd clean compile
```

### Ejecutar
```bash
.\mvnw.cmd spring-boot:run
```

### Generar JAR
```bash
.\mvnw.cmd clean package
java -jar target/Backend-chaski-0.0.1-SNAPSHOT.jar
```

### Ver logs
El servidor muestra logs en consola automáticamente

---

## 📚 Documentación Completa

- **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** - Todos los endpoints para el frontend
- **[DATABASE_SETUP.md](DATABASE_SETUP.md)** - Scripts SQL y datos de prueba
- **[README.md](README.md)** - Guía completa del proyecto
- **[RESUMEN_EJECUTIVO.md](RESUMEN_EJECUTIVO.md)** - Resumen de todo lo implementado

---

## ⚠️ Solución de Problemas

### Error: "Cannot connect to database"
- Verifica que SQL Server esté corriendo
- Verifica usuario y contraseña en `application.properties`
- Verifica que la base de datos `chaski_db` exista

### Error: "Port 8080 already in use"
Cambiar puerto en `application.properties`:
```properties
server.port=8081
```

### Error de compilación
```bash
.\mvnw.cmd clean install -DskipTests
```

---

## ✅ Checklist de Inicio

- [ ] SQL Server instalado y corriendo
- [ ] Base de datos `chaski_db` creada
- [ ] `application.properties` configurado
- [ ] Java 21 instalado
- [ ] Proyecto compila sin errores
- [ ] Servidor inicia en puerto 8080
- [ ] Endpoints responden correctamente

---

## 🎯 Primer Flujo Completo

1. Registrar usuario
2. Crear dirección
3. Crear restaurante (opcional: usar datos de prueba)
4. Crear productos
5. Crear pedido
6. Procesar pago
7. Actualizar estado del pedido

Ver ejemplos completos en **API_DOCUMENTATION.md**

---

## 💡 Tips

- **Postman**: Importar endpoints para testing más fácil
- **H2 Console**: Considerar usar H2 para desarrollo rápido
- **Logs**: Activar logs SQL para debug: `spring.jpa.show-sql=true`
- **Hot Reload**: DevTools está configurado, los cambios se recargan automáticamente

---

¡Listo! Tu backend está funcionando 🚀

