package sv.edu.itca.practicas.model;

import java.time.LocalDate;

/**
 * Fila del listado de postulantes de una empresa
 * (postulacion + estudiante + oportunidad).
 */
public class Postulante {

    private int postulacionId;
    private LocalDate fechaPostulacion;
    private String estado;              // estado de la postulacion

    private int oportunidadId;
    private String oportunidadTitulo;
    private int horasOfrecidas;
    private String oportunidadEstado;

    private int alumnoId;
    private String alumnoNombre;
    private String correo;
    private String carnet;
    private String telefono;
    private String carrera;
    private String tipoCarrera;
    private int horasRequeridas;        // 320 o 640 segun tipo de carrera
    private int horasComprometidas;     // ya planificadas en otras empresas

    public Postulante() {
    }

    public int getPostulacionId() { return postulacionId; }
    public void setPostulacionId(int v) { this.postulacionId = v; }

    public LocalDate getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDate v) { this.fechaPostulacion = v; }

    public String getEstado() { return estado; }
    public void setEstado(String v) { this.estado = v; }

    public int getOportunidadId() { return oportunidadId; }
    public void setOportunidadId(int v) { this.oportunidadId = v; }

    public String getOportunidadTitulo() { return oportunidadTitulo; }
    public void setOportunidadTitulo(String v) { this.oportunidadTitulo = v; }

    public int getHorasOfrecidas() { return horasOfrecidas; }
    public void setHorasOfrecidas(int v) { this.horasOfrecidas = v; }

    public String getOportunidadEstado() { return oportunidadEstado; }
    public void setOportunidadEstado(String v) { this.oportunidadEstado = v; }

    public int getAlumnoId() { return alumnoId; }
    public void setAlumnoId(int v) { this.alumnoId = v; }

    public String getAlumnoNombre() { return alumnoNombre; }
    public void setAlumnoNombre(String v) { this.alumnoNombre = v; }

    public String getCorreo() { return correo; }
    public void setCorreo(String v) { this.correo = v; }

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

    public int getHorasComprometidas() { return horasComprometidas; }
    public void setHorasComprometidas(int v) { this.horasComprometidas = v; }

    public int getHorasPorCubrir() {
        return Math.max(0, horasRequeridas - horasComprometidas);
    }
}
