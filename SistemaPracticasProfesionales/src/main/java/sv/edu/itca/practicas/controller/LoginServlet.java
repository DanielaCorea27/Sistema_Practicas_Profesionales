package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.dao.RepresentanteDAO;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.UsuarioService;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    private final RepresentanteDAO representanteDAO = new RepresentanteDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Usuario usuario = usuarioService.autenticar(correo, password);

        if (usuario == null || usuario.getRol() == null) {

            volverConError(request, response,
                    "Correo o contraseña incorrectos.");

            return;
        }

        // Un usuario EMPRESA debe estar ligado a una empresa.
        Representante representante = null;

        if (usuario.getRol() == Rol.EMPRESA) {

            representante =
                    representanteDAO.buscarPorUsuario(usuario.getId());

            if (representante == null) {

                volverConError(request, response,
                        "Tu usuario no está asociado a ninguna empresa activa.");

                return;
            }
        }

        // Nueva sesion para evitar reutilizar una sesion anterior.
        HttpSession anterior = request.getSession(false);

        if (anterior != null) {
            anterior.invalidate();
        }

        HttpSession session = request.getSession(true);

        session.setAttribute("usuario", usuario);
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("rol", usuario.getRol());

        if (representante != null) {
            session.setAttribute("representante", representante);
        }

        String ctx = request.getContextPath();

        switch (usuario.getRol()) {

            case ADMINISTRADOR:
                response.sendRedirect(ctx + "/admin/dashboard.jsp");
                return;

            case MAESTRO:
                response.sendRedirect(ctx + "/maestro/dashboard");
                return;

            case ALUMNO:
                response.sendRedirect(ctx + "/alumno/dashboard");
                return;

            case EMPRESA:
                response.sendRedirect(ctx + "/empresa/dashboard");
                return;

            default:
                session.invalidate();
                response.sendRedirect(ctx + "/login.jsp");
        }
    }

    private void volverConError(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
