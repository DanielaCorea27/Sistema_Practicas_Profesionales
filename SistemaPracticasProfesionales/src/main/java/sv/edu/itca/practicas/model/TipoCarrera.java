package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class TipoCarrera implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private int horasRequeridas;

    public TipoCarrera() {
    }

    public TipoCarrera(int id, String nombre, int horasRequeridas) {
        this.id = id;
        this.nombre = nombre;
        this.horasRequeridas = horasRequeridas;
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

    public int getHorasRequeridas() {
        return horasRequeridas;
    }

    public void setHorasRequeridas(int horasRequeridas) {
        this.horasRequeridas = horasRequeridas;
    }
}
