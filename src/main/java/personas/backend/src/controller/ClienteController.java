package personas.backend.src.controller;

import personas.backend.src.dto.ClienteDTO;
import personas.backend.src.entity.Cliente;
import personas.backend.src.exeption.ClienteNotFoundException;
import personas.backend.src.exeption.ValidationException;
import personas.backend.src.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente not found with id: " + id));
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteDTO clienteDTO) {
        if (clienteDTO.getNombre() == null || clienteDTO.getNombre().isEmpty()) {
            throw new ValidationException("El nombre es obligatorio");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContraseña(clienteDTO.getContraseña());
        cliente.setEstado(clienteDTO.isEstado());

        Cliente createdCliente = clienteService.save(cliente);
        return ResponseEntity.ok(createdCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente not found with id: " + id));
        
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContraseña(clienteDTO.getContraseña());
        cliente.setEstado(clienteDTO.isEstado());
        
        Cliente updatedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
                clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente not found with id: " + id));

        
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}