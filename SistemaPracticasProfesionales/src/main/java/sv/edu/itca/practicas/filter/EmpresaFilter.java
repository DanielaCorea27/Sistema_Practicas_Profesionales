package sv.edu.itca.practicas.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.Usuario;

/**
 * Solo los usuarios con rol EMPRESA entran a /empresa/*.
 */
@WebFilter(
        filterName = "EmpresaFilter",
        urlPatterns = {"/empresa/*"}
)
public class EmpresaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        Usuario usuario = session == null
                ? null
                : (Usuario) session.getAttribute("usuario");

        if (usuario == null) {

            resp.sendRedirect(req.getContextPath() + "/login.jsp");

            return;
        }

        if (usuario.getRol() != Rol.EMPRESA
                || session.getAttribute("representante") == null) {

            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permisos para acceder a esta sección."
            );

            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
