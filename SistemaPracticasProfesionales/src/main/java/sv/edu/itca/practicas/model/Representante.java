package sv.edu.itca.practicas.model;

import java.io.Serializable;

/**
 * Representante de una empresa (usuario con rol EMPRESA).
 * Guarda solo datos simples para poder vivir en la sesion.
 */
public class Representante implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;               // representantes_empresa.id_representante
    private int usuarioId;        // usuarios.id_usuario
    private int empresaId;        // empresas.id_empresa
    private String empresaNombre;
    private String cargo;

    public Representante() {
    }

    public Representante(int id, int usuarioId, int empresaId,
                         String empresaNombre, String cargo) {

        this.id = id;
        this.usuarioId = usuarioId;
        this.empresaId = empresaId;
        this.empresaNombre = empresaNombre;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNombre() {
        return empresaNombre;
    }

    public void setEmpresaNombre(String empresaNombre) {
        this.empresaNombre = empresaNombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
