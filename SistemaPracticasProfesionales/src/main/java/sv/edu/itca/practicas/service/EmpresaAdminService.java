package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.EmpresaAdminDAO;
import sv.edu.itca.practicas.model.EmpresaAdmin;
import sv.edu.itca.practicas.util.ReglaNegocioException;

public class EmpresaAdminService {

    private static final String PATRON_CORREO =
            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final EmpresaAdminDAO empresaDAO;

    public EmpresaAdminService() {
        empresaDAO = new EmpresaAdminDAO();
    }

    public List<EmpresaAdmin> listarTodos() {
        return empresaDAO.listarTodos();
    }

    public EmpresaAdmin buscarPorId(int id) {
        return empresaDAO.buscarPorId(id);
    }

    public void guardar(EmpresaAdmin empresa) throws ReglaNegocioException {

        if (empresa == null) {
            throw new ReglaNegocioException("Datos de la empresa no válidos.");
        }

        String nombre = limpiar(empresa.getNombre());
        String descripcion = limpiar(empresa.getDescripcion());
        String direccion = limpiar(empresa.getDireccion());
        String telefono = limpiar(empresa.getTelefono());
        String correo = limpiar(empresa.getCorreo()).toLowerCase();
        String sitioWeb = limpiar(empresa.getSitioWeb());
        String contacto = limpiar(empresa.getContacto());

        if (nombre.isEmpty() || nombre.length() > 150) {
            throw new ReglaNegocioException("El nombre es obligatorio (máximo 150 caracteres).");
        }

        if (contacto.isEmpty() || contacto.length() > 150) {
            throw new ReglaNegocioException("La persona de contacto es obligatoria (máximo 150 caracteres).");
        }

        if (telefono.isEmpty() || telefono.length() > 20) {
            throw new ReglaNegocioException("El teléfono es obligatorio (máximo 20 caracteres).");
        }

        if (!correo.matches(PATRON_CORREO) || correo.length() > 120) {
            throw new ReglaNegocioException("El correo no es válido.");
        }

        if (direccion.length() > 200) {
            throw new ReglaNegocioException("La dirección admite máximo 200 caracteres.");
        }

        if (!sitioWeb.isEmpty()) {
            if (!sitioWeb.matches("^https?://.+")) {
                sitioWeb = "https://" + sitioWeb;
            }
            if (sitioWeb.length() > 150) {
                throw new ReglaNegocioException("El sitio web admite máximo 150 caracteres.");
            }
        }

        if (empresaDAO.existeNombre(nombre)) {
            throw new ReglaNegocioException("Ya existe una empresa con ese nombre.");
        }

        empresa.setNombre(nombre);
        empresa.setDescripcion(descripcion.isEmpty() ? null : descripcion);
        empresa.setDireccion(direccion.isEmpty() ? null : direccion);
        empresa.setTelefono(telefono);
        empresa.setCorreo(correo);
        empresa.setSitioWeb(sitioWeb.isEmpty() ? null : sitioWeb);
        empresa.setContacto(contacto);

        if (!empresaDAO.insertar(empresa)) {
            throw new ReglaNegocioException("No se pudo guardar la empresa. Intenta de nuevo.");
        }
    }

    public void cambiarEstado(int id) throws ReglaNegocioException {

        if (!empresaDAO.cambiarEstado(id)) {
            throw new ReglaNegocioException("No se encontró la empresa.");
        }
    }

    public int contarTodos() {
        return empresaDAO.contarTodos();
    }

    public int contarActivas() {
        return empresaDAO.contarActivas();
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
