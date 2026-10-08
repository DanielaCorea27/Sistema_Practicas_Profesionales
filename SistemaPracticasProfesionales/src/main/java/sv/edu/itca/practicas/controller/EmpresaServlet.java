package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.Empresa;
import sv.edu.itca.practicas.service.EmpresaService;

@WebServlet(
        name = "EmpresaServlet",
        urlPatterns = {"/admin/empresa"}
)
public class EmpresaServlet extends HttpServlet {

    private EmpresaService empresaService;

    @Override
    public void init() {

        empresaService = new EmpresaService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion =
                request.getParameter("accion");

        if (accion == null || accion.isEmpty()) {

            listar(request, response);

            return;
        }

        switch (accion) {

            case "nuevo":

                nuevo(request, response);

                break;

            case "editar":

                editar(request, response);

                break;

            case "cambiarEstado":

                cambiarEstado(request, response);

                break;

            case "eliminar":

                eliminar(request, response);

                break;

            default:

                listar(request, response);

                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion =
                request.getParameter("accion");

        if ("guardar".equals(accion)) {

            guardar(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/empresas"
            );
        }
    }

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "empresas",
                empresaService.listarTodas()
        );

        request.getRequestDispatcher(
                "/admin/empresas.jsp"
        ).forward(
                request,
                response
        );
    }

    private void nuevo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Empresa empresa =
                new Empresa();

        empresa.setActiva(true);

        request.setAttribute(
                "empresa",
                empresa
        );

        request.setAttribute(
                "modo",
                "nuevo"
        );

        request.getRequestDispatcher(
                "/admin/empresa-form.jsp"
        ).forward(
                request,
                response
        );
    }

    private void editar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            Empresa empresa =
                    empresaService.buscarPorId(id);

            if (empresa == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La empresa no existe."
                );

                return;
            }

            request.setAttribute(
                    "empresa",
                    empresa
            );

            request.setAttribute(
                    "modo",
                    "editar"
            );

            request.getRequestDispatcher(
                    "/admin/empresa-form.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de empresa inválido."
            );
        }
    }

    private void guardar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro =
                request.getParameter("id");

        String nombre =
                request.getParameter("nombre");

        String direccion =
                request.getParameter("direccion");

        String telefono =
                request.getParameter("telefono");

        String correo =
                request.getParameter("correo");

        String contacto =
                request.getParameter("contacto");

        String activaParametro =
                request.getParameter("activa");

        int id = 0;

        if (idParametro != null
                && !idParametro.trim().isEmpty()) {

            try {

                id = Integer.parseInt(idParametro);

            } catch (NumberFormatException e) {

                mostrarError(
                        request,
                        response,
                        "El ID de la empresa no es válido.",
                        construirEmpresa(
                                id,
                                nombre,
                                direccion,
                                telefono,
                                correo,
                                contacto,
                                activaParametro
                        )
                );

                return;
            }
        }

        boolean activa =
                "true".equals(activaParametro);

        Empresa empresa =
                construirEmpresa(
                        id,
                        nombre,
                        direccion,
                        telefono,
                        correo,
                        contacto,
                        activaParametro
                );

        empresa.setActiva(activa);

        String error =
                validar(empresa);

        if (error != null) {

            mostrarError(
                    request,
                    response,
                    error,
                    empresa
            );

            return;
        }

        boolean resultado =
                empresaService.guardar(empresa);

        if (resultado) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/empresas"
            );

        } else {

            mostrarError(
                    request,
                    response,
                    "No fue posible guardar la empresa.",
                    empresa
            );
        }
    }

    private Empresa construirEmpresa(
            int id,
            String nombre,
            String direccion,
            String telefono,
            String correo,
            String contacto,
            String activaParametro) {

        Empresa empresa =
                new Empresa();

        empresa.setId(id);

        empresa.setNombre(
                nombre != null
                        ? nombre.trim()
                        : ""
        );

        empresa.setDireccion(
                direccion != null
                        ? direccion.trim()
                        : ""
        );

        empresa.setTelefono(
                telefono != null
                        ? telefono.trim()
                        : ""
        );

        empresa.setCorreo(
                correo != null
                        ? correo.trim()
                        : ""
        );

        empresa.setContacto(
                contacto != null
                        ? contacto.trim()
                        : ""
        );

        empresa.setActiva(
                "true".equals(activaParametro)
        );

        return empresa;
    }

    private String validar(
            Empresa empresa) {

        if (empresa.getNombre().isEmpty()) {

            return "Debe ingresar el nombre de la empresa.";
        }

        if (empresa.getDireccion().isEmpty()) {

            return "Debe ingresar la dirección.";
        }

        if (empresa.getTelefono().isEmpty()) {

            return "Debe ingresar el teléfono.";
        }

        if (empresa.getCorreo().isEmpty()) {

            return "Debe ingresar el correo electrónico.";
        }

        if (!empresa.getCorreo().contains("@")) {

            return "Ingrese un correo electrónico válido.";
        }

        if (empresa.getContacto().isEmpty()) {

            return "Debe ingresar el nombre del contacto.";
        }

        return null;
    }

    private void mostrarError(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje,
            Empresa empresa)
            throws ServletException, IOException {

        request.setAttribute(
                "error",
                mensaje
        );

        request.setAttribute(
                "empresa",
                empresa
        );

        request.setAttribute(
                "modo",
                empresa.getId() == 0
                        ? "nuevo"
                        : "editar"
        );

        request.getRequestDispatcher(
                "/admin/empresa-form.jsp"
        ).forward(
                request,
                response
        );
    }

    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            empresaService.cambiarEstado(id);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/empresas"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID inválido."
            );
        }
    }

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            empresaService.eliminar(id);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/empresas"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID inválido."
            );
        }
    }
}