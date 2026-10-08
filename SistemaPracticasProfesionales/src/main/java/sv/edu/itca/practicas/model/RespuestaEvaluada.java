package sv.edu.itca.practicas.model;

/** Pregunta + opcion elegida, tal como se respondio. */
public class RespuestaEvaluada {

    private String pregunta;
    private String opcion;

    public RespuestaEvaluada() {
    }

    public RespuestaEvaluada(String pregunta, String opcion) {
        this.pregunta = pregunta;
        this.opcion = opcion;
    }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String v) { this.pregunta = v; }

    public String getOpcion() { return opcion; }
    public void setOpcion(String v) { this.opcion = v; }
}
