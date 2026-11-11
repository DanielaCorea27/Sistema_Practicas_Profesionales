package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.MaestroService;

import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(
        name = "MaestroHorasServlet",
        urlPatterns = {"/maestro/horas"}
)
public class MaestroHorasServlet extends HttpServlet {

    private RegistroHoraService service;
    private MaestroService maestroService;

    @Override
    public void init() {

        service = new RegistroHoraService();
        maestroService = new MaestroService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<RegistroHora> registros;

        if (usuario.getRol() == Rol.ADMINISTRADOR) {

            // El admin puede entrar a /maestro/*, así que ve todo
            registros = service.listarTodos();

        } else {

            // El maestro solo ve a sus alumnos
            int maestroId = maestroService.obtenerIdMaestro(usuario.getId());
            registros = service.listarPorMaestro(maestroId);
        }

        int pendientes = 0;

        for (RegistroHora r : registros) {

            if ("PENDIENTE".equals(r.getEstado())) {
                pendientes++;
            }
        }

        request.setAttribute("registros", registros);
        request.setAttribute("pendientes", pendientes);

        request.getRequestDispatcher("/maestro/horas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion
                = request.getParameter("accion");

        String idParametro
                = request.getParameter("id");

        String observacion
                = request.getParameter(
                        "observacion"
                );

        int id;

        try {

            id = Integer.parseInt(
                    idParametro
            );

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?error=id"
            );

            return;
        }
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario.getRol() != Rol.ADMINISTRADOR) {

            int maestroId = maestroService.obtenerIdMaestro(usuario.getId());

            if (!service.perteneceAMaestro(id, maestroId)) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/maestro/horas?error=accion"
                );

                return;
            }
        }
        boolean resultado = false;

        if ("aprobar".equals(accion)) {

            resultado
                    = service.aprobar(
                            id,
                            observacion
                    );

        } else if ("rechazar".equals(accion)) {

            resultado
                    = service.rechazar(
                            id,
                            observacion
                    );
        }

        if (resultado) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?mensaje=ok"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?error=accion"
            );
        }

    }

}
