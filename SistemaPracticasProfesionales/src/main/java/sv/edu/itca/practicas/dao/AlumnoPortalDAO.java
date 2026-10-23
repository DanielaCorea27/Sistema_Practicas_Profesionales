package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Consultas de apoyo para el modulo del ALUMNO:
 * de usuario (sesion) a alumno, asignacion activa y horas requeridas.
 *
 * NO reemplaza a AlumnoDAO (ese lo usa el modulo admin).
 */
public class AlumnoPortalDAO {

    /** usuarios.id_usuario -> alumnos.id_alumno. Devuelve 0 si no existe. */
    public int buscarIdAlumno(int usuarioId) {

        return entero(
                "SELECT id_alumno FROM alumnos WHERE id_usuario = ?",
                usuarioId);
    }

    /**
     * Asignacion (alumno en una empresa) ACTIVA mas reciente.
     * Devuelve 0 si el alumno todavia no ha sido aceptado en ninguna empresa.
     */
    public int buscarAsignacionActiva(int alumnoId) {

        return entero(
                "SELECT ap.id_asignacion FROM asignaciones_pasantia ap "
                + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
                + "WHERE pa.id_alumno = ? AND ap.estado = 'ACTIVA' "
                + "ORDER BY ap.id_asignacion DESC LIMIT 1",
                alumnoId);
    }

    /** Horas que exige la carrera del alumno (320 Tecnico / 640 Ingenieria). */
    public int horasRequeridas(int alumnoId) {

        return entero(
                "SELECT t.horas_requeridas FROM alumnos a "
                + "JOIN carreras c ON c.id_carrera = a.id_carrera "
                + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
                + "WHERE a.id_alumno = ?",
                alumnoId);
    }

    private int entero(String sql, int parametro) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, parametro);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
