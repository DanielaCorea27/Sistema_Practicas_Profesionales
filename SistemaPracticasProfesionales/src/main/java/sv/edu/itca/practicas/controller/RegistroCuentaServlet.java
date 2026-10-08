package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.service.RegistroCuentaService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Pantalla 3: Registro de estudiante o empresa. Es publica (sin sesion).
 *   GET  /registro            -> formulario (?tipo=empresa abre la pestana de empresa)
 *   POST /registro            -> crea la cuenta y manda al login
 */
@WebServlet(
        name = "RegistroCuentaServlet",
        urlPatterns = {"/registro"}
)
public class RegistroCuentaServlet extends HttpServlet {

    /** Campos que se aceptan del formulario (las contrasenas no se devuelven). */
    private static final String[] CAMPOS = {
        "nombre", "apellido", "correo", "telefono", "carnet", "carreraId",
        "empresaNombre", "descripcion", "direccion", "empresaTelefono",
        "empresaCorreo", "sitioWeb", "cargo"
    };

    private final RegistroCuentaService service = new RegistroCuentaService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        mostrar(request, response,
                "empresa".equals(request.getParameter("tipo")) ? "empresa" : "estudiante",
                new HashMap<String, String>());
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String tipo = "empresa".equals(request.getParameter("tipo"))
                ? "empresa" : "estudiante";

        Map<String, String> datos = new HashMap<>();

        for (String campo : CAMPOS) {

            String v = request.getParameter(campo);

            if (v != null) {
                datos.put(campo, v);
            }
        }

        // Contrasenas: se leen aparte y no se devuelven al formulario.
        Map<String, String> paraValidar = new HashMap<>(datos);
        paraValidar.put("password", request.getParameter("password"));
        paraValidar.put("password2", request.getParameter("password2"));

        try {

            if ("empresa".equals(tipo)) {
                service.registrarEmpresa(paraValidar);
            } else {
                service.registrarEstudiante(paraValidar);
            }

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp?registro=ok");

        } catch (ReglaNegocioException e) {

            request.setAttribute("error", e.getMessage());

            mostrar(request, response, tipo, datos);
        }
    }

    private void mostrar(
            HttpServletRequest request,
            HttpServletResponse response,
            String tipo,
            Map<String, String> valores)
            throws ServletException, IOException {

        request.setAttribute("tipo", tipo);
        request.setAttribute("valores", valores);
        request.setAttribute("carreras", service.carreras());

        request.getRequestDispatcher("/registro.jsp")
                .forward(request, response);
    }
}
