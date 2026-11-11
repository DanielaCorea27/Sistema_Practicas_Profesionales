package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.AlumnoAdmin;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.util.Conexion;

public class AlumnoAdminDAO {

    private static final String SELECT =
            "SELECT a.id_alumno, a.id_usuario, a.carnet, a.telefono, a.id_carrera, "
            + "c.nombre AS carrera, u.nombre, u.apellido, u.correo, u.estado "
            + "FROM alumnos a "
            + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
            + "JOIN carreras c ON c.id_carrera = a.id_carrera ";

    private AlumnoAdmin mapear(ResultSet rs) throws SQLException {

        AlumnoAdmin a = new AlumnoAdmin();
        a.setId(rs.getInt("id_alumno"));
        a.setIdUsuario(rs.getInt("id_usuario"));
        a.setNombre(rs.getString("nombre"));
        a.setApellido(rs.getString("apellido"));
        a.setCorreo(rs.getString("correo"));
        a.setCarnet(rs.getString("carnet"));
        a.setTelefono(rs.getString("telefono"));
        a.setIdCarrera(rs.getInt("id_carrera"));
        a.setCarrera(rs.getString("carrera"));
        a.setActivo(rs.getBoolean("estado"));
        return a;
    }

    public List<AlumnoAdmin> listarTodos() {

        List<AlumnoAdmin> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "ORDER BY a.id_alumno");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public AlumnoAdmin buscarPorId(int id) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SELECT + "WHERE a.id_alumno = ?")) {

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

    public boolean existeCarnet(String carnet) {
        return existe("SELECT COUNT(*) FROM alumnos WHERE carnet = ?", carnet);
    }

    public boolean existeCarrera(int idCarrera) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM carreras WHERE id_carrera = ?")) {

            ps.setInt(1, idCarrera);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertar(AlumnoAdmin alumno) {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {
                int idUsuario = insertarUsuario(con, alumno);

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO alumnos (id_usuario, id_carrera, carnet, telefono) "
                        + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, idUsuario);
                    ps.setInt(2, alumno.getIdCarrera());
                    ps.setString(3, alumno.getCarnet());
                    ps.setString(4, alumno.getTelefono());
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            alumno.setId(keys.getInt(1));
                        }
                    }
                }

                con.commit();
                alumno.setIdUsuario(idUsuario);
                alumno.setActivo(true);
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

    public boolean cambiarEstado(int idAlumno) {

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE usuarios u JOIN alumnos a ON a.id_usuario = u.id_usuario "
                     + "SET u.estado = NOT u.estado WHERE a.id_alumno = ?")) {

            ps.setInt(1, idAlumno);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarTodos() {
        return contar("SELECT COUNT(*) FROM alumnos");
    }

    public int contarActivos() {
        return contar("SELECT COUNT(*) FROM alumnos a "
                + "JOIN usuarios u ON u.id_usuario = a.id_usuario WHERE u.estado = 1");
    }

    private int insertarUsuario(Connection con, AlumnoAdmin alumno) throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO usuarios (id_rol, nombre, apellido, correo, password, estado) "
                + "VALUES (?, ?, ?, ?, SHA2(?, 256), 1)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, Rol.ALUMNO.getId());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido());
            ps.setString(4, alumno.getCorreo());
            ps.setString(5, alumno.getPassword());
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
