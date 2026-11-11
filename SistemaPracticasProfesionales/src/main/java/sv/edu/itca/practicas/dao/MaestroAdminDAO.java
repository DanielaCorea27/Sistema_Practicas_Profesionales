package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.MaestroAdmin;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.util.Conexion;

public class MaestroAdminDAO {

    private static final String SELECT =
            "SELECT m.id_maestro, m.id_usuario, m.codigo_empleado, m.especialidad, m.telefono, "
            + "u.nombre, u.apellido, u.correo, u.estado "
            + "FROM maestros m "
            + "JOIN usuarios u ON u.id_usuario = m.id_usuario ";

    private MaestroAdmin mapear(ResultSet rs) throws SQLException {

        MaestroAdmin m = new MaestroAdmin();
        m.setId(rs.getInt("id_maestro"));
        m.setIdUsuario(rs.getInt("id_usuario"));
        m.setNombre(rs.getString("nombre"));
        m.setApellido(rs.getString("apellido"));
        m.setCorreo(rs.getString("correo"));
        m.setCodigoEmpleado(rs.getString("codigo_empleado"));
        m.setEspecialidad(rs.getString("especialidad"));
        m.setTelefono(rs.getString("telefono"));
        m.setActivo(rs.getBoolean("estado"));
        return m;
    }

    public List<MaestroAdmin> listarTodos() {

        List<MaestroAdmin> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "ORDER BY m.id_maestro");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public MaestroAdmin buscarPorId(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "WHERE m.id_maestro = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeCorreo(String correo) {
        return existe("SELECT COUNT(*) FROM usuarios WHERE correo = ?", correo);
    }

    public boolean existeCodigo(String codigo) {
        return existe("SELECT COUNT(*) FROM maestros WHERE codigo_empleado = ?", codigo);
    }

    public boolean insertar(MaestroAdmin maestro) {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {
                int idUsuario = insertarUsuario(con, maestro);

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO maestros (id_usuario, codigo_empleado, especialidad, telefono) "
                        + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, idUsuario);
                    ps.setString(2, maestro.getCodigoEmpleado());
                    ps.setString(3, maestro.getEspecialidad());
                    ps.setString(4, maestro.getTelefono());
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            maestro.setId(keys.getInt(1));
                        }
                    }
                }

                con.commit();
                maestro.setIdUsuario(idUsuario);
                maestro.setActivo(true);
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarEstado(int idMaestro) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE usuarios u JOIN maestros m ON m.id_usuario = u.id_usuario "
                     + "SET u.estado = NOT u.estado WHERE m.id_maestro = ?")) {

            ps.setInt(1, idMaestro);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM maestros");
    }

    public int contarActivos() {
        return contar("SELECT COUNT(*) FROM maestros m "
                + "JOIN usuarios u ON u.id_usuario = m.id_usuario WHERE u.estado = 1");
    }

    private int insertarUsuario(Connection con, MaestroAdmin maestro) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO usuarios (id_rol, nombre, apellido, correo, password, estado) "
                + "VALUES (?, ?, ?, ?, SHA2(?, 256), 1)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, Rol.MAESTRO.getId());
            ps.setString(2, maestro.getNombre());
            ps.setString(3, maestro.getApellido());
            ps.setString(4, maestro.getCorreo());
            ps.setString(5, maestro.getPassword());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("No se generó el id del usuario.");
                }
                return keys.getInt(1);
            }
        }
    }

    private boolean existe(String sql, String valor) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, valor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int contar(String sql) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
