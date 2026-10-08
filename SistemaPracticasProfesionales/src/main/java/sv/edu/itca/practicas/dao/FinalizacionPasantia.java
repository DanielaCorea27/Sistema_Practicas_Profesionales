package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Regla de cierre del flujo (planificacion, fase 4):
 *   EMPRESA + TUTOR hacen la evaluacion final  ->  PASANTIA FINALIZADA
 *
 * Se llama DENTRO de la transaccion, justo despues de insertar una
 * evaluacion. El modulo del TUTOR debe llamarla igual al guardar la suya.
 *
 *  - La asignacion pasa a FINALIZADA cuando tiene evaluacion de la EMPRESA
 *    y del MAESTRO.
 *  - La pasantia pasa a FINALIZADA cuando ya no le quedan asignaciones
 *    ACTIVAS y las horas planificadas cubren las horas requeridas
 *    (asi no se cierra si aun falta la segunda empresa).
 */
public final class FinalizacionPasantia {

    public static final int SOLO_GUARDADA = 0;
    public static final int ASIGNACION_FINALIZADA = 1;
    public static final int PASANTIA_FINALIZADA = 2;

    private FinalizacionPasantia() {
    }

    public static int revisar(Connection con, int asignacionId)
            throws SQLException {

        // 1. Deben existir las dos evaluaciones.
        int tipos = entero(con,
                "SELECT COUNT(DISTINCT tipo_evaluador) FROM evaluaciones "
                + "WHERE id_asignacion = ?", asignacionId);

        if (tipos < 2) {
            return SOLO_GUARDADA;
        }

        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE asignaciones_pasantia SET estado = 'FINALIZADA' "
                + "WHERE id_asignacion = ? AND estado = 'ACTIVA'")) {

            ps.setInt(1, asignacionId);
            ps.executeUpdate();
        }

        // 2. ¿Se puede cerrar toda la pasantia?
        int pasantiaId = entero(con,
                "SELECT id_pasantia FROM asignaciones_pasantia "
                + "WHERE id_asignacion = ?", asignacionId);

        int activas = entero(con,
                "SELECT COUNT(*) FROM asignaciones_pasantia "
                + "WHERE id_pasantia = ? AND estado = 'ACTIVA'", pasantiaId);

        if (activas > 0) {
            return ASIGNACION_FINALIZADA;
        }

        boolean cubierta = false;

        String sql = "SELECT pa.horas_requeridas, "
                + "COALESCE(SUM(ap.horas_planificadas), 0) AS planificadas "
                + "FROM pasantias pa "
                + "LEFT JOIN asignaciones_pasantia ap "
                + "  ON ap.id_pasantia = pa.id_pasantia "
                + " AND ap.estado <> 'CANCELADA' "
                + "WHERE pa.id_pasantia = ? "
                + "GROUP BY pa.id_pasantia, pa.horas_requeridas";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pasantiaId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    cubierta = rs.getInt("planificadas")
                            >= rs.getInt("horas_requeridas");
                }
            }
        }

        if (!cubierta) {
            return ASIGNACION_FINALIZADA;
        }

        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE pasantias SET estado = 'FINALIZADA' "
                + "WHERE id_pasantia = ? AND estado IN ('PENDIENTE','ACTIVA')")) {

            ps.setInt(1, pasantiaId);
            ps.executeUpdate();
        }

        return PASANTIA_FINALIZADA;
    }

    private static int entero(Connection con, String sql, int parametro)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, parametro);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}
