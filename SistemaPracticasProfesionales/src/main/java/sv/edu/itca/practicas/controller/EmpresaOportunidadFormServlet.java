package sv.edu.itca.practicas.controller;
/**
 *
 * @author danie
 */
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.Oportunidad;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.OportunidadService;

@WebServlet(
        name = "EmpresaOportunidadFormServlet",
        urlPatterns = {"/empresa/oportunidad"}
)
public class EmpresaOportunidadFormServlet extends HttpServlet {

    private final OportunidadService service = new OportunidadService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        String idParam = request.getParameter("id");

        Oportunidad o = new Oportunidad();

        if (idParam != null && !idParam.isEmpty()) {

            try {

                o = service.buscar(Integer.parseInt(idParam), rep.getEmpresaId());

            } catch (NumberFormatException e) {
                o = null;
            }

            // No existe, es de otra empresa o ya esta cerrada.
            if (o == null || "CERRADA".equals(o.getEstado())) {

                response.sendRedirect(request.getContextPath()
                        + "/empresa/oportunidades?ok=noeditable");

                return;
            }
        }

        mostrar(request, response, o);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        Oportunidad o = new Oportunidad();

        o.setEmpresaId(rep.getEmpresaId());
        o.setRepresentanteId(rep.getId());
        o.setTitulo(request.getParameter("titulo"));
        o.setDescripcion(request.getParameter("descripcion"));
        o.setRequisitos(request.getParameter("requisitos"));
        o.setModalidad(request.getParameter("modalidad"));
        o.setDuracion(request.getParameter("duracion"));

        String error = null;

        try {

            o.setHorasOfrecidas(
                    Integer.parseInt(request.getParameter("horas").trim()));

        } catch (Exception e) {
            error = "Las horas ofrecidas deben ser un número.";
        }

        try {

            o.setFechaInicio(leerFecha(request.getParameter("fechaInicio")));
            o.setFechaFin(leerFecha(request.getParameter("fechaFin")));

        } catch (DateTimeParseException e) {
            error = "Las fechas no tienen un formato válido.";
        }

        String idParam = request.getParameter("id");
        boolean edicion = idParam != null && !idParam.isEmpty();

        if (edicion) {

            try {
                o.setId(Integer.parseInt(idParam));
            } catch (NumberFormatException e) {
                error = "Oportunidad no válida.";
            }
        }

        if (error == null) {
            error = edicion ? service.editar(o) : service.crear(o);
        }

        if (error != null) {

            request.setAttribute("error", error);

            mostrar(request, response, o);

            return;
        }

        response.sendRedirect(request.getContextPath()
                + "/empresa/oportunidades?ok=" + (edicion ? "editada" : "creada"));
    }

    private LocalDate leerFecha(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(texto.trim());
    }

    private void mostrar(
            HttpServletRequest request,
            HttpServletResponse response,
            Oportunidad o)
            throws ServletException, IOException {

        request.setAttribute("oportunidad", o);
        request.setAttribute("menuActivo", "oportunidades");

        request.getRequestDispatcher("/empresa/oportunidad_form.jsp")
                .forward(request, response);
    }
}
