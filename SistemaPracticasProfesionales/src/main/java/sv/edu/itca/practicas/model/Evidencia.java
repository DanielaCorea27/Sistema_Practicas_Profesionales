package sv.edu.itca.practicas.model;

import java.time.LocalDate;

public class Evidencia {

    private int id;
    private int practicaId;
    private String nombreArchivo;
    private String rutaArchivo;
    private String descripcion;
    private LocalDate fechaSubida;
    private boolean aprobada;

    public Evidencia() {
    }

    public Evidencia(int id,
                     int practicaId,
                     String nombreArchivo,
                     String rutaArchivo,
                     String descripcion,
                     LocalDate fechaSubida,
                     boolean aprobada) {

        this.id = id;
        this.practicaId = practicaId;
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.descripcion = descripcion;
        this.fechaSubida = fechaSubida;
        this.aprobada = aprobada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPracticaId() {
        return practicaId;
    }

    public void setPracticaId(int practicaId) {
        this.practicaId = practicaId;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }
}