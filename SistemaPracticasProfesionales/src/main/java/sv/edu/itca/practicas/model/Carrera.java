package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private double horasRequeridas;

    public Carrera() {
    }

    public Carrera(
            int id,
            String nombre,
            double horasRequeridas) {

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

    public double getHorasRequeridas() {
        return horasRequeridas;
    }

    public void setHorasRequeridas(
            double horasRequeridas) {

        this.horasRequeridas = horasRequeridas;
    }
}