package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Contadores del dashboard de empresa (pantalla 15 de la planificacion).
 */
public class EmpresaDashboardDAO {

    /** Oportunidades publicadas por la empresa (no rechazadas). */
    public int contarOportunidades(int empresaId) {

        return contar(
                "SELECT COUNT(*) FROM oportunidades "
                + "WHERE id_empresa = ? AND estado <> 'RECHAZADA'",
                empresaId);
    }

    /** Postulaciones pendientes de respuesta a oportunidades de la empresa. */
    public int contarPostulacionesPendientes(int empresaId) {

        return contar(
                "SELECT COUNT(*) FROM postulaciones p "
                + "JOIN oportunidades o ON o.id_oportunidad = p.id_oportunidad "
                + "WHERE o.id_empresa = ? AND p.estado = 'PENDIENTE'",
                empresaId);
    }

    /** Estudiantes con una asignacion activa en la empresa. */
    public int contarEstudiantesActivos(int empresaId) {

        return contar(
                "SELECT COUNT(*) FROM asignaciones_pasantia "
                + "WHERE id_empresa = ? AND estado = 'ACTIVA'",
                empresaId);
    }

    /** Asignaciones de la empresa que aun no tienen evaluacion de empresa. */
    public int contarEvaluacionesPendientes(int empresaId) {

        return contar(
                "SELECT COUNT(*) FROM asignaciones_pasantia a "
                + "WHERE a.id_empresa = ? AND a.estado IN ('ACTIVA','FINALIZADA') "
                + "AND NOT EXISTS (SELECT 1 FROM evaluaciones ev "
                + "WHERE ev.id_asignacion = a.id_asignacion "
                + "AND ev.tipo_evaluador = 'EMPRESA')",
                empresaId);
    }

    private int contar(String sql, int empresaId) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
