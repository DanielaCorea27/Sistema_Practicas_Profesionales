<%-- 
    Document   : sss
    Created on : 7 oct 2026, 8:08:12 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="sv.edu.itca.practicas.model.CarreraOpcion" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%!
    /** Valor reenviado al formulario tras un error (nunca incluye contrasenas). */
    private static String val(Map<String, String> m, String clave) {

        if (m == null) {
            return "";
        }

        String v = m.get(clave);

        return v == null ? "" : Html.esc(v);
    }
%>
<%
    String ctx = request.getContextPath();

    String tipo = (String) request.getAttribute("tipo");
    if (tipo == null) {
        tipo = "estudiante";
    }

    @SuppressWarnings("unchecked")
    Map<String, String> v = (Map<String, String>) request.getAttribute("valores");

    @SuppressWarnings("unchecked")
    List<CarreraOpcion> carreras = (List<CarreraOpcion>) request.getAttribute("carreras");

    String error = (String) request.getAttribute("error");

    String carreraElegida = v == null ? "" : v.get("carreraId");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Crear cuenta | Prácticas ITCA-FEPADE</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

    <style>
        body {
            min-height: 100vh; margin: 0;
            background: linear-gradient(135deg, #0b2545 0%, #123b6d 50%, #1d6fa5 100%);
            font-family: Arial, sans-serif;
        }

        .wrapper { padding: 40px 16px; }

        .reg-card {
            max-width: 920px; margin: 0 auto; border: 0;
            border-radius: 24px; overflow: hidden;
            box-shadow: 0 25px 70px rgba(0,0,0,.30);
        }

        .reg-head {
            background: linear-gradient(145deg, #123b6d, #0b2545);
            color: #fff; padding: 30px 40px;
        }

        .reg-body { background: #fff; padding: 34px 40px; }

        .tipo-btn {
            border: 2px solid #d5dfec; background: #fff; color: #123b6d;
            border-radius: 14px; padding: 12px 18px; font-weight: 700; width: 100%;
        }

        .tipo-btn.activo {
            background: #123b6d; border-color: #123b6d; color: #fff;
        }

        .form-control, .form-select { border-radius: 12px; padding: 11px 14px; }

        .seccion { color: #123b6d; font-weight: 800; }

        .btn-reg {
            padding: 13px; border-radius: 12px; background: #123b6d;
            border: none; font-weight: 700; color: #fff;
        }

        .btn-reg:hover { background: #0b2545; color: #fff; }

        @media (max-width: 768px) {
            .reg-head, .reg-body { padding: 24px; }
        }
    </style>
</head>
<body>

<div class="wrapper">
<div class="card reg-card">

    <div class="reg-head">
        <h2 class="fw-bold mb-1"><i class="bi bi-person-plus"></i> Crear cuenta</h2>
        <div style="opacity:.85;">Sistema de Pasantías ITCA-FEPADE</div>
    </div>

    <div class="reg-body">

        <div class="row g-3 mb-4">
            <div class="col-6">
                <button type="button" class="tipo-btn <%= "estudiante".equals(tipo) ? "activo" : "" %>"
                        id="btn-estudiante" onclick="mostrar('estudiante')">
                    <i class="bi bi-mortarboard"></i> Soy estudiante
                </button>
            </div>
            <div class="col-6">
                <button type="button" class="tipo-btn <%= "empresa".equals(tipo) ? "activo" : "" %>"
                        id="btn-empresa" onclick="mostrar('empresa')">
                    <i class="bi bi-building"></i> Soy una empresa
                </button>
            </div>
        </div>

        <% if (error != null) { %>
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle"></i> <%= Html.esc(error) %>
            </div>
        <% } %>

        <%-- ================= ESTUDIANTE ================= --%>
        <form id="form-estudiante" action="<%= ctx %>/registro" method="post"
              class="<%= "estudiante".equals(tipo) ? "" : "d-none" %>"
              data-confirm="Se creará tu cuenta de estudiante."
              data-confirm-titulo="¿Crear cuenta?"
              data-confirm-boton="Crear cuenta"
              data-confirm-tipo="exito">

            <input type="hidden" name="tipo" value="estudiante">

            <div class="row g-3">

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Nombre *</label>
                    <input type="text" name="nombre" class="form-control" maxlength="80" required
                           value="<%= "estudiante".equals(tipo) ? val(v, "nombre") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Apellido *</label>
                    <input type="text" name="apellido" class="form-control" maxlength="80" required
                           value="<%= "estudiante".equals(tipo) ? val(v, "apellido") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Correo electrónico *</label>
                    <input type="email" name="correo" class="form-control" maxlength="120" required
                           placeholder="correo@itca.edu.sv"
                           value="<%= "estudiante".equals(tipo) ? val(v, "correo") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Teléfono</label>
                    <input type="text" name="telefono" class="form-control" maxlength="20"
                           value="<%= "estudiante".equals(tipo) ? val(v, "telefono") : "" %>">
                </div>

                <div class="col-md-4">
                    <label class="form-label fw-semibold">Carnet *</label>
                    <input type="text" name="carnet" class="form-control" maxlength="20" required
                           value="<%= "estudiante".equals(tipo) ? val(v, "carnet") : "" %>">
                </div>

                <div class="col-md-8">
                    <label class="form-label fw-semibold">Carrera *</label>
                    <select name="carreraId" class="form-select" required>
                        <option value="">Selecciona tu carrera...</option>
                        <% if (carreras != null) {
                               for (CarreraOpcion c : carreras) { %>
                            <option value="<%= c.getId() %>"
                                <%= "estudiante".equals(tipo)
                                        && String.valueOf(c.getId()).equals(carreraElegida)
                                        ? "selected" : "" %>>
                                <%= Html.esc(c.getNombre()) %>
                                (<%= Html.esc(c.getTipo()) %> · <%= c.getHorasRequeridas() %> h)
                            </option>
                        <%     }
                           } %>
                    </select>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Contraseña *</label>
                    <input type="password" name="password" class="form-control"
                           minlength="6" maxlength="64" required autocomplete="new-password">
                    <div class="form-text">Mínimo 6 caracteres.</div>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Confirmar contraseña *</label>
                    <input type="password" name="password2" class="form-control"
                           minlength="6" maxlength="64" required autocomplete="new-password">
                </div>

            </div>

            <button type="submit" class="btn btn-reg w-100 mt-4">
                <i class="bi bi-person-plus"></i> Crear cuenta de estudiante
            </button>
        </form>

        <%-- ================= EMPRESA ================= --%>
        <form id="form-empresa" action="<%= ctx %>/registro" method="post"
              class="<%= "empresa".equals(tipo) ? "" : "d-none" %>"
              data-confirm="Se creará la empresa y la cuenta de su representante."
              data-confirm-titulo="¿Crear cuenta?"
              data-confirm-boton="Crear cuenta"
              data-confirm-tipo="exito">

            <input type="hidden" name="tipo" value="empresa">

            <h5 class="seccion mb-3"><i class="bi bi-building"></i> Datos de la empresa</h5>

            <div class="row g-3 mb-4">

                <div class="col-12">
                    <label class="form-label fw-semibold">Nombre de la empresa *</label>
                    <input type="text" name="empresaNombre" class="form-control" maxlength="150" required
                           value="<%= "empresa".equals(tipo) ? val(v, "empresaNombre") : "" %>">
                </div>

                <div class="col-12">
                    <label class="form-label fw-semibold">Descripción</label>
                    <textarea name="descripcion" class="form-control" rows="3"
                              maxlength="1000"><%= "empresa".equals(tipo) ? val(v, "descripcion") : "" %></textarea>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Dirección</label>
                    <input type="text" name="direccion" class="form-control" maxlength="200"
                           value="<%= "empresa".equals(tipo) ? val(v, "direccion") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Sitio web</label>
                    <input type="text" name="sitioWeb" class="form-control" maxlength="150"
                           value="<%= "empresa".equals(tipo) ? val(v, "sitioWeb") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Teléfono de la empresa</label>
                    <input type="text" name="empresaTelefono" class="form-control" maxlength="20"
                           value="<%= "empresa".equals(tipo) ? val(v, "empresaTelefono") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Correo de la empresa</label>
                    <input type="email" name="empresaCorreo" class="form-control" maxlength="120"
                           value="<%= "empresa".equals(tipo) ? val(v, "empresaCorreo") : "" %>">
                </div>

            </div>

            <h5 class="seccion mb-3"><i class="bi bi-person-badge"></i> Representante (tu usuario de acceso)</h5>

            <div class="row g-3">

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Nombre *</label>
                    <input type="text" name="nombre" class="form-control" maxlength="80" required
                           value="<%= "empresa".equals(tipo) ? val(v, "nombre") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Apellido *</label>
                    <input type="text" name="apellido" class="form-control" maxlength="80" required
                           value="<%= "empresa".equals(tipo) ? val(v, "apellido") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Correo electrónico *</label>
                    <input type="email" name="correo" class="form-control" maxlength="120" required
                           value="<%= "empresa".equals(tipo) ? val(v, "correo") : "" %>">
                    <div class="form-text">Con este correo iniciarás sesión.</div>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Cargo</label>
                    <input type="text" name="cargo" class="form-control" maxlength="100"
                           value="<%= "empresa".equals(tipo) ? val(v, "cargo") : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Contraseña *</label>
                    <input type="password" name="password" class="form-control"
                           minlength="6" maxlength="64" required autocomplete="new-password">
                    <div class="form-text">Mínimo 6 caracteres.</div>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-semibold">Confirmar contraseña *</label>
                    <input type="password" name="password2" class="form-control"
                           minlength="6" maxlength="64" required autocomplete="new-password">
                </div>

            </div>

            <button type="submit" class="btn btn-reg w-100 mt-4">
                <i class="bi bi-building-add"></i> Crear cuenta de empresa
            </button>
        </form>

        <div class="text-center mt-4">
            <span class="text-muted">¿Ya tienes cuenta?</span>
            <a href="<%= ctx %>/login.jsp" class="fw-semibold text-decoration-none">Iniciar sesión</a>
            &middot;
            <a href="<%= ctx %>/index.jsp" class="text-decoration-none">Inicio</a>
        </div>

    </div>
</div>
</div>

<script>
    // Cambia entre el formulario de estudiante y el de empresa.
    function mostrar(tipo) {

        var tipos = ['estudiante', 'empresa'];

        for (var i = 0; i < tipos.length; i++) {

            var t = tipos[i];

            document.getElementById('form-' + t)
                    .classList.toggle('d-none', t !== tipo);

            document.getElementById('btn-' + t)
                    .classList.toggle('activo', t === tipo);
        }
    }
</script>
<script src="<%= ctx %>/assets/confirmar.js"></script>
</body>
</html>
