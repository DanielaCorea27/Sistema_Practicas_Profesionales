/*
 * confirmar.js  -  Modal de confirmacion del Sistema de Pasantias ITCA.
 *
 * Reemplaza el cuadro nativo "localhost:8080 dice..." por un modal propio.
 * No depende de Bootstrap ni de otras librerias: sirve en TODAS las pantallas.
 *
 * USO (en cualquier JSP, antes de </body>):
 *   <script src="${pageContext.request.contextPath}/assets/confirmar.js"></script>
 *
 * Atributos que se pueden poner en <form>, <a> o <button type="submit">:
 *   data-confirm="¿Mensaje?"            (obligatorio: activa el modal)
 *   data-confirm-titulo="Titulo"        (opcional)
 *   data-confirm-boton="Eliminar"       (opcional, texto del boton principal)
 *   data-confirm-tipo="peligro"         (info | peligro | exito | aviso)
 *
 * AUTOMATICO (no hay que tocar las pantallas existentes):
 *   - onclick="return confirm('...')" / onsubmit="return confirm('...')"
 *     se convierten solos en este modal.
 *   - Todos los enlaces a /logout preguntan "¿Deseas cerrar tu sesión?".
 *
 * Tambien se puede llamar desde JavaScript:
 *   itcaConfirmar({mensaje:'...', tipo:'peligro'}, function () { ... });
 */
(function () {
    'use strict';

    if (window.itcaConfirmar) {
        return;
    }

    var TIPOS = {
        info:    { color: '#123b6d', icono: '?',      titulo: 'Confirmar' },
        peligro: { color: '#e03131', icono: '!',      titulo: 'Confirmar acción' },
        exito:   { color: '#2f9e44', icono: '\u2713', titulo: 'Confirmar' },
        aviso:   { color: '#f08c00', icono: '!',      titulo: 'Atención' }
    };

    var overlay, iconoEl, tituloEl, mensajeEl, btnOk, btnCancel;
    var accionPendiente = null;
    var focoPrevio = null;
    var overflowPrevio = '';

    // ------------------------------------------------------------------
    //  Estilos
    // ------------------------------------------------------------------
    function inyectarEstilos() {

        var css = ''
            + '.cf-overlay{position:fixed;top:0;left:0;right:0;bottom:0;z-index:20000;'
            + 'display:none;align-items:center;justify-content:center;padding:16px;'
            + 'background:rgba(11,37,69,.55);}'
            + '.cf-overlay.cf-show{display:flex;animation:cf-fade .15s ease-out;}'
            + '.cf-box{width:100%;max-width:420px;background:#fff;border-radius:20px;'
            + 'padding:28px 26px 22px;box-shadow:0 25px 70px rgba(0,0,0,.35);'
            + 'text-align:center;font-family:Arial,Helvetica,sans-serif;'
            + 'animation:cf-pop .18s ease-out;}'
            + '.cf-icon{width:64px;height:64px;border-radius:50%;margin:0 auto 14px;'
            + 'display:flex;align-items:center;justify-content:center;'
            + 'font-size:32px;font-weight:700;color:#fff;}'
            + '.cf-title{margin:0 0 8px;font-size:20px;font-weight:800;color:#123b6d;}'
            + '.cf-msg{margin:0 0 22px;color:#4a5568;font-size:15px;line-height:1.5;'
            + 'white-space:pre-line;}'
            + '.cf-actions{display:flex;gap:10px;justify-content:center;flex-wrap:wrap;}'
            + '.cf-btn{border:0;border-radius:12px;padding:11px 22px;font-size:15px;'
            + 'font-weight:700;cursor:pointer;min-width:120px;font-family:inherit;}'
            + '.cf-btn:focus{outline:3px solid rgba(29,111,165,.45);outline-offset:2px;}'
            + '.cf-cancel{background:#e9eef5;color:#123b6d;}'
            + '.cf-cancel:hover{background:#dbe4ef;}'
            + '.cf-ok{color:#fff;}'
            + '.cf-ok:hover{filter:brightness(.92);}'
            + '@keyframes cf-fade{from{opacity:0}to{opacity:1}}'
            + '@keyframes cf-pop{from{transform:scale(.92);opacity:0}'
            + 'to{transform:scale(1);opacity:1}}';

        var style = document.createElement('style');
        style.type = 'text/css';
        style.appendChild(document.createTextNode(css));
        document.head.appendChild(style);
    }

    // ------------------------------------------------------------------
    //  Construccion del modal (una sola vez)
    // ------------------------------------------------------------------
    function construir() {

        if (overlay) {
            return;
        }

        inyectarEstilos();

        overlay = document.createElement('div');
        overlay.className = 'cf-overlay';
        overlay.setAttribute('role', 'dialog');
        overlay.setAttribute('aria-modal', 'true');
        overlay.setAttribute('aria-labelledby', 'cf-titulo');

        var caja = document.createElement('div');
        caja.className = 'cf-box';

        iconoEl = document.createElement('div');
        iconoEl.className = 'cf-icon';

        tituloEl = document.createElement('h3');
        tituloEl.className = 'cf-title';
        tituloEl.id = 'cf-titulo';

        mensajeEl = document.createElement('p');
        mensajeEl.className = 'cf-msg';

        var acciones = document.createElement('div');
        acciones.className = 'cf-actions';

        btnCancel = document.createElement('button');
        btnCancel.type = 'button';
        btnCancel.className = 'cf-btn cf-cancel';
        btnCancel.textContent = 'Cancelar';

        btnOk = document.createElement('button');
        btnOk.type = 'button';
        btnOk.className = 'cf-btn cf-ok';

        acciones.appendChild(btnCancel);
        acciones.appendChild(btnOk);

        caja.appendChild(iconoEl);
        caja.appendChild(tituloEl);
        caja.appendChild(mensajeEl);
        caja.appendChild(acciones);
        overlay.appendChild(caja);
        document.body.appendChild(overlay);

        btnCancel.addEventListener('click', cerrar);

        overlay.addEventListener('mousedown', function (e) {
            if (e.target === overlay) {
                cerrar();
            }
        });

        btnOk.addEventListener('click', function () {

            var fn = accionPendiente;

            cerrar();

            if (fn) {
                fn();
            }
        });

        document.addEventListener('keydown', function (e) {

            if (!overlay.classList.contains('cf-show')) {
                return;
            }

            if (e.key === 'Escape' || e.keyCode === 27) {
                e.preventDefault();
                cerrar();
                return;
            }

            // Mantener el foco dentro del modal.
            if (e.key === 'Tab' || e.keyCode === 9) {

                if (e.shiftKey && document.activeElement === btnCancel) {
                    e.preventDefault();
                    btnOk.focus();

                } else if (!e.shiftKey && document.activeElement === btnOk) {
                    e.preventDefault();
                    btnCancel.focus();
                }
            }
        });
    }

    // ------------------------------------------------------------------
    //  Abrir / cerrar
    // ------------------------------------------------------------------
    function abrir(op, alConfirmar) {

        construir();

        var tipo = TIPOS[op.tipo] ? op.tipo : 'info';
        var def = TIPOS[tipo];

        iconoEl.textContent = def.icono;
        iconoEl.style.background = def.color;

        tituloEl.textContent = op.titulo || def.titulo;
        mensajeEl.textContent = op.mensaje || '¿Deseas continuar?';

        btnOk.textContent = op.boton || 'Aceptar';
        btnOk.style.background = def.color;

        accionPendiente = alConfirmar;
        focoPrevio = document.activeElement;

        overflowPrevio = document.body.style.overflow;
        document.body.style.overflow = 'hidden';

        overlay.classList.add('cf-show');

        // En acciones peligrosas el foco empieza en "Cancelar".
        (tipo === 'peligro' ? btnCancel : btnOk).focus();
    }

    function cerrar() {

        if (!overlay) {
            return;
        }

        overlay.classList.remove('cf-show');
        document.body.style.overflow = overflowPrevio;
        accionPendiente = null;

        if (focoPrevio && focoPrevio.focus) {
            try {
                focoPrevio.focus();
            } catch (e) { /* nada */ }
        }
    }

    // ------------------------------------------------------------------
    //  Lectura de atributos y envio de formularios
    // ------------------------------------------------------------------
    function leerOpciones(el) {

        return {
            mensaje: el.getAttribute('data-confirm'),
            titulo: el.getAttribute('data-confirm-titulo'),
            boton: el.getAttribute('data-confirm-boton'),
            tipo: el.getAttribute('data-confirm-tipo') || 'info'
        };
    }

    /** Envia el formulario sin volver a disparar el evento submit. */
    function enviar(form, boton) {

        if (boton && boton.name) {

            var oculto = document.createElement('input');
            oculto.type = 'hidden';
            oculto.name = boton.name;
            oculto.value = boton.value;
            form.appendChild(oculto);
        }

        HTMLFormElement.prototype.submit.call(form);
    }

    // Formularios con data-confirm.
    document.addEventListener('submit', function (e) {

        var f = e.target;

        if (!f || f.nodeType !== 1 || !f.hasAttribute('data-confirm')) {
            return;
        }

        e.preventDefault();

        var boton = e.submitter || null;

        abrir(leerOpciones(f), function () {
            enviar(f, boton);
        });

    }, true);

    // Enlaces y botones con data-confirm.
    document.addEventListener('click', function (e) {

        if (!e.target.closest) {
            return;
        }

        var el = e.target.closest('a[data-confirm], button[data-confirm]');

        if (!el) {
            return;
        }

        if (el.tagName === 'A') {

            var url = el.href;

            if (!url) {
                return;
            }

            e.preventDefault();

            abrir(leerOpciones(el), function () {
                window.location.href = url;
            });

            return;
        }

        // Boton de envio dentro de un formulario.
        var form = el.form;

        // Si el formulario ya tiene data-confirm, el se encarga.
        if (!form || form.hasAttribute('data-confirm') || el.type === 'button') {
            return;
        }

        e.preventDefault();

        // Respetar la validacion HTML5 (campos required) antes de preguntar.
        if (form.checkValidity && !form.checkValidity()) {
            form.reportValidity();
            return;
        }

        abrir(leerOpciones(el), function () {
            enviar(form, el);
        });

    }, true);

    // ------------------------------------------------------------------
    //  Conversion automatica de pantallas existentes
    // ------------------------------------------------------------------
    var PATRON_CONFIRM =
        /^\s*return\s+confirm\(\s*(['"])([\s\S]*)\1\s*\)\s*;?\s*$/;

    function convertir() {

        // 1) onclick / onsubmit = "return confirm('...')"
        var lista = document.querySelectorAll(
            '[onclick*="confirm("], [onsubmit*="confirm("]');

        for (var i = 0; i < lista.length; i++) {

            var el = lista[i];
            var attr = el.hasAttribute('onsubmit') ? 'onsubmit' : 'onclick';
            var m = PATRON_CONFIRM.exec(el.getAttribute(attr) || '');

            if (!m) {
                continue;
            }

            var mensaje = m[2].replace(/\\(['"])/g, '$1');

            el.setAttribute('data-confirm', mensaje);
            el.removeAttribute(attr);

            if (/elimin|borrar|quitar/i.test(mensaje)) {

                el.setAttribute('data-confirm-tipo', 'peligro');
                el.setAttribute('data-confirm-titulo', 'Eliminar');
                el.setAttribute('data-confirm-boton', 'Eliminar');

            } else if (/rechaz/i.test(mensaje)) {

                el.setAttribute('data-confirm-tipo', 'peligro');
                el.setAttribute('data-confirm-boton', 'Rechazar');
            }
        }

        // 2) Todos los enlaces de cerrar sesion.
        var enlaces = document.querySelectorAll('a[href]');

        for (var j = 0; j < enlaces.length; j++) {

            var a = enlaces[j];

            if (a.hasAttribute('data-confirm')) {
                continue;
            }

            if (/\/logout(\?|#|$)/.test(a.getAttribute('href'))) {

                a.setAttribute('data-confirm', '¿Deseas cerrar tu sesión?');
                a.setAttribute('data-confirm-titulo', 'Cerrar sesión');
                a.setAttribute('data-confirm-boton', 'Cerrar sesión');
                a.setAttribute('data-confirm-tipo', 'aviso');
            }
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', convertir);
    } else {
        convertir();
    }

    // API publica.
    window.itcaConfirmar = abrir;

})();
