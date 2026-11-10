package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.UsuarioAdmin;
import sv.edu.itca.practicas.util.Conexion;

public class UsuarioAdminDAO {

    private static final String SELECT =
            "SELECT id_usuario, nombre, apellido, correo, id_rol, estado "
            + "FROM usuarios ";

    private UsuarioAdmin mapear(ResultSet rs) throws SQLException {

        return new UsuarioAdmin(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                Rol.fromId(rs.getInt("id_rol")),
                rs.getBoolean("estado")
        );
    }

    public List<UsuarioAdmin> listarTodos() {

        List<UsuarioAdmin> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     SELECT + "ORDER BY id_usuario");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public UsuarioAdmin buscarPorId(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     SELECT + "WHERE id_usuario = ?")) {

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

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM usuarios WHERE correo = ?")) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertar(UsuarioAdmin usuario) {

        String sql = "INSERT INTO usuarios (id_rol, nombre, apellido, correo, "
                + "password, estado) VALUES (?, ?, ?, ?, SHA2(?, 256), 1)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, usuario.getRolEnum().getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getPassword());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    usuario.setId(keys.getInt(1));
                }
            }

            usuario.setActivo(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarEstado(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE usuarios SET estado = NOT estado "
                     + "WHERE id_usuario = ?")) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM usuarios", null);
    }

    public int contarActivos() {
        return contar("SELECT COUNT(*) FROM usuarios WHERE estado = 1", null);
    }

    public int contarPorRol(Rol rol) {
        return contar("SELECT COUNT(*) FROM usuarios WHERE id_rol = ?", rol.getId());
    }

    private int contar(String sql, Integer parametro) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setInt(1, parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}