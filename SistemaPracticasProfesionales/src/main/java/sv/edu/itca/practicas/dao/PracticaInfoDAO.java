package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import sv.edu.itca.practicas.model.PracticaInfo;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Informacion de "Mi practica" del alumno, leida de MySQL:
 * pasantia mas reciente (no cancelada) + sus empresas + el tutor.
 *
 * Si el alumno tiene dos empresas, se muestran unidas con " / ".
 * Devuelve null si el alumno todavia no tiene pasantia (nadie lo ha aceptado).
 */
public class PracticaInfoDAO {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PracticaInfo buscarPorAlumno(int alumnoId) {

        if (alumnoId <= 0) {
            return null;
        }

        String sql = "SELECT pa.id_pasantia, pa.horas_requeridas, "
                + "pa.fecha_inicio, pa.fecha_fin, pa.estado, "
                + "u.nombre, u.apellido, c.nombre AS carrera, "
                + "mu.nombre AS m_nombre, mu.apellido AS m_apellido, "
                + "mu.correo AS m_correo, m.telefono AS m_telefono "
                + "FROM pasantias pa "
                + "JOIN alumnos a ON a.id_alumno = pa.id_alumno "
                + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
                + "JOIN carreras c ON c.id_carrera = a.id_carrera "
                + "LEFT JOIN maestros m ON m.id_maestro = pa.id_maestro "
                + "LEFT JOIN usuarios mu ON mu.id_usuario = m.id_usuario "
                + "WHERE pa.id_alumno = ? AND pa.estado <> 'CANCELADA' "
                + "ORDER BY pa.id_pasantia DESC LIMIT 1";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                int pasantiaId = rs.getInt("id_pasantia");

                PracticaInfo p = new PracticaInfo();

                p.setId(pasantiaId);
                p.setAlumnoId(alumnoId);
                p.setAlumno(rs.getString("nombre") + " " + rs.getString("apellido"));
                p.setCarrera(rs.getString("carrera"));
                p.setHorasRequeridas(rs.getInt("horas_requeridas"));
                p.setFechaInicio(fecha(rs.getDate("fecha_inicio")));
                p.setFechaFin(fecha(rs.getDate("fecha_fin")));
                p.setEstado(estadoVista(rs.getString("estado")));

                String mNombre = rs.getString("m_nombre");

                if (mNombre == null) {
                    p.setMaestro("Sin asignar");
                    p.setCorreoMaestro("—");
                    p.setTelefonoMaestro("—");
                } else {
                    p.setMaestro(mNombre + " " + rs.getString("m_apellido"));
                    p.setCorreoMaestro(textoOGuion(rs.getString("m_correo")));
                    p.setTelefonoMaestro(textoOGuion(rs.getString("m_telefono")));
                }

                cargarEmpresas(con, pasantiaId, p);

                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Empresa, area (titulo de la oportunidad) y direccion de cada asignacion. */
    private void cargarEmpresas(Connection con, int pasantiaId, PracticaInfo p)
            throws SQLException {

        String sql = "SELECT e.nombre, e.direccion, o.titulo "
                + "FROM asignaciones_pasantia ap "
                + "JOIN empresas e ON e.id_empresa = ap.id_empresa "
                + "LEFT JOIN oportunidades o ON o.id_oportunidad = ap.id_oportunidad "
                + "WHERE ap.id_pasantia = ? AND ap.estado <> 'CANCELADA' "
                + "ORDER BY ap.id_asignacion";

        StringJoiner empresas = new StringJoiner(" / ");
        StringJoiner areas = new StringJoiner(" / ");
        StringJoiner direcciones = new StringJoiner(" / ");

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pasantiaId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    empresas.add(rs.getString("nombre"));

                    String area = rs.getString("titulo");
                    String dir = rs.getString("direccion");

                    if (area != null && !area.trim().isEmpty()) {
                        areas.add(area);
                    }

                    if (dir != null && !dir.trim().isEmpty()) {
                        direcciones.add(dir);
                    }
                }
            }
        }

        p.setEmpresa(vacio(empresas) ? "Sin empresa asignada" : empresas.toString());
        p.setArea(vacio(areas) ? "—" : areas.toString());
        p.setDireccionEmpresa(vacio(direcciones) ? "—" : direcciones.toString());
    }

    public List<PracticaInfo> listar() {

        List<PracticaInfo> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT DISTINCT id_alumno FROM pasantias "
                     + "WHERE estado <> 'CANCELADA' ORDER BY id_alumno");
             ResultSet rs = ps.executeQuery()) {

            List<Integer> ids = new ArrayList<>();

            while (rs.next()) {
                ids.add(rs.getInt(1));
            }

            for (int id : ids) {

                PracticaInfo p = buscarPorAlumno(id);

                if (p != null) {
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------

    private String fecha(Date d) {
        return d == null ? "—" : d.toLocalDate().format(FORMATO);
    }

    /** La BD guarda ACTIVA; la vista antes mostraba "EN PROCESO". */
    private String estadoVista(String estado) {
        return "ACTIVA".equals(estado) ? "EN PROCESO" : estado;
    }

    private String textoOGuion(String s) {
        return s == null || s.trim().isEmpty() ? "—" : s;
    }

    private boolean vacio(StringJoiner j) {
        return j.length() == 0;
    }
}
