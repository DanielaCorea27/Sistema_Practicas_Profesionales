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

public class EmpresaAdminDAO {

    private static final String SELECT =
            "SELECT e.id_empresa, e.nombre, e.descripcion, e.direccion, e.telefono, "
            + "e.correo, e.sitio_web, e.contacto, e.estado, "
            + "(SELECT COUNT(*) FROM representantes_empresa r "
            + " WHERE r.id_empresa = e.id_empresa) AS total_representantes "
            + "FROM empresas e ";

    private EmpresaAdmin mapear(ResultSet rs) throws SQLException {

        EmpresaAdmin e = new EmpresaAdmin();
        e.setId(rs.getInt("id_empresa"));
        e.setNombre(rs.getString("nombre"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setDireccion(rs.getString("direccion"));
        e.setTelefono(rs.getString("telefono"));
        e.setCorreo(rs.getString("correo"));
        e.setSitioWeb(rs.getString("sitio_web"));
        e.setContacto(rs.getString("contacto"));
        e.setActivo(rs.getBoolean("estado"));
        e.setTotalRepresentantes(rs.getInt("total_representantes"));
        return e;
    }

    public List<EmpresaAdmin> listarTodos() {

        List<EmpresaAdmin> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "ORDER BY e.nombre");
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

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "WHERE e.id_empresa = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeNombre(String nombre) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM empresas WHERE LOWER(nombre) = LOWER(?)")) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertar(EmpresaAdmin empresa) {

        String sql = "INSERT INTO empresas (nombre, descripcion, direccion, telefono, "
                + "correo, sitio_web, contacto, estado) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getDescripcion());
            ps.setString(3, empresa.getDireccion());
            ps.setString(4, empresa.getTelefono());
            ps.setString(5, empresa.getCorreo());
            ps.setString(6, empresa.getSitioWeb());
            ps.setString(7, empresa.getContacto());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    empresa.setId(keys.getInt(1));
                }
            }

            empresa.setActivo(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarEstado(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE empresas SET estado = NOT estado WHERE id_empresa = ?")) {

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
