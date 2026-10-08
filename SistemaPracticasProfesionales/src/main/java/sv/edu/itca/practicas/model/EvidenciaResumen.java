package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class EvidenciaResumen implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int alumnoId;
    private String alumno;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String fecha;
    private String estado;

    public EvidenciaResumen() {
    }

    public EvidenciaResumen(
            int id,
            int alumnoId,
            String alumno,
            String titulo,
            String descripcion,
            String tipo,
            String fecha,
            String estado) {

        this.id = id;
        this.alumnoId = alumnoId;
        this.alumno = alumno;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}