package sv.edu.itca.practicas.model;

import java.time.LocalDateTime;

public class Observacion {

    private int id;
    private int practicaId;
    private int maestroId;
    private String comentario;
    private LocalDateTime fecha;
    private boolean importante;

    public Observacion() {
    }

    public Observacion(int id,
                       int practicaId,
                       int maestroId,
                       String comentario,
                       LocalDateTime fecha,
                       boolean importante) {

        this.id = id;
        this.practicaId = practicaId;
        this.maestroId = maestroId;
        this.comentario = comentario;
        this.fecha = fecha;
        this.importante = importante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPracticaId() {
        return practicaId;
    }

    public void setPracticaId(int practicaId) {
        this.practicaId = practicaId;
    }

    public int getMaestroId() {
        return maestroId;
    }

    public void setMaestroId(int maestroId) {
        this.maestroId = maestroId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }
}