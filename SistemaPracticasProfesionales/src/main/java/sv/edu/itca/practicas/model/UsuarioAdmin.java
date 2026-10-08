package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class UsuarioAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String usuario;
    private String rol;
    private String estado;

    public UsuarioAdmin() {
    }

    public UsuarioAdmin(
            int id,
            String nombre,
            String usuario,
            String rol,
            String estado) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}