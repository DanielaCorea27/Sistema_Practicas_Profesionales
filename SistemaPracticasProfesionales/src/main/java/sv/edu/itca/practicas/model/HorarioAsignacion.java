package sv.edu.itca.practicas.model;

/** Un bloque del horario PLANIFICADO (no cuenta como horas realizadas). */
public class HorarioAsignacion {

    private static final String[] DIAS = {
        "", "Lunes", "Martes", "Miércoles", "Jueves",
        "Viernes", "Sábado", "Domingo"
    };

    private int id;
    private int diaSemana;       // 1 = Lunes ... 7 = Domingo
    private String horaInicio;
    private String horaFin;

    public HorarioAsignacion() {
    }

    public String getDiaNombre() {

        return diaSemana >= 1 && diaSemana <= 7 ? DIAS[diaSemana] : "?";
    }

    public int getId() { return id; }
    public void setId(int v) { this.id = v; }

    public int getDiaSemana() { return diaSemana; }
    public void setDiaSemana(int v) { this.diaSemana = v; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String v) { this.horaInicio = v; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String v) { this.horaFin = v; }
}
