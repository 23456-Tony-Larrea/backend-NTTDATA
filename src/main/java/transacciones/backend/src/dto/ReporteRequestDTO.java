package transacciones.backend.src.dto;

import java.util.Date;
import java.util.List;

public class ReporteRequestDTO {
    private Long clienteId;
    private String clienteNombre;
    private Date fechaInicio;
    private Date fechaFin;
    private List<CuentaDTO> cuentas;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<CuentaDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaDTO> cuentas) {
        this.cuentas = cuentas;
    }

    public static class CuentaDTO {
        private String numeroCuenta;
        private String tipoCuenta;
        private double saldoInicial;
        private boolean estado;
        private List<MovimientoDTO> movimientos;

        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public String getTipoCuenta() {
            return tipoCuenta;
        }

        public void setTipoCuenta(String tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
        }

        public double getSaldoInicial() {
            return saldoInicial;
        }

        public void setSaldoInicial(double saldoInicial) {
            this.saldoInicial = saldoInicial;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        public List<MovimientoDTO> getMovimientos() {
            return movimientos;
        }

        public void setMovimientos(List<MovimientoDTO> movimientos) {
            this.movimientos = movimientos;
        }
    }

    public static class MovimientoDTO {
        private Date fecha;
        private String tipoMovimiento;
        private double valor;
        private double saldo;

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        public String getTipoMovimiento() {
            return tipoMovimiento;
        }

        public void setTipoMovimiento(String tipoMovimiento) {
            this.tipoMovimiento = tipoMovimiento;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }
    }
}