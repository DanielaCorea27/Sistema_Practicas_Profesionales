package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Oportunidad;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Acceso a la tabla oportunidades.
 * Los metodos de la empresa reciben empresaId para que una empresa
 * nunca pueda ver ni modificar oportunidades de otra.
 */
public class OportunidadDAO {

    private static final String SELECT =
            "SELECT o.id_oportunidad, o.id_empresa, o.id_representante, "
            + "o.titulo, o.descripcion, o.requisitos, o.modalidad, "
            + "o.duracion, o.horas_ofrecidas, o.fecha_inicio, o.fecha_fin, "
            + "o.estado, o.observacion, "
            + "(SELECT COUNT(*) FROM postulaciones p "
            + " WHERE p.id_oportunidad = o.id_oportunidad) AS total_post "
            + "FROM oportunidades o ";

    private Oportunidad mapear(ResultSet rs) throws SQLException {

        Oportunidad o = new Oportunidad();

        o.setId(rs.getInt("id_oportunidad"));
        o.setEmpresaId(rs.getInt("id_empresa"));
        o.setRepresentanteId(rs.getInt("id_representante"));
        o.setTitulo(rs.getString("titulo"));
        o.setDescripcion(rs.getString("descripcion"));
        o.setRequisitos(rs.getString("requisitos"));
        o.setModalidad(rs.getString("modalidad"));
        o.setDuracion(rs.getString("duracion"));
        o.setHorasOfrecidas(rs.getInt("horas_ofrecidas"));

        Date ini = rs.getDate("fecha_inicio");
        Date fin = rs.getDate("fecha_fin");

        o.setFechaInicio(ini == null ? null : ini.toLocalDate());
        o.setFechaFin(fin == null ? null : fin.toLocalDate());

        o.setEstado(rs.getString("estado"));
        o.setObservacion(rs.getString("observacion"));
        o.setTotalPostulaciones(rs.getInt("total_post"));

        return o;
    }

    public List<Oportunidad> listarPorEmpresa(int empresaId) {

        List<Oportunidad> lista = new ArrayList<>();

        String sql = SELECT
                + "WHERE o.id_empresa = ? ORDER BY o.id_oportunidad DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Oportunidad buscarPorIdYEmpresa(int id, int empresaId) {

        String sql = SELECT
                + "WHERE o.id_oportunidad = ? AND o.id_empresa = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, empresaId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Las oportunidades nuevas siempre nacen PENDIENTE. */
    public boolean insertar(Oportunidad o) {

        String sql = "INSERT INTO oportunidades (id_empresa, id_representante, "
                + "titulo, descripcion, requisitos, modalidad, duracion, "
                + "horas_ofrecidas, fecha_inicio, fecha_fin, estado) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?, 'PENDIENTE')";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, o.getEmpresaId());
            ps.setInt(2, o.getRepresentanteId());
            ps.setString(3, o.getTitulo());
            ps.setString(4, o.getDescripcion());
            ps.setString(5, o.getRequisitos());
            ps.setString(6, o.getModalidad());
            ps.setString(7, o.getDuracion());
            ps.setInt(8, o.getHorasOfrecidas());
            asignarFecha(ps, 9, o.getFechaInicio());
            asignarFecha(ps, 10, o.getFechaFin());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    o.setId(keys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos y la regresa a PENDIENTE para que el maestro
     * la vuelva a revisar. No permite editar oportunidades CERRADAS.
     */
    public boolean actualizar(Oportunidad o) {

        String sql = "UPDATE oportunidades SET titulo = ?, descripcion = ?, "
                + "requisitos = ?, modalidad = ?, duracion = ?, "
                + "horas_ofrecidas = ?, fecha_inicio = ?, fecha_fin = ?, "
                + "estado = 'PENDIENTE', observacion = NULL "
                + "WHERE id_oportunidad = ? AND id_empresa = ? "
                + "AND estado <> 'CERRADA'";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, o.getTitulo());
            ps.setString(2, o.getDescripcion());
            ps.setString(3, o.getRequisitos());
            ps.setString(4, o.getModalidad());
            ps.setString(5, o.getDuracion());
            ps.setInt(6, o.getHorasOfrecidas());
            asignarFecha(ps, 7, o.getFechaInicio());
            asignarFecha(ps, 8, o.getFechaFin());
            ps.setInt(9, o.getId());
            ps.setInt(10, o.getEmpresaId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cierra la oportunidad: deja de recibir postulaciones. */
    public boolean cerrar(int id, int empresaId) {

        String sql = "UPDATE oportunidades SET estado = 'CERRADA' "
                + "WHERE id_oportunidad = ? AND id_empresa = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, empresaId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void asignarFecha(PreparedStatement ps, int indice,
                              LocalDate fecha) throws SQLException {

        if (fecha == null) {
            ps.setNull(indice, Types.DATE);
        } else {
            ps.setDate(indice, Date.valueOf(fecha));
        }
    }
}
