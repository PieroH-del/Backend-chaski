# 📚 API Backend Chaski - Documentación para Frontend

## 🌐 Base URL
```
https://chaski-eva-hghugee4aceme5g6.canadacentral-01.azurewebsites.net/api
```

---

## 📋 Índice
1. [Autenticación y Usuarios](#autenticación-y-usuarios)
2. [Direcciones](#direcciones)
3. [Restaurantes](#restaurantes)
4. [Categorías](#categorías)
5. [Productos](#productos)
6. [Pedidos](#pedidos)
7. [Pagos](#pagos)
8. [Modelos de Datos](#modelos-de-datos)
9. [Códigos de Estado](#códigos-de-estado)

---

## 🔐 Autenticación y Usuarios

### Registrar Usuario
```http
POST /api/usuarios/registro
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "telefono": "987654321",
  "imagenPerfilUrl": "https://example.com/foto.jpg"
}
```

**Respuesta:**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "telefono": "987654321",
  "imagenPerfilUrl": "https://example.com/foto.jpg",
  "fechaRegistro": "2025-12-08T21:30:00",
  "direcciones": []
}
```

### Login por Email
```http
POST /api/usuarios/login/email?email=juan@example.com
```

### Login por Teléfono
```http
POST /api/usuarios/login/telefono?telefono=987654321
```

### Obtener Usuario por ID
```http
GET /api/usuarios/{id}
```

### Actualizar Usuario
```http
PUT /api/usuarios/{id}
Content-Type: application/json

{
  "nombre": "Juan Carlos Pérez",
  "imagenPerfilUrl": "https://example.com/nueva-foto.jpg"
}
```

### Listar Todos los Usuarios
```http
GET /api/usuarios
```

### Eliminar Usuario
```http
DELETE /api/usuarios/{id}
```

---

## 📍 Direcciones

### Obtener Direcciones de un Usuario
```http
GET /api/direcciones/usuario/{usuarioId}
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "usuarioId": 1,
    "etiqueta": "Casa",
    "direccionCompleta": "Av. Arequipa 1234, Lima",
    "referencia": "Edificio azul al lado del parque",
    "esPredeterminada": true
  }
]
```

### Crear Dirección
```http
POST /api/direcciones
Content-Type: application/json

{
  "usuarioId": 1,
  "etiqueta": "Trabajo",
  "direccionCompleta": "Av. Javier Prado 456, San Isidro",
  "referencia": "Torre empresarial piso 5",
  "esPredeterminada": false
}
```

### Actualizar Dirección
```http
PUT /api/direcciones/{id}
Content-Type: application/json

{
  "etiqueta": "Oficina Nueva",
  "direccionCompleta": "Av. Primavera 789",
  "referencia": "Cerca al metro",
  "esPredeterminada": true
}
```

### Obtener Dirección por ID
```http
GET /api/direcciones/{id}
```

### Eliminar Dirección
```http
DELETE /api/direcciones/{id}
```

---

## 🍽️ Restaurantes

### Listar Todos los Restaurantes
```http
GET /api/restaurantes
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "nombre": "Burger King",
    "descripcion": "Las mejores hamburguesas",
    "imagenLogoUrl": "https://example.com/logo.png",
    "imagenPortadaUrl": "https://example.com/portada.jpg",
    "direccion": "Av. La Marina 2000",
    "calificacionPromedio": 4.5,
    "estaAbierto": true,
    "tiempoEsperaMinutos": 30,
    "costoEnvioBase": 5.00,
    "categorias": [
      {
        "id": 1,
        "nombre": "Hamburguesas",
        "imagenUrl": "https://example.com/hamburguesa.png"
      }
    ]
  }
]
```

### Listar Restaurantes Abiertos
```http
GET /api/restaurantes/abiertos
```

### Obtener Restaurante por ID
```http
GET /api/restaurantes/{id}
```

### Filtrar por Categoría
```http
GET /api/restaurantes/categoria/{categoriaId}
```

### Buscar Restaurantes
```http
GET /api/restaurantes/buscar?keyword=pizza
```

### Filtrar por Calificación
```http
GET /api/restaurantes/calificacion?minCalificacion=4.0
```

### Crear Restaurante (Admin)
```http
POST /api/restaurantes
Content-Type: application/json

{
  "nombre": "Pizza Hut",
  "descripcion": "Pizzas deliciosas",
  "imagenLogoUrl": "https://example.com/logo.png",
  "imagenPortadaUrl": "https://example.com/portada.jpg",
  "direccion": "Av. Colonial 123",
  "costoEnvioBase": 7.50,
  "tiempoEsperaMinutos": 40
}
```

### Actualizar Restaurante (Admin)
```http
PUT /api/restaurantes/{id}
Content-Type: application/json
```

### Cambiar Estado de Apertura (Gerente)
```http
PATCH /api/restaurantes/{id}/estado?estaAbierto=false
```

### Eliminar Restaurante (Admin)
```http
DELETE /api/restaurantes/{id}
```

---

## 🏷️ Categorías

### Listar Todas las Categorías
```http
GET /api/categorias
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "nombre": "Hamburguesas",
    "imagenUrl": "https://example.com/hamburguesa.png"
  },
  {
    "id": 2,
    "nombre": "Sushi",
    "imagenUrl": "https://example.com/sushi.png"
  }
]
```

### Obtener Categoría por ID
```http
GET /api/categorias/{id}
```

### Crear Categoría (Admin)
```http
POST /api/categorias
Content-Type: application/json

{
  "nombre": "Vegano",
  "imagenUrl": "https://example.com/vegano.png"
}
```

### Actualizar Categoría (Admin)
```http
PUT /api/categorias/{id}
Content-Type: application/json
```

### Eliminar Categoría (Admin)
```http
DELETE /api/categorias/{id}
```

---

## 🍕 Productos

### Listar Todos los Productos
```http
GET /api/productos
```

### Obtener Producto por ID
```http
GET /api/productos/{id}
```

**Respuesta:**
```json
{
  "id": 1,
  "restauranteId": 1,
  "restauranteNombre": "Burger King",
  "nombre": "Whopper Clásica",
  "descripcion": "Hamburguesa con carne a la parrilla",
  "precio": 18.90,
  "imagenUrl": "https://example.com/whopper.jpg",
  "disponible": true,
  "gruposOpciones": [
    {
      "id": 1,
      "productoId": 1,
      "nombre": "Elige tu salsa",
      "esObligatorio": false,
      "seleccionMinima": 0,
      "seleccionMaxima": 2,
      "opciones": [
        {
          "id": 1,
          "grupoOpcionId": 1,
          "nombre": "Ketchup",
          "precioExtra": 0.00
        },
        {
          "id": 2,
          "grupoOpcionId": 1,
          "nombre": "Extra Queso",
          "precioExtra": 2.00
        }
      ]
    }
  ]
}
```

### Obtener Productos por Restaurante
```http
GET /api/productos/restaurante/{restauranteId}
```

### Obtener Solo Productos Disponibles
```http
GET /api/productos/restaurante/{restauranteId}/disponibles
```

### Buscar Productos
```http
GET /api/productos/buscar?keyword=hamburguesa
```

### Crear Producto (Gerente)
```http
POST /api/productos
Content-Type: application/json

{
  "restauranteId": 1,
  "nombre": "Hamburguesa BBQ",
  "descripcion": "Con salsa BBQ especial",
  "precio": 22.90,
  "imagenUrl": "https://example.com/bbq.jpg",
  "disponible": true
}
```

### Actualizar Producto (Gerente)
```http
PUT /api/productos/{id}
Content-Type: application/json
```

### Cambiar Disponibilidad (Gerente)
```http
PATCH /api/productos/{id}/disponibilidad?disponible=false
```

### Eliminar Producto (Gerente)
```http
DELETE /api/productos/{id}
```

---

## 🛒 Pedidos

### Crear Pedido
```http
POST /api/pedidos
Content-Type: application/json

{
  "usuarioId": 1,
  "restauranteId": 1,
  "direccionEntregaId": 1,
  "notasInstrucciones": "Sin cebolla por favor",
  "detallesPedido": [
    {
      "productoId": 1,
      "cantidad": 2,
      "opcionesDetallePedido": [
        {
          "opcionId": 2,
          "precioExtraCobrado": 2.00
        }
      ]
    }
  ]
}
```

**Respuesta:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "usuarioNombre": "Juan Pérez",
  "restauranteId": 1,
  "restauranteNombre": "Burger King",
  "direccionEntregaId": 1,
  "direccionEntregaCompleta": "Av. Arequipa 1234, Lima",
  "subtotalProductos": 37.80,
  "costoEnvio": 5.00,
  "impuestos": 6.80,
  "totalFinal": 49.60,
  "estado": "PENDIENTE_PAGO",
  "notasInstrucciones": "Sin cebolla por favor",
  "fechaCreacion": "2025-12-08T21:30:00",
  "fechaActualizacion": "2025-12-08T21:30:00",
  "detallesPedido": [...]
}
```

### Obtener Pedido por ID
```http
GET /api/pedidos/{id}
```

### Obtener Pedidos del Usuario
```http
GET /api/pedidos/usuario/{usuarioId}
```

### Obtener Pedidos del Restaurante
```http
GET /api/pedidos/restaurante/{restauranteId}
```

### Obtener Pedidos Recientes del Restaurante
```http
GET /api/pedidos/restaurante/{restauranteId}/recientes?horasAtras=24
```

### Obtener Pedidos por Estado
```http
GET /api/pedidos/estado/EN_PREPARACION
```

**Estados disponibles:**
- `PENDIENTE_PAGO`
- `CONFIRMADO_TIENDA`
- `EN_PREPARACION`
- `LISTO_PARA_RECOGER`
- `EN_CAMINO`
- `ENTREGADO`
- `CANCELADO`

### Confirmar Pedido (Gerente)
```http
PATCH /api/pedidos/{id}/confirmar
```

### Marcar en Preparación (Gerente)
```http
PATCH /api/pedidos/{id}/preparacion
```

### Marcar Listo para Recoger (Gerente)
```http
PATCH /api/pedidos/{id}/listo
```

### Marcar en Camino (Repartidor)
```http
PATCH /api/pedidos/{id}/en-camino
```

### Marcar Entregado (Repartidor)
```http
PATCH /api/pedidos/{id}/entregado
```

### Cancelar Pedido
```http
PATCH /api/pedidos/{id}/cancelar
```

### Cambiar Estado Manualmente
```http
PATCH /api/pedidos/{id}/estado?estado=EN_PREPARACION
```

---

## 💳 Pagos

### Procesar Pago
```http
POST /api/pagos/procesar
Content-Type: application/json

{
  "pedidoId": 1,
  "monto": 49.60,
  "metodo": "TARJETA_CREDITO"
}
```

**Métodos de pago disponibles:**
- `TARJETA_CREDITO`
- `TARJETA_DEBITO`
- `YAPE`
- `EFECTIVO`

**Respuesta:**
```json
{
  "id": 1,
  "pedidoId": 1,
  "monto": 49.60,
  "metodo": "TARJETA_CREDITO",
  "estado": "COMPLETADO",
  "referenciaPasarela": "REF-1702073400000",
  "fechaPago": "2025-12-08T21:30:00"
}
```

### Obtener Pago por ID
```http
GET /api/pagos/{id}
```

### Obtener Pago por Pedido
```http
GET /api/pagos/pedido/{pedidoId}
```

### Reembolsar Pago (Admin)
```http
POST /api/pagos/{id}/reembolsar
```

---

## 📊 Modelos de Datos

### Usuario
```typescript
interface Usuario {
  id: number;
  nombre: string;
  email: string;
  telefono: string;
  imagenPerfilUrl?: string;
  fechaRegistro: string; // ISO 8601
  direcciones?: Direccion[];
}
```

### Dirección
```typescript
interface Direccion {
  id: number;
  usuarioId: number;
  etiqueta: string; // "Casa", "Trabajo", etc.
  direccionCompleta: string;
  referencia?: string;
  esPredeterminada: boolean;
}
```

### Restaurante
```typescript
interface Restaurante {
  id: number;
  nombre: string;
  descripcion: string;
  imagenLogoUrl?: string;
  imagenPortadaUrl?: string;
  direccion: string;
  calificacionPromedio: number;
  estaAbierto: boolean;
  tiempoEsperaMinutos: number;
  costoEnvioBase: number;
  categorias?: Categoria[];
}
```

### Categoría
```typescript
interface Categoria {
  id: number;
  nombre: string;
  imagenUrl?: string;
}
```

### Producto
```typescript
interface Producto {
  id: number;
  restauranteId: number;
  restauranteNombre: string;
  nombre: string;
  descripcion: string;
  precio: number;
  imagenUrl?: string;
  disponible: boolean;
  gruposOpciones?: GrupoOpcion[];
}
```

### Grupo de Opciones
```typescript
interface GrupoOpcion {
  id: number;
  productoId: number;
  nombre: string; // "Elige tu salsa", "Adicionales"
  esObligatorio: boolean;
  seleccionMinima: number;
  seleccionMaxima: number;
  opciones: Opcion[];
}
```

### Opción
```typescript
interface Opcion {
  id: number;
  grupoOpcionId: number;
  nombre: string; // "Ketchup", "Extra Queso"
  precioExtra: number;
}
```

### Pedido
```typescript
interface Pedido {
  id: number;
  usuarioId: number;
  usuarioNombre: string;
  restauranteId: number;
  restauranteNombre: string;
  direccionEntregaId: number;
  direccionEntregaCompleta: string;
  subtotalProductos: number;
  costoEnvio: number;
  impuestos: number;
  totalFinal: number;
  estado: EstadoPedido;
  notasInstrucciones?: string;
  fechaCreacion: string;
  fechaActualizacion: string;
  detallesPedido: DetallePedido[];
  pago?: Pago;
}

type EstadoPedido = 
  | "PENDIENTE_PAGO"
  | "CONFIRMADO_TIENDA"
  | "EN_PREPARACION"
  | "LISTO_PARA_RECOGER"
  | "EN_CAMINO"
  | "ENTREGADO"
  | "CANCELADO";
```

### Detalle de Pedido
```typescript
interface DetallePedido {
  id: number;
  pedidoId: number;
  productoId: number;
  productoNombre: string;
  cantidad: number;
  precioUnitario: number;
  opcionesDetallePedido?: OpcionDetallePedido[];
}
```

### Opción Detalle Pedido
```typescript
interface OpcionDetallePedido {
  id: number;
  detallePedidoId: number;
  opcionId: number;
  opcionNombre: string;
  precioExtraCobrado: number;
}
```

### Pago
```typescript
interface Pago {
  id: number;
  pedidoId: number;
  monto: number;
  metodo: MetodoPago;
  estado: EstadoPago;
  referenciaPasarela?: string;
  fechaPago: string;
}

type MetodoPago = 
  | "TARJETA_CREDITO"
  | "TARJETA_DEBITO"
  | "YAPE"
  | "EFECTIVO";

type EstadoPago = 
  | "PENDIENTE"
  | "COMPLETADO"
  | "FALLIDO"
  | "REEMBOLSADO";
```

---

## 🚦 Códigos de Estado HTTP

- **200 OK**: Solicitud exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Eliminación exitosa
- **400 Bad Request**: Error en los datos enviados
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error del servidor

### Formato de Error
```json
{
  "timestamp": "2025-12-08T21:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario no encontrado con id: 999"
}
```

---

## 🔄 Flujo Completo: Realizar un Pedido

### 1. Usuario se registra/inicia sesión
```http
POST /api/usuarios/registro
POST /api/usuarios/login/email?email=usuario@example.com
```

### 2. Usuario agrega/selecciona dirección de entrega
```http
POST /api/direcciones
GET /api/direcciones/usuario/{usuarioId}
```

### 3. Usuario explora restaurantes
```http
GET /api/restaurantes/abiertos
GET /api/restaurantes/categoria/1
```

### 4. Usuario ve el menú del restaurante
```http
GET /api/productos/restaurante/{restauranteId}/disponibles
GET /api/productos/{productoId}
```

### 5. Usuario crea el pedido
```http
POST /api/pedidos
```

### 6. Usuario procesa el pago
```http
POST /api/pagos/procesar
```

### 7. Usuario monitorea el estado del pedido
```http
GET /api/pedidos/{pedidoId}
```

### 8. Gerente actualiza el estado
```http
PATCH /api/pedidos/{pedidoId}/confirmar
PATCH /api/pedidos/{pedidoId}/preparacion
PATCH /api/pedidos/{pedidoId}/listo
```

### 9. Repartidor actualiza el estado
```http
PATCH /api/pedidos/{pedidoId}/en-camino
PATCH /api/pedidos/{pedidoId}/entregado
```

---

## 🎯 Ejemplos de Uso con JavaScript/TypeScript

### Fetch API (JavaScript)

```javascript
// Registrar usuario
async function registrarUsuario(usuario) {
  const response = await fetch('http://localhost:8080/api/usuarios/registro', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(usuario)
  });
  return await response.json();
}

// Obtener restaurantes abiertos
async function obtenerRestaurantesAbiertos() {
  const response = await fetch('http://localhost:8080/api/restaurantes/abiertos');
  return await response.json();
}

// Crear pedido
async function crearPedido(pedido) {
  const response = await fetch('http://localhost:8080/api/pedidos', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(pedido)
  });
  return await response.json();
}
```

### Axios (TypeScript)

```typescript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Obtener productos de un restaurante
const obtenerProductos = async (restauranteId: number) => {
  const { data } = await api.get(`/productos/restaurante/${restauranteId}/disponibles`);
  return data;
};

// Actualizar estado de pedido
const actualizarEstadoPedido = async (pedidoId: number, estado: string) => {
  const { data } = await api.patch(`/pedidos/${pedidoId}/estado`, null, {
    params: { estado }
  });
  return data;
};
```

---

## 🔔 Notificaciones en Tiempo Real (Próximamente)

Para implementar notificaciones en tiempo real, se recomienda usar **WebSockets** o **Server-Sent Events (SSE)**.

### Eventos a Implementar:
- `nuevo_pedido`: Cuando llega un nuevo pedido al restaurante
- `cambio_estado_pedido`: Cuando cambia el estado del pedido
- `pedido_cancelado`: Cuando se cancela un pedido
- `repartidor_asignado`: Cuando se asigna un repartidor

---

## 📝 Notas Importantes

1. **CORS**: La API está configurada para aceptar peticiones de cualquier origen (`*`). En producción, configura los orígenes permitidos.

2. **Autenticación**: Actualmente no hay autenticación JWT. Se recomienda implementar Spring Security con JWT para producción.

3. **Validaciones**: Agrega validaciones en el frontend antes de enviar datos.

4. **Imágenes**: Las URLs de imágenes deben apuntar a servicios de almacenamiento como AWS S3, Cloudinary, etc.

5. **Pagos**: La integración de pagos es simulada. Implementa Stripe, MercadoPago o similar para producción.

6. **Base de Datos**: Configura la conexión a tu base de datos en `application.properties`.

---

## 🛠️ Configuración Recomendada

### application.properties
```properties
# Base de datos
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=chaski
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Puerto del servidor
server.port=8080
```

---

## 📧 Soporte

Para dudas o problemas con la API, contacta al equipo de backend.

**Última actualización**: 2025-12-08

