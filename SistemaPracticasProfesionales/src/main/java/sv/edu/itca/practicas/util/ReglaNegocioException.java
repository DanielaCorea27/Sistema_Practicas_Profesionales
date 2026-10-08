package sv.edu.itca.practicas.util;

/**
 * Error esperado por una regla del negocio (no es un fallo tecnico).
 * El mensaje es apto para mostrarse al usuario.
 */
public class ReglaNegocioException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
