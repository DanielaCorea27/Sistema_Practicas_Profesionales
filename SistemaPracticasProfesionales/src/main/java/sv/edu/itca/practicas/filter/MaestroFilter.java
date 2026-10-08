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

@WebFilter(
        filterName = "MaestroFilter",
        urlPatterns = {"/maestro/*"}
)
public class MaestroFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse resp =
                (HttpServletResponse) response;

        HttpSession session =
                req.getSession(false);

        if (session == null) {

            resp.sendRedirect(
                    req.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        Usuario usuario =
                (Usuario) session.getAttribute(
                        "usuario"
                );

        if (usuario == null) {

            resp.sendRedirect(
                    req.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        Rol rol = usuario.getRol();

        if (rol != Rol.MAESTRO
                && rol != Rol.ADMINISTRADOR) {

            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permisos para acceder a esta sección."
            );

            return;
        }

        chain.doFilter(
                request,
                response
        );
    }

    @Override
    public void destroy() {
    }
}