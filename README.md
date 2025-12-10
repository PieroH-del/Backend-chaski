# 🚀 Backend Chaski - API de Delivery

Sistema backend completo para aplicación de delivery de comida, desarrollado con Spring Boot, JPA, MapStruct y SQL Server.

## 📋 Tabla de Contenidos
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecutar el Proyecto](#ejecutar-el-proyecto)
- [Documentación de la API](#documentación-de-la-api)
- [Testing](#testing)

---

## ✨ Características

### Para Clientes
- ✅ Registro e inicio de sesión por email o teléfono
- ✅ Gestión de múltiples direcciones de entrega
- ✅ Exploración de restaurantes abiertos
- ✅ Filtrado por categorías (Hamburguesas, Sushi, Pizza, etc.)
- ✅ Búsqueda de restaurantes y productos
- ✅ Filtrado por calificación
- ✅ Visualización de menú con opciones personalizables
- ✅ Carrito con cálculo de subtotal, envío e impuestos
- ✅ Múltiples métodos de pago (Tarjeta, Yape, Efectivo)
- ✅ Seguimiento de pedidos en tiempo real

### Para Restaurantes
- ✅ Gestión de estado de apertura
- ✅ Recepción de nuevos pedidos
- ✅ Actualización de estado de pedidos
- ✅ Edición de menú en tiempo real
- ✅ Activar/desactivar productos agotados
- ✅ Consulta de pedidos recientes

### Para Administradores
- ✅ Gestión de restaurantes
- ✅ Gestión de categorías
- ✅ Gestión de usuarios
- ✅ Procesamiento de reembolsos

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 4.0.0**
  - Spring Web MVC
  - Spring Data JPA
  - Spring DevTools
- **MapStruct 1.5.5** - Mapeo automático Entity ↔ DTO
- **Lombok** - Reducción de código boilerplate
- **SQL Server** - Base de datos
- **Maven** - Gestión de dependencias

---

## 📁 Estructura del Proyecto

```
src/main/java/com/chaski/Backend_chaski/
├── config/              # Configuraciones (CORS, etc.)
├── controller/          # Controladores REST
│   ├── UsuarioController.java
│   ├── DireccionController.java
│   ├── RestauranteController.java
│   ├── CategoriaController.java
│   ├── ProductoController.java
│   ├── PedidoController.java
│   └── PagoController.java
├── dto/                 # Data Transfer Objects
│   ├── UsuarioDTO.java
│   ├── DireccionDTO.java
│   ├── RestauranteDTO.java
│   ├── ProductoDTO.java
│   ├── PedidoDTO.java
│   └── ...
├── entity/              # Entidades JPA
│   ├── Usuario.java
│   ├── Direccion.java
│   ├── Restaurante.java
│   ├── Producto.java
│   ├── Pedido.java
│   └── ...
├── exception/           # Manejo de excepciones
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   └── GlobalExceptionHandler.java
├── mapper/              # Mappers MapStruct
│   ├── UsuarioMapper.java
│   ├── RestauranteMapper.java
│   ├── ProductoMapper.java
│   └── ...
├── repository/          # Repositorios JPA
│   ├── UsuarioRepository.java
│   ├── RestauranteRepository.java
│   ├── ProductoRepository.java
│   └── ...
├── service/             # Lógica de negocio
│   ├── UsuarioService.java
│   ├── RestauranteService.java
│   ├── ProductoService.java
│   ├── PedidoService.java
│   └── ...
└── BackendChaskiApplication.java
```

---

## 🔧 Instalación

### Prerrequisitos
- Java 21 o superior
- Maven 3.8+
- SQL Server 2019 o superior
- Git

### Pasos

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd Backend-chaski
```

2. **Configurar SQL Server**
   - Crear la base de datos `chaski_db`
   - Ver detalles en [DATABASE_SETUP.md](DATABASE_SETUP.md)

3. **Configurar application.properties**
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

4. **Compilar el proyecto**
```bash
./mvnw clean install
```

---

## ⚙️ Configuración

### application.properties

```properties
# Base de datos
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TuPassword123
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

# Servidor
server.port=8080

# Logs
logging.level.org.hibernate.SQL=DEBUG
```

---

## 🚀 Ejecutar el Proyecto

### Modo Desarrollo

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Compilar JAR

```bash
./mvnw clean package
java -jar target/Backend-chaski-0.0.1-SNAPSHOT.jar
```

El servidor estará disponible en: `http://localhost:8080`

---

## 📚 Documentación de la API

La documentación completa de todos los endpoints está disponible en:
- **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** - Documentación completa para el frontend

### Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/usuarios/registro` | Registrar nuevo usuario |
| POST | `/api/usuarios/login/email` | Login por email |
| GET | `/api/restaurantes/abiertos` | Listar restaurantes abiertos |
| GET | `/api/productos/restaurante/{id}/disponibles` | Menú de restaurante |
| POST | `/api/pedidos` | Crear nuevo pedido |
| PATCH | `/api/pedidos/{id}/confirmar` | Confirmar pedido |
| POST | `/api/pagos/procesar` | Procesar pago |

Ver documentación completa en [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

---

## 🧪 Testing

### Probar con cURL

**Registrar Usuario:**
```bash
curl -X POST http://localhost:8080/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "telefono": "987654321"
  }'
```

**Obtener Restaurantes:**
```bash
curl http://localhost:8080/api/restaurantes/abiertos
```

### Probar con Postman

1. Importar la colección (próximamente)
2. Configurar la variable `base_url` = `http://localhost:8080/api`
3. Ejecutar las pruebas

---

## 📊 Modelo de Datos

### Diagrama de Entidades

```
Usuario (1) ──── (N) Direccion
   │
   │ (1)
   │
   └─── (N) Pedido (N) ──── (1) Restaurante
              │                      │
              │ (1)                  │ (1)
              │                      │
              └── (N) DetallePedido  └── (N) Producto
                         │                      │
                         │ (N)                  │ (1)
                         │                      │
                         └── (1) Opcion ←──(N)──┘
```

---

## 🔐 Seguridad

⚠️ **Nota**: Actualmente no hay autenticación implementada.

### Para Producción:
- Implementar Spring Security
- Agregar autenticación JWT
- Configurar roles (USER, ADMIN, RESTAURANT_MANAGER)
- HTTPS obligatorio
- Rate limiting
- Validación de datos más estricta

---

## 🌐 CORS

El backend está configurado para aceptar peticiones de cualquier origen (`*`).

Para producción, modificar en `WebConfig.java`:
```java
.allowedOrigins("https://tu-frontend.com")
```

---

## 📝 Scripts de Base de Datos

Ver scripts completos en [DATABASE_SETUP.md](DATABASE_SETUP.md):
- Creación de base de datos
- Datos de prueba
- Consultas útiles
- Limpieza de datos

---

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto es privado y confidencial.

---

## 👥 Equipo

- **Backend Developer**: Tu Nombre
- **Frontend Developer**: Nombre del equipo frontend
- **Database Administrator**: Nombre del DBA

---

## 📞 Contacto

Para dudas o consultas:
- Email: tu-email@example.com
- Slack: #backend-chaski

---

## 🔄 Próximas Funcionalidades

- [ ] Autenticación JWT
- [ ] WebSockets para notificaciones en tiempo real
- [ ] Integración con pasarelas de pago (Stripe, MercadoPago)
- [ ] Sistema de calificaciones y reseñas
- [ ] Panel de administración
- [ ] Reportes y estadísticas
- [ ] Sistema de cupones y descuentos
- [ ] Geolocalización de repartidores
- [ ] Chat entre cliente y repartidor

---

## 📈 Estado del Proyecto

✅ **Completado**: Entidades, DTOs, Mappers, Repositories, Services, Controllers
🔄 **En Progreso**: Testing, Documentación
⏳ **Pendiente**: Autenticación, Notificaciones en tiempo real

---

**Última actualización**: 2025-12-08

