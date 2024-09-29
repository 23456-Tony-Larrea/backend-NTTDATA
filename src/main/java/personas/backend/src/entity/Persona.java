package personas.backend.src.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_persona")
public class Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String genero; 
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}