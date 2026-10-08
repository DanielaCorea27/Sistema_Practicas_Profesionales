package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.EmpresaAdmin;
import sv.edu.itca.practicas.service.EmpresaAdminService;

@WebServlet(
        name = "AdminEmpresasServlet",
        urlPatterns = {"/admin/empresas"}
)
public class AdminEmpresasServlet extends HttpServlet {

    private EmpresaAdminService service;

    @Override
    public void init() throws ServletException {

        service = new EmpresaAdminService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");

        if ("cambiarEstado".equals(accion)) {

            try {

                int id =
                        Integer.parseInt(
                                request.getParameter("id")
                        );

                service.cambiarEstado(id);

            } catch (Exception e) {
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/empresas"
            );

            return;
        }

        request.setAttribute(
                "empresas",
                service.listarTodos()
        );

        request.setAttribute(
                "totalEmpresas",
                service.contarTodos()
        );

        request.setAttribute(
                "empresasActivas",
                service.contarActivas()
        );

        request.getRequestDispatcher(
                "/admin/empresas.jsp"
        ).forward(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        EmpresaAdmin empresa =
                new EmpresaAdmin();

        empresa.setNombre(
                request.getParameter("nombre")
        );

        empresa.setContacto(
                request.getParameter("contacto")
        );

        empresa.setTelefono(
                request.getParameter("telefono")
        );

        empresa.setCorreo(
                request.getParameter("correo")
        );

        service.guardar(empresa);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/empresas"
        );
    }
}