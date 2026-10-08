package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class AlumnoAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String carnet;
    private String carrera;
    private String usuario;
    private String estado;

    public AlumnoAdmin() {
    }

    public AlumnoAdmin(
            int id,
            String nombre,
            String carnet,
            String carrera,
            String usuario,
            String estado) {

        this.id = id;
        this.nombre = nombre;
        this.carnet = carnet;
        this.carrera = carrera;
        this.usuario = usuario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}