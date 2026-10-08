package sv.edu.itca.practicas.model;

import java.time.LocalDate;

/** Actividad registrada por el estudiante en una asignacion. */
public class ActividadAsignacion {

    private int id;
    private LocalDate fecha;
    private String titulo;
    private String descripcion;
    private String horaInicio;     // "08:00"
    private String horaFin;        // "16:00"
    private double horas;
    private String evidencia;      // nombre/ruta del archivo (puede ser null)
    private String estado;         // PENDIENTE, APROBADO, RECHAZADO
    private String observacion;    // la deja el tutor

    public ActividadAsignacion() {
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate v) { this.fecha = v; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String v) { this.titulo = v; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String v) { this.descripcion = v; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String v) { this.horaInicio = v; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String v) { this.horaFin = v; }

    public double getHoras() { return horas; }
    public void setHoras(double v) { this.horas = v; }

    public String getEvidencia() { return evidencia; }
    public void setEvidencia(String v) { this.evidencia = v; }

    public String getEstado() { return estado; }
    public void setEstado(String v) { this.estado = v; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String v) { this.observacion = v; }
}
