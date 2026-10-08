package sv.edu.itca.practicas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import sv.edu.itca.practicas.dao.RegistroCuentaDAO;
import sv.edu.itca.practicas.model.CarreraOpcion;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Validaciones del registro de estudiantes y empresas.
 */
public class RegistroCuentaService {

    private static final Pattern CORREO =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private static final Pattern CARNET =
            Pattern.compile("^[A-Za-z0-9-]{3,20}$");

    public static final int PASSWORD_MIN = 6;
    public static final int PASSWORD_MAX = 64;

    private final RegistroCuentaDAO dao = new RegistroCuentaDAO();

    public List<CarreraOpcion> carreras() {
        return dao.listarCarreras();
    }

    // ------------------------------------------------------------------
    public void registrarEstudiante(Map<String, String> d)
            throws ReglaNegocioException {

        String nombre = texto(d, "nombre", "Nombre", 80, true);
        String apellido = texto(d, "apellido", "Apellido", 80, true);
        String correo = correo(d, "correo", "Correo electrónico", true);
        String password = password(d);
        String telefono = texto(d, "telefono", "Teléfono", 20, false);

        String carnet = texto(d, "carnet", "Carnet", 20, true);

        if (!CARNET.matcher(carnet).matches()) {
            throw new ReglaNegocioException(
                    "El carnet solo puede tener letras, números y guiones "
                    + "(3 a 20 caracteres).");
        }

        int carreraId;

        try {
            carreraId = Integer.parseInt(d.get("carreraId"));
        } catch (Exception e) {
            throw new ReglaNegocioException("Selecciona tu carrera.");
        }

        dao.registrarEstudiante(
                nombre, apellido, correo, password,
                carreraId, carnet, vacioANull(telefono));
    }

    // ------------------------------------------------------------------
    public void registrarEmpresa(Map<String, String> d)
            throws ReglaNegocioException {

        Map<String, String> limpio = new HashMap<>();

        limpio.put("empresaNombre", texto(d, "empresaNombre", "Nombre de la empresa", 150, true));
        limpio.put("descripcion", vacioANull(texto(d, "descripcion", "Descripción", 1000, false)));
        limpio.put("direccion", vacioANull(texto(d, "direccion", "Dirección", 200, false)));
        limpio.put("empresaTelefono", vacioANull(texto(d, "empresaTelefono", "Teléfono de la empresa", 20, false)));
        limpio.put("empresaCorreo", vacioANull(correo(d, "empresaCorreo", "Correo de la empresa", false)));
        limpio.put("sitioWeb", vacioANull(texto(d, "sitioWeb", "Sitio web", 150, false)));

        limpio.put("nombre", texto(d, "nombre", "Nombre del representante", 80, true));
        limpio.put("apellido", texto(d, "apellido", "Apellido del representante", 80, true));
        limpio.put("correo", correo(d, "correo", "Correo del representante", true));
        limpio.put("password", password(d));
        limpio.put("cargo", vacioANull(texto(d, "cargo", "Cargo", 100, false)));

        dao.registrarEmpresa(limpio);
    }

    // ------------------------------------------------------------------
    //  Validaciones comunes
    // ------------------------------------------------------------------
    private String texto(Map<String, String> d, String clave, String etiqueta,
                         int max, boolean obligatorio)
            throws ReglaNegocioException {

        String v = d.get(clave);
        v = v == null ? "" : v.trim();

        if (obligatorio && v.isEmpty()) {
            throw new ReglaNegocioException("Completa el campo: " + etiqueta + ".");
        }

        if (v.length() > max) {
            throw new ReglaNegocioException(
                    etiqueta + " no puede superar " + max + " caracteres.");
        }

        return v;
    }

    private String correo(Map<String, String> d, String clave, String etiqueta,
                          boolean obligatorio)
            throws ReglaNegocioException {

        String v = texto(d, clave, etiqueta, 120, obligatorio).toLowerCase();

        if (!v.isEmpty() && !CORREO.matcher(v).matches()) {
            throw new ReglaNegocioException(
                    etiqueta + " no tiene un formato válido.");
        }

        return v;
    }

    private String password(Map<String, String> d) throws ReglaNegocioException {

        String p1 = d.get("password");
        String p2 = d.get("password2");

        if (p1 == null || p1.length() < PASSWORD_MIN) {
            throw new ReglaNegocioException(
                    "La contraseña debe tener al menos " + PASSWORD_MIN
                    + " caracteres.");
        }

        if (p1.length() > PASSWORD_MAX) {
            throw new ReglaNegocioException(
                    "La contraseña no puede superar " + PASSWORD_MAX
                    + " caracteres.");
        }

        if (!p1.equals(p2)) {
            throw new ReglaNegocioException("Las contraseñas no coinciden.");
        }

        return p1;
    }

    private String vacioANull(String s) {
        return s == null || s.isEmpty() ? null : s;
    }
}
