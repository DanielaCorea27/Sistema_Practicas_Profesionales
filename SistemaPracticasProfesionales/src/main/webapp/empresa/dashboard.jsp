<%-- 
    Document   : sss
    Created on : 7 oct 2026, 8:08:12 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Representante" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();
    Representante rep = (Representante) session.getAttribute("representante");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Dashboard | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
    <style>
        a.card-link { text-decoration: none; color: inherit; display: block; }
        a.card-link .stat-card { transition: transform .15s, box-shadow .15s; }
        a.card-link:hover .stat-card {
            transform: translateY(-3px);
            box-shadow: 0 14px 32px rgba(18, 59, 109, .18);
        }
    </style>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-1">Panel de la empresa</h2>
    <p class="text-muted mb-4">
        <%= Html.esc(rep != null ? rep.getEmpresaNombre() : "") %>
        <% if (rep != null && rep.getCargo() != null && !rep.getCargo().isEmpty()) { %>
            &middot; <%= Html.esc(rep.getCargo()) %>
        <% } %>
    </p>

    <div class="row g-4">

        <div class="col-12 col-md-6 col-xl-3">
            <a class="card-link" href="<%= ctx %>/empresa/oportunidades">
                <div class="card stat-card">
                    <div class="card-body d-flex align-items-center gap-3">
                        <div class="stat-icon" style="background:#1d6fa5;">
                            <i class="bi bi-briefcase"></i>
                        </div>
                        <div>
                            <div class="stat-number">${totalOportunidades}</div>
                            <div class="text-muted small">Oportunidades publicadas</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <a class="card-link" href="<%= ctx %>/empresa/postulantes?estado=PENDIENTE">
                <div class="card stat-card">
                    <div class="card-body d-flex align-items-center gap-3">
                        <div class="stat-icon" style="background:#f08c00;">
                            <i class="bi bi-inbox"></i>
                        </div>
                        <div>
                            <div class="stat-number">${postulacionesPendientes}</div>
                            <div class="text-muted small">Postulaciones pendientes</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <a class="card-link" href="<%= ctx %>/empresa/estudiantes">
                <div class="card stat-card">
                    <div class="card-body d-flex align-items-center gap-3">
                        <div class="stat-icon" style="background:#2f9e44;">
                            <i class="bi bi-mortarboard"></i>
                        </div>
                        <div>
                            <div class="stat-number">${estudiantesActivos}</div>
                            <div class="text-muted small">Estudiantes activos</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <a class="card-link" href="<%= ctx %>/empresa/evaluaciones">
                <div class="card stat-card">
                    <div class="card-body d-flex align-items-center gap-3">
                        <div class="stat-icon" style="background:#7048e8;">
                            <i class="bi bi-clipboard-check"></i>
                        </div>
                        <div>
                            <div class="stat-number">${evaluacionesPendientes}</div>
                            <div class="text-muted small">Evaluaciones pendientes</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>

    </div>

</div>

<jsp:include page="_scripts.jsp"/>
</body>
</html>
