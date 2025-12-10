package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.PagoDTO;
import com.chaski.Backend_chaski.entity.EstadoPago;
import com.chaski.Backend_chaski.entity.Pago;
import com.chaski.Backend_chaski.entity.Pedido;
import com.chaski.Backend_chaski.exception.BadRequestException;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.PagoMapper;
import com.chaski.Backend_chaski.repository.PagoRepository;
import com.chaski.Backend_chaski.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagoMapper pagoMapper;

    public PagoDTO obtenerPorId(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        return pagoMapper.toDTO(pago);
    }

    public PagoDTO obtenerPorPedidoId(Integer pedidoId) {
        Pago pago = pagoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado para el pedido: " + pedidoId));
        return pagoMapper.toDTO(pago);
    }

    public PagoDTO procesarPago(PagoDTO pagoDTO) {
        // Validar pedido
        Pedido pedido = pedidoRepository.findById(pagoDTO.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        // Verificar que no exista ya un pago para este pedido
        if (pagoRepository.findByPedidoId(pedido.getId()).isPresent()) {
            throw new BadRequestException("Ya existe un pago para este pedido");
        }

        // Crear pago
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(pagoDTO.getMonto());
        pago.setMetodo(pagoDTO.getMetodo());
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setFechaPago(LocalDateTime.now());

        // Simular procesamiento de pago
        // En producción, aquí se integraría con Stripe, MercadoPago, etc.
        boolean pagoExitoso = simularProcesoPago(pagoDTO);

        if (pagoExitoso) {
            pago.setEstado(EstadoPago.COMPLETADO);
            pago.setReferenciaPasarela("REF-" + System.currentTimeMillis());
        } else {
            pago.setEstado(EstadoPago.FALLIDO);
        }

        Pago pagoGuardado = pagoRepository.save(pago);
        return pagoMapper.toDTO(pagoGuardado);
    }

    private boolean simularProcesoPago(PagoDTO pagoDTO) {
        // Simulación: siempre exitoso
        // En producción, aquí se llamaría a la API de la pasarela de pagos
        return true;
    }

    public PagoDTO reembolsarPago(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));

        if (pago.getEstado() != EstadoPago.COMPLETADO) {
            throw new BadRequestException("Solo se pueden reembolsar pagos completados");
        }

        pago.setEstado(EstadoPago.REEMBOLSADO);
        Pago pagoActualizado = pagoRepository.save(pago);
        return pagoMapper.toDTO(pagoActualizado);
    }
}

