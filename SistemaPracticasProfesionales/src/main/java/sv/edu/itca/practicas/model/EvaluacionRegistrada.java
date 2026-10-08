package sv.edu.itca.practicas.model;

import java.time.LocalDate;

/** Evaluacion ya guardada (calificacion 1-10 + observaciones). */
public class EvaluacionRegistrada {

    private int id;
    private double calificacion;
    private String observaciones;
    private LocalDate fecha;
    private String evaluador;

    public EvaluacionRegistrada() {
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double v) { this.calificacion = v; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String v) { this.observaciones = v; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate v) { this.fecha = v; }

    public String getEvaluador() { return evaluador; }
    public void setEvaluador(String v) { this.evaluador = v; }
}
