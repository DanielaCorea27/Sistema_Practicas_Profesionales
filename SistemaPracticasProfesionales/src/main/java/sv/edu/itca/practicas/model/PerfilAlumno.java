package sv.edu.itca.practicas.model;

import java.io.Serializable;

/** Datos del perfil del estudiante (usuario + alumno + carrera). */
public class PerfilAlumno implements Serializable {

    private static final long serialVersionUID = 1L;

    private int alumnoId;
    private String nombreCompleto;
    private String correo;
    private String rol;
    private String carnet;
    private String telefono;
    private String carrera;
    private String tipoCarrera;      // Técnico o Ingeniería
    private int horasRequeridas;     // 320 o 640
    private boolean activo;

    public PerfilAlumno() {
    }

    public String getEstadoTexto() {
        return activo ? "Activo" : "Inactivo";
    }

    public int getAlumnoId() { return alumnoId; }
    public void setAlumnoId(int v) { this.alumnoId = v; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String v) { this.nombreCompleto = v; }

    public String getCorreo() { return correo; }
    public void setCorreo(String v) { this.correo = v; }

    public String getRol() { return rol; }
    public void setRol(String v) { this.rol = v; }

    public String getCarnet() { return carnet; }
    public void setCarnet(String v) { this.carnet = v; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String v) { this.telefono = v; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String v) { this.carrera = v; }

    public String getTipoCarrera() { return tipoCarrera; }
    public void setTipoCarrera(String v) { this.tipoCarrera = v; }

    public int getHorasRequeridas() { return horasRequeridas; }
    public void setHorasRequeridas(int v) { this.horasRequeridas = v; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean v) { this.activo = v; }
}
