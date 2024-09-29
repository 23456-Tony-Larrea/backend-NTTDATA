package transacciones.backend.src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import transacciones.backend.src.entity.Movimientos;
import transacciones.backend.src.exception.SaldoNoDisponibleException;
import transacciones.backend.src.service.MovimientoService;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/{cuentaId}")
    public ResponseEntity<?> registrarMovimiento(@PathVariable Long cuentaId, @RequestBody Movimientos movimiento) {
        try {
            Movimientos nuevoMovimiento = movimientoService.createMovimiento(cuentaId, movimiento);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (SaldoNoDisponibleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    @GetMapping
    public List<Movimientos> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientoById(@PathVariable Long id) {
        return movimientoService.getMovimientoById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimiento(@PathVariable Long id, @RequestBody Movimientos movimientoDetails) {
        return movimientoService.updateMovimiento(id, movimientoDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
    }
}