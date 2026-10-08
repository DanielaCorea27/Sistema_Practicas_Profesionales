package sv.edu.itca.practicas.model;

import java.time.LocalDateTime;

/** Observacion sobre una asignacion (la escribe la empresa o el tutor). */
public class ObservacionAsignacion {

    private int id;
    private String texto;
    private LocalDateTime fechaHora;
    private String autor;     // nombre completo
    private String rol;       // EMPRESA, MAESTRO, ...

    public ObservacionAsignacion() {
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public String getTexto() { return texto; }
    public void setTexto(String v) { this.texto = v; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime v) { this.fechaHora = v; }

    public String getAutor() { return autor; }
    public void setAutor(String v) { this.autor = v; }

    public String getRol() { return rol; }
    public void setRol(String v) { this.rol = v; }
}
