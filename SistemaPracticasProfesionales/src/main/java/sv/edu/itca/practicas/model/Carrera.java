package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private int idTipoCarrera;
    private String tipo;
    private int horasRequeridas;
    private int totalAlumnos;

    public Carrera() {
    }

    public Carrera(int id, String nombre, int idTipoCarrera,
            String tipo, int horasRequeridas) {

        this.id = id;
        this.nombre = nombre;
        this.idTipoCarrera = idTipoCarrera;
        this.tipo = tipo;
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

    public int getIdTipoCarrera() {
        return idTipoCarrera;
    }

    public void setIdTipoCarrera(int idTipoCarrera) {
        this.idTipoCarrera = idTipoCarrera;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getHorasRequeridas() {
        return horasRequeridas;
    }

    public void setHorasRequeridas(int horasRequeridas) {
        this.horasRequeridas = horasRequeridas;
    }

    public int getTotalAlumnos() {
        return totalAlumnos;
    }

    public void setTotalAlumnos(int totalAlumnos) {
        this.totalAlumnos = totalAlumnos;
    }
}
