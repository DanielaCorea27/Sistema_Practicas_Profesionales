package sv.edu.itca.practicas.model;

public class Maestro {

    private int id;
    private Usuario usuario;
    private String codigoEmpleado;
    private String especialidad;
    private String telefono;

    public Maestro() {
    }

    public Maestro(int id, Usuario usuario,
                   String codigoEmpleado,
                   String especialidad,
                   String telefono) {

        this.id = id;
        this.usuario = usuario;
        this.codigoEmpleado = codigoEmpleado;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}