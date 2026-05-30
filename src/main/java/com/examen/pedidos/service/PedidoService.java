package com.examen.pedidos.service;

import com.examen.pedidos.dto.EstadoRequestDTO;
import com.examen.pedidos.dto.PedidoRequestDTO;
import com.examen.pedidos.dto.PedidoResponseDTO;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.exception.PedidoNotFoundException;
import com.examen.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setCorreoCliente(dto.getCorreoCliente());
        pedido.setProductoId(dto.getProductoId());
        pedido.setNombreProducto(dto.getNombreProducto());
        pedido.setCantidad(dto.getCantidad());
        pedido.setPrecioUnitario(dto.getPrecioUnitario());
        // Total calculado en el backend
        BigDecimal total = dto.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(dto.getCantidad()));
        pedido.setTotal(total);
        pedido.setEstado("REGISTRADO");
        pedido.setFechaPedido(LocalDateTime.now());
        return mapToResponse(pedidoRepository.save(pedido));
    }

    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return mapToResponse(pedido);
    }
/*
    public PedidoResponseDTO actualizarEstado(Long id, EstadoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        pedido.setEstado(dto.getEstado().toUpperCase());
        return mapToResponse(pedidoRepository.save(pedido));
    }
    */

    public PedidoResponseDTO actualizarEstado(Long id, EstadoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        String nuevoEstado = dto.getEstado().toUpperCase().trim();
        String estadoActual = pedido.getEstado().toUpperCase().trim();

        // LÓGICA DE PROTECCIÓN (Asegura que el flujo sea lógico)

        // 1. Si quieren marcar como DEVUELTO, obligamos a que esté PAGADO primero
        if ("DEVUELTO".equals(nuevoEstado) && !"PAGADO".equals(estadoActual)) {
            throw new RuntimeException("Error: Solo se pueden devolver pedidos que ya están PAGADOS.");
        }

        // 2. Si quieren marcar como PAGADO, es válido (flujo normal)
        // 3. Si quieren marcar como CANCELADO, es válido solo si no estaba PAGADO
        if ("CANCELADO".equals(nuevoEstado) && "PAGADO".equals(estadoActual)) {
            throw new RuntimeException("Error: No se puede cancelar un pedido ya pagado.");
        }

        pedido.setEstado(nuevoEstado);
        return mapToResponse(pedidoRepository.save(pedido));
    }


    public void eliminar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        // Eliminación lógica: cambia estado a CANCELADO
        pedido.setEstado("CANCELADO");
        pedidoRepository.save(pedido);
    }

    private PedidoResponseDTO mapToResponse(Pedido p) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(p.getId());
        dto.setCliente(p.getCliente());
        dto.setCorreoCliente(p.getCorreoCliente());
        dto.setProductoId(p.getProductoId());
        dto.setNombreProducto(p.getNombreProducto());
        dto.setCantidad(p.getCantidad());
        dto.setPrecioUnitario(p.getPrecioUnitario());
        dto.setTotal(p.getTotal());
        dto.setEstado(p.getEstado());
        dto.setFechaPedido(p.getFechaPedido());
        return dto;
    }

}
