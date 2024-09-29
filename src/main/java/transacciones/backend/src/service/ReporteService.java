package transacciones.backend.src.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import transacciones.backend.src.config.RabbitMQConfig;
import transacciones.backend.src.dto.ReporteEstadoCuentaDTO;
import transacciones.backend.src.dto.ReporteRequestDTO;
import transacciones.backend.src.dto.ReporteEstadoCuentaDTO.CuentaDTO;
import transacciones.backend.src.dto.ReporteEstadoCuentaDTO.MovimientoDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public ReporteEstadoCuentaDTO generarReporteEstadoCuenta(String clienteNombre, Date fechaInicio, Date fechaFin, List<ReporteRequestDTO.CuentaDTO> cuentas) {
        ReporteEstadoCuentaDTO reporte = new ReporteEstadoCuentaDTO();
        reporte.setClienteNombre(clienteNombre);

        List<CuentaDTO> cuentasDTO = cuentas.stream().map(cuenta -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDTO.setEstado(cuenta.isEstado());

            List<MovimientoDTO> movimientosDTO = cuenta.getMovimientos().stream()
                    .filter(movimiento -> !movimiento.getFecha().before(fechaInicio) && !movimiento.getFecha().after(fechaFin))
                    .map(movimiento -> {
                        MovimientoDTO movimientoDTO = new MovimientoDTO();
                        movimientoDTO.setFecha(movimiento.getFecha());
                        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
                        movimientoDTO.setValor(movimiento.getValor());
                        movimientoDTO.setSaldo(movimiento.getSaldo());
                        return movimientoDTO;
                    }).collect(Collectors.toList());

            cuentaDTO.setMovimientos(movimientosDTO);
            return cuentaDTO;
        }).collect(Collectors.toList());

        reporte.setCuentas(cuentasDTO);
        return reporte;
    }

    @RabbitListener(queues = "reporte-queue")
    public void receiveMessage(ReporteRequestDTO request) {
        ReporteEstadoCuentaDTO reporte = generarReporteEstadoCuenta(request.getClienteNombre(), request.getFechaInicio(), request.getFechaFin(), request.getCuentas());
        System.out.println("Reporte generado: " + reporte);
    }

    public void solicitarReporte(ReporteRequestDTO request) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, request);
    }

    public List<ReporteRequestDTO.CuentaDTO> obtenerCuentasDelCliente(String clienteNombre) {
        return List.of();
    }
}