package transacciones.backend.src.controller;

import transacciones.backend.src.service.ReporteService;
import transacciones.backend.src.dto.ReporteRequestDTO;
import transacciones.backend.src.dto.ReporteEstadoCuentaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @PostMapping("/estadoCuenta")
    public String solicitarReporteEstadoCuenta(@RequestBody ReporteRequestDTO request) {
        reporteService.solicitarReporte(request);
        return "Solicitud de reporte recibida. El reporte se generará y se enviará en breve.";
    }

    @GetMapping
    public ReporteEstadoCuentaDTO generarReporteEstadoCuenta(
            @RequestParam String cliente,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inicio = dateFormat.parse(fechaInicio);
        Date fin = dateFormat.parse(fechaFin);

        List<ReporteRequestDTO.CuentaDTO> cuentas = reporteService.obtenerCuentasDelCliente(cliente);

        return reporteService.generarReporteEstadoCuenta(cliente, inicio, fin, cuentas);
    }
}