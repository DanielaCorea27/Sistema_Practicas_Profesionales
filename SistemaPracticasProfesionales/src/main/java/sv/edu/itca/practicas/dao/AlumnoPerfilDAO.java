package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.ObservacionAsignacion;
import sv.edu.itca.practicas.model.PerfilAlumno;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Perfil y observaciones del alumno (solo lectura).
 */
public class AlumnoPerfilDAO {

    /** null si el alumno no existe. */
    public PerfilAlumno buscarPerfil(int alumnoId) {

        String sql = "SELECT a.id_alumno, a.carnet, a.telefono, "
                + "u.nombre, u.apellido, u.correo, u.estado, "
                + "r.nombre AS rol, c.nombre AS carrera, "
                + "t.nombre AS tipo, t.horas_requeridas "
                + "FROM alumnos a "
                + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
                + "JOIN roles r ON r.id_rol = u.id_rol "
                + "JOIN carreras c ON c.id_carrera = a.id_carrera "
                + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
                + "WHERE a.id_alumno = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                PerfilAlumno p = new PerfilAlumno();

                p.setAlumnoId(rs.getInt("id_alumno"));
                p.setNombreCompleto(
                        rs.getString("nombre") + " " + rs.getString("apellido"));
                p.setCorreo(rs.getString("correo"));
                p.setRol(rs.getString("rol"));
                p.setCarnet(rs.getString("carnet"));
                p.setTelefono(rs.getString("telefono"));
                p.setCarrera(rs.getString("carrera"));
                p.setTipoCarrera(rs.getString("tipo"));
                p.setHorasRequeridas(rs.getInt("horas_requeridas"));
                p.setActivo(rs.getBoolean("estado"));

                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Observaciones que la empresa o el tutor dejaron en cualquiera de las
     * asignaciones del alumno (mas recientes primero).
     */
    public List<ObservacionAsignacion> listarObservaciones(int alumnoId) {

        List<ObservacionAsignacion> lista = new ArrayList<>();

        String sql = "SELECT ob.id_observacion, ob.texto, ob.fecha_hora, "
                + "u.nombre, u.apellido, r.nombre AS rol "
                + "FROM observaciones_asignacion ob "
                + "JOIN asignaciones_pasantia ap ON ap.id_asignacion = ob.id_asignacion "
                + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
                + "JOIN usuarios u ON u.id_usuario = ob.id_usuario "
                + "JOIN roles r ON r.id_rol = u.id_rol "
                + "WHERE pa.id_alumno = ? "
                + "ORDER BY ob.id_observacion DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ObservacionAsignacion o = new ObservacionAsignacion();

                    Timestamp ts = rs.getTimestamp("fecha_hora");

                    o.setId(rs.getInt("id_observacion"));
                    o.setTexto(rs.getString("texto"));
                    o.setFechaHora(ts == null ? null : ts.toLocalDateTime());
                    o.setAutor(
                            rs.getString("nombre") + " " + rs.getString("apellido"));
                    o.setRol(rs.getString("rol"));

                    lista.add(o);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
