package personas.backend.src.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona  {

    @Column(nullable = true)
    private String clienteId;

    @Column(nullable = false)
    private String contraseña;

    private boolean estado;
}