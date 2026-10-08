package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class AlumnoResumen implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String carnet;
    private String nombre;
    private String carrera;
    private String empresa;
    private String estado;

    public AlumnoResumen() {
    }

    public AlumnoResumen(
            int id,
            String carnet,
            String nombre,
            String carrera,
            String empresa,
            String estado) {

        this.id = id;
        this.carnet = carnet;
        this.nombre = nombre;
        this.carrera = carrera;
        this.empresa = empresa;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}