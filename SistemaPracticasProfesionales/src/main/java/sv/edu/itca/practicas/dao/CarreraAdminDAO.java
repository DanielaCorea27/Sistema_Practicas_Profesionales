package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.Carrera;
import sv.edu.itca.practicas.model.TipoCarrera;
import sv.edu.itca.practicas.util.Conexion;

public class CarreraAdminDAO {

    private static final String SELECT =
            "SELECT c.id_carrera, c.nombre, c.id_tipo_carrera, "
            + "t.nombre AS tipo, t.horas_requeridas, "
            + "(SELECT COUNT(*) FROM alumnos a WHERE a.id_carrera = c.id_carrera) AS total_alumnos "
            + "FROM carreras c "
            + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera ";

    private Carrera mapear(ResultSet rs) throws SQLException {

        Carrera c = new Carrera(
                rs.getInt("id_carrera"),
                rs.getString("nombre"),
                rs.getInt("id_tipo_carrera"),
                rs.getString("tipo"),
                rs.getInt("horas_requeridas")
        );
        c.setTotalAlumnos(rs.getInt("total_alumnos"));
        return c;
    }

    public List<Carrera> listarTodos() {

        List<Carrera> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "ORDER BY c.nombre");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Carrera buscarPorId(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "WHERE c.id_carrera = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TipoCarrera> listarTipos() {

        List<TipoCarrera> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT id_tipo_carrera, nombre, horas_requeridas "
                     + "FROM tipo_carrera ORDER BY horas_requeridas");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoCarrera(
                        rs.getInt("id_tipo_carrera"),
                        rs.getString("nombre"),
                        rs.getInt("horas_requeridas")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existeTipo(int idTipo) {
        return contar("SELECT COUNT(*) FROM tipo_carrera WHERE id_tipo_carrera = ?", idTipo) > 0;
    }

    public boolean existeNombre(String nombre) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM carreras WHERE LOWER(nombre) = LOWER(?)")) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertar(Carrera carrera) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO carreras (id_tipo_carrera, nombre) VALUES (?, ?)")) {

            ps.setInt(1, carrera.getIdTipoCarrera());
            ps.setString(2, carrera.getNombre());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarAlumnos(int idCarrera) {
        return contar("SELECT COUNT(*) FROM alumnos WHERE id_carrera = ?", idCarrera);
    }

    public boolean eliminar(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM carreras WHERE id_carrera = ?")) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM carreras", null);
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
