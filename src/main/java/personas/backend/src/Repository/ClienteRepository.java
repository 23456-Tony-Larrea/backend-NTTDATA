package personas.backend.src.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import personas.backend.src.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}