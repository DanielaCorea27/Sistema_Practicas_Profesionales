package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import sv.edu.itca.practicas.model.CarreraOpcion;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.util.Conexion;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Registro de cuentas nuevas (pantalla 3): estudiante o empresa.
 * Cada registro se guarda en UNA transaccion. La contrasena se guarda
 * con SHA-256 (igual que la compara el login).
 */
public class RegistroCuentaDAO {

    private static final String YA_REGISTRADO =
            "El correo o el carnet ya están registrados.";

    public List<CarreraOpcion> listarCarreras() {

        List<CarreraOpcion> lista = new ArrayList<>();

        String sql = "SELECT c.id_carrera, c.nombre, t.nombre AS tipo, "
                + "t.horas_requeridas FROM carreras c "
                + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
                + "ORDER BY t.horas_requeridas DESC, c.nombre";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                CarreraOpcion c = new CarreraOpcion();

                c.setId(rs.getInt("id_carrera"));
                c.setNombre(rs.getString("nombre"));
                c.setTipo(rs.getString("tipo"));
                c.setHorasRequeridas(rs.getInt("horas_requeridas"));

                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ------------------------------------------------------------------
    //  Estudiante: usuario (ALUMNO) + alumno
    // ------------------------------------------------------------------
    public void registrarEstudiante(
            String nombre, String apellido, String correo, String password,
            int carreraId, String carnet, String telefono)
            throws ReglaNegocioException {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {

                if (existe(con, "SELECT COUNT(*) FROM usuarios WHERE correo = ?", correo)
                        || existe(con, "SELECT COUNT(*) FROM alumnos WHERE carnet = ?", carnet)) {

                    throw new ReglaNegocioException(YA_REGISTRADO);
                }

                if (!existe(con,
                        "SELECT COUNT(*) FROM carreras WHERE id_carrera = ?",
                        String.valueOf(carreraId))) {

                    throw new ReglaNegocioException("Selecciona una carrera válida.");
                }

                int usuarioId = insertarUsuario(
                        con, Rol.ALUMNO.getId(), nombre, apellido, correo, password);

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO alumnos (id_usuario, id_carrera, carnet, telefono) "
                        + "VALUES (?,?,?,?)")) {

                    ps.setInt(1, usuarioId);
                    ps.setInt(2, carreraId);
                    ps.setString(3, carnet);
                    ps.setString(4, telefono);
                    ps.executeUpdate();
                }

                con.commit();

            } catch (ReglaNegocioException e) {

                con.rollback();
                throw e;

            } catch (SQLIntegrityConstraintViolationException e) {

                con.rollback();
                throw new ReglaNegocioException(YA_REGISTRADO);

            } catch (SQLException e) {

                con.rollback();
                throw e;

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new ReglaNegocioException(
                    "Ocurrió un error en la base de datos. Intenta de nuevo.");
        }
    }

    // ------------------------------------------------------------------
    //  Empresa: empresa + usuario (EMPRESA) + representante
    // ------------------------------------------------------------------
    public void registrarEmpresa(Map<String, String> d)
            throws ReglaNegocioException {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {

                if (existe(con, "SELECT COUNT(*) FROM usuarios WHERE correo = ?",
                        d.get("correo"))) {

                    throw new ReglaNegocioException(
                            "Ese correo ya está registrado.");
                }

                int empresaId;

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO empresas (nombre, descripcion, direccion, "
                        + "telefono, correo, sitio_web, contacto, estado) "
                        + "VALUES (?,?,?,?,?,?,?,1)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, d.get("empresaNombre"));
                    ps.setString(2, d.get("descripcion"));
                    ps.setString(3, d.get("direccion"));
                    ps.setString(4, d.get("empresaTelefono"));
                    ps.setString(5, d.get("empresaCorreo"));
                    ps.setString(6, d.get("sitioWeb"));
                    ps.setString(7, d.get("nombre") + " " + d.get("apellido"));
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {

                        keys.next();
                        empresaId = keys.getInt(1);
                    }
                }

                int usuarioId = insertarUsuario(
                        con, Rol.EMPRESA.getId(),
                        d.get("nombre"), d.get("apellido"),
                        d.get("correo"), d.get("password"));

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO representantes_empresa "
                        + "(id_usuario, id_empresa, cargo) VALUES (?,?,?)")) {

                    ps.setInt(1, usuarioId);
                    ps.setInt(2, empresaId);
                    ps.setString(3, d.get("cargo"));
                    ps.executeUpdate();
                }

                con.commit();

            } catch (ReglaNegocioException e) {

                con.rollback();
                throw e;

            } catch (SQLIntegrityConstraintViolationException e) {

                con.rollback();
                throw new ReglaNegocioException("Ese correo ya está registrado.");

            } catch (SQLException e) {

                con.rollback();
                throw e;

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new ReglaNegocioException(
                    "Ocurrió un error en la base de datos. Intenta de nuevo.");
        }
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------
    private int insertarUsuario(
            Connection con, int rolId, String nombre, String apellido,
            String correo, String password)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO usuarios (id_rol, nombre, apellido, correo, "
                + "password, estado) VALUES (?,?,?,?, SHA2(?, 256), 1)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, rolId);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, correo);
            ps.setString(5, password);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                keys.next();
                return keys.getInt(1);
            }
        }
    }

    private boolean existe(Connection con, String sql, String valor)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, valor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
