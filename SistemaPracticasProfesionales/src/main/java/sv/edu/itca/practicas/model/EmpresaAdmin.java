package sv.edu.itca.practicas.model;
/**
 *
 * @author danie
 */

import java.io.Serializable;

public class EmpresaAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String correo;
    private String estado;

    public EmpresaAdmin() {
    }

    public EmpresaAdmin(
            int id,
            String nombre,
            String contacto,
            String telefono,
            String correo,
            String estado) {

        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.correo = correo;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}