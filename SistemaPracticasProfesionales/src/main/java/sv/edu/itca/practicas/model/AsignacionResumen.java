package sv.edu.itca.practicas.model;

import java.time.LocalDate;

/**
 * Una asignacion (estudiante en ESTA empresa) con sus horas y avance.
 * Sirve para el listado "Estudiantes en practica" y para la cabecera
 * de "Seguimiento".
 */
public class AsignacionResumen {

    private int id;                       // asignaciones_pasantia.id_asignacion
    private int pasantiaId;

    private int alumnoId;
    private String alumnoNombre;
    private String carnet;
    private String correo;
    private String telefono;
    private String carrera;
    private String tipoCarrera;

    private String maestroNombre;         // null si aun no hay tutor
    private String oportunidadTitulo;

    private int horasRequeridas;          // de toda la pasantia (320 o 640)
    private int horasPlanificadas;        // las de esta empresa
    private double horasAprobadas;        // aprobadas en esta empresa
    private double horasPendientes;       // por aprobar en esta empresa
    private double horasTotalesPasantia;  // aprobadas en todas las empresas

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String modalidad;
    private String estado;                // ACTIVA, FINALIZADA, CANCELADA

    public AsignacionResumen() {
    }

    /** Avance dentro de esta empresa (0 a 100, un decimal). */
    public double getPorcentajeEmpresa() {
        return porcentaje(horasAprobadas, horasPlanificadas);
    }

    /** Avance de toda la pasantia (0 a 100, un decimal). */
    public double getPorcentajePasantia() {
        return porcentaje(horasTotalesPasantia, horasRequeridas);
    }

    public double getHorasPorCubrirEmpresa() {
        return Math.max(0, horasPlanificadas - horasAprobadas);
    }

    private double porcentaje(double parte, double total) {

        if (total <= 0) {
            return 0;
        }

        double p = parte * 100.0 / total;

        if (p > 100) {
            p = 100;
        }

        return Math.round(p * 10) / 10.0;
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public int getPasantiaId() { return pasantiaId; }
    public void setPasantiaId(int v) { this.pasantiaId = v; }

    public int getAlumnoId() { return alumnoId; }
    public void setAlumnoId(int v) { this.alumnoId = v; }

    public String getAlumnoNombre() { return alumnoNombre; }
    public void setAlumnoNombre(String v) { this.alumnoNombre = v; }

    public String getCarnet() { return carnet; }
    public void setCarnet(String v) { this.carnet = v; }

    public String getCorreo() { return correo; }
    public void setCorreo(String v) { this.correo = v; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String v) { this.telefono = v; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String v) { this.carrera = v; }

    public String getTipoCarrera() { return tipoCarrera; }
    public void setTipoCarrera(String v) { this.tipoCarrera = v; }

    public String getMaestroNombre() { return maestroNombre; }
    public void setMaestroNombre(String v) { this.maestroNombre = v; }

    public String getOportunidadTitulo() { return oportunidadTitulo; }
    public void setOportunidadTitulo(String v) { this.oportunidadTitulo = v; }

    public int getHorasRequeridas() { return horasRequeridas; }
    public void setHorasRequeridas(int v) { this.horasRequeridas = v; }

    public int getHorasPlanificadas() { return horasPlanificadas; }
    public void setHorasPlanificadas(int v) { this.horasPlanificadas = v; }

    public double getHorasAprobadas() { return horasAprobadas; }
    public void setHorasAprobadas(double v) { this.horasAprobadas = v; }

    public double getHorasPendientes() { return horasPendientes; }
    public void setHorasPendientes(double v) { this.horasPendientes = v; }

    public double getHorasTotalesPasantia() { return horasTotalesPasantia; }
    public void setHorasTotalesPasantia(double v) { this.horasTotalesPasantia = v; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate v) { this.fechaInicio = v; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate v) { this.fechaFin = v; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String v) { this.modalidad = v; }

    public String getEstado() { return estado; }
    public void setEstado(String v) { this.estado = v; }
}
