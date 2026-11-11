package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Consultas basicas del maestro logueado.
 */
public class MaestroDAO {

    /**
     * Devuelve el id_maestro que corresponde a un id_usuario,
     * o 0 si ese usuario no tiene perfil en la tabla maestros.
     */
    public int obtenerIdMaestro(int idUsuario) {

        String sql = "SELECT id_maestro FROM maestros WHERE id_usuario = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("id_maestro");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}