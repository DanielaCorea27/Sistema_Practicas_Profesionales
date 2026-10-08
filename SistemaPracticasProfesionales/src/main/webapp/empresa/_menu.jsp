<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Usuario" %>
<%@ page import="sv.edu.itca.practicas.model.Representante" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();
    String activo = (String) request.getAttribute("menuActivo");
    Usuario u = (Usuario) session.getAttribute("usuario");
    Representante r = (Representante) session.getAttribute("representante");
%>
<nav class="navbar navbar-expand-lg topbar mb-4">
    <div class="container-xl">

        <a class="navbar-brand fw-bold" href="<%= ctx %>/empresa/dashboard">
            <i class="bi bi-building"></i>
            <%= Html.esc(r != null ? r.getEmpresaNombre() : "Empresa") %>
        </a>

        <button class="navbar-toggler bg-light" type="button"
                data-bs-toggle="collapse" data-bs-target="#menuEmpresa">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menuEmpresa">

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">
                    <a class="nav-link <%= "dashboard".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/dashboard">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link <%= "perfil".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/perfil">
                        <i class="bi bi-person-badge"></i> Perfil
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link <%= "oportunidades".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/oportunidades">
                        <i class="bi bi-briefcase"></i> Oportunidades
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link <%= "postulantes".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/postulantes">
                        <i class="bi bi-people"></i> Postulantes
                    </a>
                </li>

                <%-- Se habilita en la siguiente fase --%>

                <li class="nav-item">
                    <a class="nav-link <%= "estudiantes".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/estudiantes">
                        <i class="bi bi-mortarboard"></i> En práctica
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link <%= "evaluaciones".equals(activo) ? "active" : "" %>"
                       href="<%= ctx %>/empresa/evaluaciones">
                        <i class="bi bi-clipboard-check"></i> Evaluaciones
                    </a>
                </li>

            </ul>

            <span class="text-white me-3 small">
                <i class="bi bi-person-circle"></i>
                <%= Html.esc(u != null ? u.getNombreCompleto() : "") %>
            </span>

            <a class="btn btn-sm btn-outline-light" href="<%= ctx %>/logout"
               data-confirm="¿Deseas cerrar tu sesión?"
               data-confirm-titulo="Cerrar sesión"
               data-confirm-boton="Cerrar sesión"
               data-confirm-tipo="aviso">
                <i class="bi bi-box-arrow-right"></i> Salir
            </a>

        </div>
    </div>
</nav>
