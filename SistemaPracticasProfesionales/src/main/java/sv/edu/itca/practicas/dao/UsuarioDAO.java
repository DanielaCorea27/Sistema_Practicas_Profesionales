package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.util.Conexion;

public class UsuarioDAO {

    private static final String SELECT =
            "SELECT id_usuario, nombre, apellido, correo, password, "
            + "id_rol, estado FROM usuarios ";

    private Usuario mapear(ResultSet rs) throws SQLException {

        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("password"),
                Rol.fromId(rs.getInt("id_rol")),
                rs.getBoolean("estado")
        );
    }

    /**
     * La contrasena se compara con SHA-256 directamente en MySQL.
     */
    public Usuario autenticar(String correo, String password) {

        if (correo == null || password == null) {
            return null;
        }

        String sql = SELECT
                + "WHERE correo = ? AND password = SHA2(?, 256) AND estado = 1";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario buscarPorId(int id) {

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

    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

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

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM usuarios");
    }

    public int contarActivos() {
        return contar("SELECT COUNT(*) FROM usuarios WHERE estado = 1");
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
