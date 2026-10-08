package sv.edu.itca.practicas.service;
/**
 *
 * @author danie
 */

import java.util.List;

import sv.edu.itca.practicas.dao.EmpresaAdminDAO;
import sv.edu.itca.practicas.model.EmpresaAdmin;

public class EmpresaAdminService {

    private final EmpresaAdminDAO empresaDAO;

    public EmpresaAdminService() {
        empresaDAO = new EmpresaAdminDAO();
    }

    public List<EmpresaAdmin> listarTodos() {

        return empresaDAO.listarTodos();
    }

    public boolean guardar(EmpresaAdmin empresa) {

        if (empresa == null) {
            return false;
        }

        if (empresa.getNombre() == null
                || empresa.getNombre().trim().isEmpty()) {
            return false;
        }

        if (empresa.getContacto() == null
                || empresa.getContacto().trim().isEmpty()) {
            return false;
        }

        if (empresa.getTelefono() == null
                || empresa.getTelefono().trim().isEmpty()) {
            return false;
        }

        if (empresa.getCorreo() == null
                || empresa.getCorreo().trim().isEmpty()) {
            return false;
        }

        empresa.setNombre(
                empresa.getNombre().trim()
        );

        empresa.setContacto(
                empresa.getContacto().trim()
        );

        empresa.setTelefono(
                empresa.getTelefono().trim()
        );

        empresa.setCorreo(
                empresa.getCorreo().trim()
        );

        empresa.setEstado("ACTIVO");

        return empresaDAO.insertar(empresa);
    }

    public boolean cambiarEstado(int id) {

        return empresaDAO.cambiarEstado(id);
    }

    public int contarTodos() {

        return empresaDAO.contarTodos();
    }

    public int contarActivas() {

        return empresaDAO.contarActivas();
    }
}