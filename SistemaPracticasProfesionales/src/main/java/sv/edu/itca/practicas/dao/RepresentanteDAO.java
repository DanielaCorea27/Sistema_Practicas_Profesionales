package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.util.Conexion;

public class RepresentanteDAO {

    /**
     * Busca el representante asociado a un usuario con rol EMPRESA.
     * Devuelve null si el usuario no esta ligado a ninguna empresa ACTIVA.
     */
    public Representante buscarPorUsuario(int usuarioId) {

        String sql =
                "SELECT r.id_representante, r.id_usuario, r.id_empresa, "
                + "r.cargo, e.nombre AS empresa_nombre "
                + "FROM representantes_empresa r "
                + "JOIN empresas e ON e.id_empresa = r.id_empresa "
                + "WHERE r.id_usuario = ? AND e.estado = 1";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Representante(
                            rs.getInt("id_representante"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_empresa"),
                            rs.getString("empresa_nombre"),
                            rs.getString("cargo")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean actualizarCargo(int representanteId, String cargo) {

        String sql = "UPDATE representantes_empresa SET cargo = ? "
                + "WHERE id_representante = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cargo);
            ps.setInt(2, representanteId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
