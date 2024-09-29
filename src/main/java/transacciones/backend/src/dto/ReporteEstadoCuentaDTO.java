package transacciones.backend.src.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ReporteEstadoCuentaDTO {
    private String clienteNombre;
    private List<CuentaDTO> cuentas;

    @Data
    public static class CuentaDTO {
        private String numeroCuenta;
        private String tipoCuenta;
        private double saldoInicial;
        private boolean estado;
        private List<MovimientoDTO> movimientos;
    }

    @Data
    public static class MovimientoDTO {
        private Date fecha;
        private String tipoMovimiento;
        private double valor;
        private double saldo;
    }
}