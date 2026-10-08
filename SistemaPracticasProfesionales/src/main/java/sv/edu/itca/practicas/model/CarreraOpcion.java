package sv.edu.itca.practicas.model;

/** Carrera para mostrar en el combo del registro (con su tipo y horas). */
public class CarreraOpcion {

    private int id;
    private String nombre;
    private String tipo;          // Técnico o Ingeniería
    private int horasRequeridas;  // 320 o 640

    public CarreraOpcion() {
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public String getNombre() { return nombre; }
    public void setNombre(String v) { this.nombre = v; }

    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }

    public int getHorasRequeridas() { return horasRequeridas; }
    public void setHorasRequeridas(int v) { this.horasRequeridas = v; }
}
