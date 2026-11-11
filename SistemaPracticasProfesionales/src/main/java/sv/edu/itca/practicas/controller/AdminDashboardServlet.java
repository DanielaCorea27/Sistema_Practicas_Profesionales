package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.AlumnoAdminService;
import sv.edu.itca.practicas.service.CarreraAdminService;
import sv.edu.itca.practicas.service.EmpresaAdminService;
import sv.edu.itca.practicas.service.MaestroAdminService;
import sv.edu.itca.practicas.service.UsuarioAdminService;

@WebServlet(
        name = "AdminDashboardServlet",
        urlPatterns = {"/admin/dashboard"}
)
public class AdminDashboardServlet extends HttpServlet {

    private UsuarioAdminService usuarioService;
    private AlumnoAdminService alumnoService;
    private MaestroAdminService maestroService;
    private EmpresaAdminService empresaService;
    private CarreraAdminService carreraService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioAdminService();
        alumnoService = new AlumnoAdminService();
        maestroService = new MaestroAdminService();
        empresaService = new EmpresaAdminService();
        carreraService = new CarreraAdminService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("totalUsuarios", usuarioService.contarTodos());
        request.setAttribute("usuariosActivos", usuarioService.contarActivos());
        request.setAttribute("totalAlumnos", alumnoService.contarTodos());
        request.setAttribute("totalMaestros", maestroService.contarTodos());
        request.setAttribute("totalEmpresas", empresaService.contarTodos());
        request.setAttribute("empresasActivas", empresaService.contarActivas());
        request.setAttribute("totalCarreras", carreraService.contarTodos());

        request.getRequestDispatcher("/admin/dashboard.jsp")
                .forward(request, response);
    }
}
