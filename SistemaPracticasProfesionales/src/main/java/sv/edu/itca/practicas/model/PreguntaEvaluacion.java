package sv.edu.itca.practicas.model;

import java.util.ArrayList;
import java.util.List;

/** Pregunta de opcion multiple con sus opciones. */
public class PreguntaEvaluacion {

    private int id;
    private String texto;
    private List<OpcionEvaluacion> opciones = new ArrayList<>();

    public PreguntaEvaluacion() {
    }

    /** true si la opcion pertenece a esta pregunta. */
    public boolean tieneOpcion(int opcionId) {

        for (OpcionEvaluacion o : opciones) {

            if (o.getId() == opcionId) {
                return true;
            }
        }

        return false;
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public String getTexto() { return texto; }
    public void setTexto(String v) { this.texto = v; }

    public List<OpcionEvaluacion> getOpciones() { return opciones; }
    public void setOpciones(List<OpcionEvaluacion> v) { this.opciones = v; }
}
