package transacciones.backend.src.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import transacciones.backend.src.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {}