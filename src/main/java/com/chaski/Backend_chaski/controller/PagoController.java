package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.PagoDTO;
import com.chaski.Backend_chaski.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagoDTO> obtenerPorPedido(@PathVariable Integer pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPorPedidoId(pedidoId));
    }

    @PostMapping("/procesar")
    public ResponseEntity<PagoDTO> procesarPago(@RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(pagoDTO));
    }

    @PostMapping("/{id}/reembolsar")
    public ResponseEntity<PagoDTO> reembolsar(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.reembolsarPago(id));
    }
}
