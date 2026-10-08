package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class MaestroAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String usuario;
    private String especialidad;
    private String estado;

    public MaestroAdmin() {
    }

    public MaestroAdmin(
            int id,
            String nombre,
            String usuario,
            String especialidad,
            String estado) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.especialidad = especialidad;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}