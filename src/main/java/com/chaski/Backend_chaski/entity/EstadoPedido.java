package com.chaski.Backend_chaski.entity;

public enum EstadoPedido {
    PENDIENTE_PAGO,
    CONFIRMADO_TIENDA,  // Pagado y esperando aceptación
    EN_PREPARACION,     // Aceptado por el restaurante
    LISTO_PARA_RECOGER, // Esperando delivery (o pickup)
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}


