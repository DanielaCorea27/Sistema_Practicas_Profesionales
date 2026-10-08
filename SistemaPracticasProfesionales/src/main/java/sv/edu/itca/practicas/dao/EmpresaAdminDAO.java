package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.EmpresaAdmin;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Vista de empresas para el panel de administrador.
 * Usa la MISMA tabla empresas (el estado se muestra como ACTIVO/INACTIVO).
 */
public class EmpresaAdminDAO {

    private EmpresaAdmin mapear(ResultSet rs) throws SQLException {

        return new EmpresaAdmin(
                rs.getInt("id_empresa"),
                rs.getString("nombre"),
                rs.getString("contacto"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getBoolean("estado") ? "ACTIVO" : "INACTIVO"
        );
    }

    public List<EmpresaAdmin> listarTodos() {

        List<EmpresaAdmin> lista = new ArrayList<>();

        String sql = "SELECT id_empresa, nombre, contacto, telefono, "
                + "correo, estado FROM empresas ORDER BY nombre";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public EmpresaAdmin buscarPorId(int id) {

        String sql = "SELECT id_empresa, nombre, contacto, telefono, "
                + "correo, estado FROM empresas WHERE id_empresa = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertar(EmpresaAdmin empresa) {

        String sql = "INSERT INTO empresas (nombre, contacto, telefono, "
                + "correo, estado) VALUES (?,?,?,?,?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getContacto());
            ps.setString(3, empresa.getTelefono());
            ps.setString(4, empresa.getCorreo());
            ps.setBoolean(5, !"INACTIVO".equalsIgnoreCase(empresa.getEstado()));

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    empresa.setId(keys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarEstado(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE empresas SET estado = NOT estado "
                     + "WHERE id_empresa = ?")) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM empresas");
    }

    public int contarActivas() {
        return contar("SELECT COUNT(*) FROM empresas WHERE estado = 1");
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
