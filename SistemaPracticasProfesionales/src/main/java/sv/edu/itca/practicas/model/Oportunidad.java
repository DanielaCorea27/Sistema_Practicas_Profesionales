package sv.edu.itca.practicas.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Oportunidad de pasantia publicada por una empresa.
 * Estados: PENDIENTE, APROBADA, RECHAZADA, CERRADA.
 */
public class Oportunidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int empresaId;
    private int representanteId;
    private String titulo;
    private String descripcion;
    private String requisitos;
    private String modalidad;        // PRESENCIAL, REMOTA, HIBRIDA
    private String duracion;
    private int horasOfrecidas;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private String observacion;      // motivo si el maestro la rechaza
    private int totalPostulaciones;  // solo para listados

    public Oportunidad() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public int getRepresentanteId() { return representanteId; }
    public void setRepresentanteId(int representanteId) { this.representanteId = representanteId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public int getHorasOfrecidas() { return horasOfrecidas; }
    public void setHorasOfrecidas(int horasOfrecidas) { this.horasOfrecidas = horasOfrecidas; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public int getTotalPostulaciones() { return totalPostulaciones; }
    public void setTotalPostulaciones(int totalPostulaciones) { this.totalPostulaciones = totalPostulaciones; }
}
