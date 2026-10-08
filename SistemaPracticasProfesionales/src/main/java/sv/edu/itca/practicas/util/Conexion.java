package sv.edu.itca.practicas.util;
/**
 *
 * @author danie
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistema_practicas_db"
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = "admin";                   
    private static final String PASS = "admin";

    public static Connection obtenerConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException e) {

            throw new SQLException("No se pudo cargar el driver de MySQL", e);
        }
    }
}