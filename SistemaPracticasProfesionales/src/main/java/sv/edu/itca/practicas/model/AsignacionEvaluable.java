package sv.edu.itca.practicas.model;

/** Fila del listado de evaluaciones de la empresa. */
public class AsignacionEvaluable {

    private int asignacionId;
    private String alumnoNombre;
    private String carnet;
    private String carrera;
    private String maestroNombre;          // null si no hay tutor
    private String estado;                 // ACTIVA o FINALIZADA
    private int horasPlanificadas;
    private double horasAprobadas;
    private Double calificacionEmpresa;    // null = pendiente
    private boolean evaluadoPorTutor;

    public AsignacionEvaluable() {
    }

    public boolean isEvaluadoPorEmpresa() {
        return calificacionEmpresa != null;
    }

    public int getAsignacionId() { return asignacionId; }
    public void setAsignacionId(int v) { this.asignacionId = v; }

    public String getAlumnoNombre() { return alumnoNombre; }
    public void setAlumnoNombre(String v) { this.alumnoNombre = v; }

    public String getCarnet() { return carnet; }
    public void setCarnet(String v) { this.carnet = v; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String v) { this.carrera = v; }

    public String getMaestroNombre() { return maestroNombre; }
    public void setMaestroNombre(String v) { this.maestroNombre = v; }

    public String getEstado() { return estado; }
    public void setEstado(String v) { this.estado = v; }

    public int getHorasPlanificadas() { return horasPlanificadas; }
    public void setHorasPlanificadas(int v) { this.horasPlanificadas = v; }

    public double getHorasAprobadas() { return horasAprobadas; }
    public void setHorasAprobadas(double v) { this.horasAprobadas = v; }

    public Double getCalificacionEmpresa() { return calificacionEmpresa; }
    public void setCalificacionEmpresa(Double v) { this.calificacionEmpresa = v; }

    public boolean isEvaluadoPorTutor() { return evaluadoPorTutor; }
    public void setEvaluadoPorTutor(boolean v) { this.evaluadoPorTutor = v; }
}
