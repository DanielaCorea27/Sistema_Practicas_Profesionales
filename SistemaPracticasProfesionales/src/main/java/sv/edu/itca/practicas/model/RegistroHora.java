package sv.edu.itca.practicas.model;

import java.io.Serializable;
import java.time.LocalDate;

public class RegistroHora implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int alumnoId;

    private LocalDate fecha;

    private double horas;

    private String actividad;

    private String estado;

    private String observacion;


    public RegistroHora() {
    }


    public RegistroHora(
            int id,
            int alumnoId,
            LocalDate fecha,
            double horas,
            String actividad,
            String estado,
            String observacion) {

        this.id = id;
        this.alumnoId = alumnoId;
        this.fecha = fecha;
        this.horas = horas;
        this.actividad = actividad;
        this.estado = estado;
        this.observacion = observacion;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getAlumnoId() {
        return alumnoId;
    }


    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public double getHoras() {
        return horas;
    }


    public void setHoras(double horas) {
        this.horas = horas;
    }


    public String getActividad() {
        return actividad;
    }


    public void setActividad(String actividad) {
        this.actividad = actividad;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getObservacion() {
        return observacion;
    }


    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}