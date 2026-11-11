package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class EmpresaAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String correo;
    private String sitioWeb;
    private String contacto;
    private boolean activo;
    private int totalRepresentantes;

    public EmpresaAdmin() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEstado() {
        return activo ? "ACTIVO" : "INACTIVO";
    }

    public int getTotalRepresentantes() {
        return totalRepresentantes;
    }

    public void setTotalRepresentantes(int totalRepresentantes) {
        this.totalRepresentantes = totalRepresentantes;
    }
}
