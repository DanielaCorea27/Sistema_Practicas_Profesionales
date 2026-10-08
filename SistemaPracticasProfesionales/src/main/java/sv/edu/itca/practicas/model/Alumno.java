package sv.edu.itca.practicas.model;

public class Alumno {

    private int id;
    private Usuario usuario;
    private String carnet;
    private Carrera carrera;
    private String telefono;

    public Alumno() {
    }

    public Alumno(int id, Usuario usuario, String carnet,
                  Carrera carrera, String telefono) {

        this.id = id;
        this.usuario = usuario;
        this.carnet = carnet;
        this.carrera = carrera;
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

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}