package sv.edu.itca.practicas.model;

import java.io.Serializable;

public class PracticaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int alumnoId;

    private String alumno;

    private String carrera;

    private int horasRequeridas;

    private String empresa;

    private String area;

    private String direccionEmpresa;

    private String maestro;

    private String correoMaestro;

    private String telefonoMaestro;

    private String fechaInicio;

    private String fechaFin;

    private String estado;

    public PracticaInfo() {
    }

    public PracticaInfo(
            int id,
            int alumnoId,
            String alumno,
            String carrera,
            int horasRequeridas,
            String empresa,
            String area,
            String direccionEmpresa,
            String maestro,
            String correoMaestro,
            String telefonoMaestro,
            String fechaInicio,
            String fechaFin,
            String estado) {

        this.id = id;
        this.alumnoId = alumnoId;
        this.alumno = alumno;
        this.carrera = carrera;
        this.horasRequeridas = horasRequeridas;
        this.empresa = empresa;
        this.area = area;
        this.direccionEmpresa = direccionEmpresa;
        this.maestro = maestro;
        this.correoMaestro = correoMaestro;
        this.telefonoMaestro = telefonoMaestro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getHorasRequeridas() {
        return horasRequeridas;
    }

    public void setHorasRequeridas(int horasRequeridas) {
        this.horasRequeridas = horasRequeridas;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public String getCorreoMaestro() {
        return correoMaestro;
    }

    public void setCorreoMaestro(String correoMaestro) {
        this.correoMaestro = correoMaestro;
    }

    public String getTelefonoMaestro() {
        return telefonoMaestro;
    }

    public void setTelefonoMaestro(String telefonoMaestro) {
        this.telefonoMaestro = telefonoMaestro;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}