package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Empresa;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Mismas firmas que la version en memoria, ahora sobre MySQL.
 */
public class EmpresaDAO {

    private static final String SELECT =
            "SELECT id_empresa, nombre, descripcion, direccion, telefono, "
            + "correo, sitio_web, contacto, estado FROM empresas ";

    private Empresa mapear(ResultSet rs) throws SQLException {

        Empresa e = new Empresa(
                rs.getInt("id_empresa"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("contacto"),
                rs.getBoolean("estado")
        );

        e.setDescripcion(rs.getString("descripcion"));
        e.setSitioWeb(rs.getString("sitio_web"));

        return e;
    }

    private List<Empresa> listar(String where) {

        List<Empresa> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     SELECT + where + " ORDER BY nombre");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Empresa> listarTodas() {
        return listar("");
    }

    public List<Empresa> listarActivas() {
        return listar("WHERE estado = 1");
    }

    public Empresa buscarPorId(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     SELECT + "WHERE id_empresa = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertar(Empresa empresa) {

        String sql = "INSERT INTO empresas (nombre, descripcion, direccion, "
                + "telefono, correo, sitio_web, contacto, estado) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getDescripcion());
            ps.setString(3, empresa.getDireccion());
            ps.setString(4, empresa.getTelefono());
            ps.setString(5, empresa.getCorreo());
            ps.setString(6, empresa.getSitioWeb());
            ps.setString(7, empresa.getContacto());
            ps.setBoolean(8, empresa.isActiva());

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

    public boolean actualizar(Empresa empresa) {

        String sql = "UPDATE empresas SET nombre = ?, descripcion = ?, "
                + "direccion = ?, telefono = ?, correo = ?, sitio_web = ?, "
                + "contacto = ?, estado = ? WHERE id_empresa = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getDescripcion());
            ps.setString(3, empresa.getDireccion());
            ps.setString(4, empresa.getTelefono());
            ps.setString(5, empresa.getCorreo());
            ps.setString(6, empresa.getSitioWeb());
            ps.setString(7, empresa.getContacto());
            ps.setBoolean(8, empresa.isActiva());
            ps.setInt(9, empresa.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Devuelve false si la empresa ya tiene datos relacionados
     * (oportunidades, representantes, pasantias). En ese caso
     * conviene desactivarla con cambiarEstado().
     */
    public boolean eliminar(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM empresas WHERE id_empresa = ?")) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

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
}
