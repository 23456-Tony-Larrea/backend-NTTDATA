package transacciones.backend.src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transacciones.backend.src.entity.Movimientos;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {
}
