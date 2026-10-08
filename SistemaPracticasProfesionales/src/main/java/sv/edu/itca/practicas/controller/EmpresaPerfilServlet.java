package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Empresa;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.EmpresaPanelService;

@WebServlet(
        name = "EmpresaPerfilServlet",
        urlPatterns = {"/empresa/perfil"}
)
public class EmpresaPerfilServlet extends HttpServlet {

    private final EmpresaPanelService service = new EmpresaPanelService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        mostrar(request, response, service.obtenerEmpresa(rep.getEmpresaId()));
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        Representante rep = (Representante) session.getAttribute("representante");

        Empresa datos = new Empresa();
        datos.setNombre(request.getParameter("nombre"));
        datos.setDescripcion(request.getParameter("descripcion"));
        datos.setDireccion(request.getParameter("direccion"));
        datos.setTelefono(request.getParameter("telefono"));
        datos.setCorreo(request.getParameter("correo"));
        datos.setSitioWeb(request.getParameter("sitioWeb"));
        datos.setContacto(request.getParameter("contacto"));

        String cargo = request.getParameter("cargo");

        String error = service.guardarPerfil(datos, rep, cargo);

        if (error != null) {

            request.setAttribute("error", error);

            // Devolvemos lo que el usuario escribio para no perderlo.
            datos.setId(rep.getEmpresaId());
            request.setAttribute("cargoForm", cargo);

            mostrar(request, response, datos);

            return;
        }

        // Refrescar el representante guardado en sesion.
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Representante actualizado =
                service.obtenerRepresentantePorUsuario(usuario.getId());

        if (actualizado != null) {
            session.setAttribute("representante", actualizado);
        }

        response.sendRedirect(
                request.getContextPath() + "/empresa/perfil?ok=1");
    }

    private void mostrar(
            HttpServletRequest request,
            HttpServletResponse response,
            Empresa empresa)
            throws ServletException, IOException {

        request.setAttribute("empresa", empresa);
        request.setAttribute("menuActivo", "perfil");

        request.getRequestDispatcher("/empresa/perfil.jsp")
                .forward(request, response);
    }
}
