package sv.edu.itca.practicas.model;

/** Una opcion de respuesta (ej. Excelente, Bueno, Regular, Deficiente). */
public class OpcionEvaluacion {

    private int id;
    private String texto;

    public OpcionEvaluacion() {
    }

    public OpcionEvaluacion(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public String getTexto() { return texto; }
    public void setTexto(String v) { this.texto = v; }
}
