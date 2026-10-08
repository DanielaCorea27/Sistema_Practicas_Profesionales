package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class PracticaResumen implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String alumno;
    private String empresa;
    private String carrera;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    public PracticaResumen() {
    }

    public PracticaResumen(
            int id,
            String alumno,
            String empresa,
            String carrera,
            String fechaInicio,
            String fechaFin,
            String estado) {

        this.id = id;
        this.alumno = alumno;
        this.empresa = empresa;
        this.carrera = carrera;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}