package transacciones.backend.src.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import transacciones.backend.src.dto.ReporteRequestDTO;
import transacciones.backend.src.entity.Cuenta;
import transacciones.backend.src.entity.Movimientos;
import transacciones.backend.src.exception.SaldoNoDisponibleException;
import transacciones.backend.src.repository.CuentaRepository;
import transacciones.backend.src.repository.MovimientosRepository;

import java.util.Date;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientosRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimientos> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public ResponseEntity<Movimientos> getMovimientoById(Long id) {
        return movimientoRepository.findById(id)
                .map(movimiento -> ResponseEntity.ok().body(movimiento))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public Movimientos createMovimiento(Long cuentaId, Movimientos movimiento) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        if (nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(new Date());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);
        return movimientoRepository.save(movimiento);
    }

    public ResponseEntity<Movimientos> updateMovimiento(Long id, Movimientos movimientoDetails) {
        return movimientoRepository.findById(id)
                .map(movimiento -> {
                    movimiento.setFecha(movimientoDetails.getFecha());
                    movimiento.setTipoMovimiento(movimientoDetails.getTipoMovimiento());
                    movimiento.setValor(movimientoDetails.getValor());
                    movimiento.setSaldo(movimientoDetails.getSaldo());
                    Movimientos updatedMovimiento = movimientoRepository.save(movimiento);
                    return ResponseEntity.ok().body(updatedMovimiento);
                }).orElse(ResponseEntity.notFound().build());
    }

    public void deleteMovimiento(Long id) {
       movimientoRepository.deleteById(id);
    }
    @RabbitListener(queues = "cliente-cuenta-queue")
    public void receiveMessage(ReporteRequestDTO cliente) {
        System.out.println("Received message: " + cliente);
    }   
}
