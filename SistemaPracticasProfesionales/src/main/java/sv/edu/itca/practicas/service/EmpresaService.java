package sv.edu.itca.practicas.service;

import java.util.List;
import sv.edu.itca.practicas.dao.EmpresaDAO;
import sv.edu.itca.practicas.model.Empresa;

public class EmpresaService {

    private final EmpresaDAO empresaDAO;

    public EmpresaService() {

        empresaDAO = new EmpresaDAO();
    }

    public List<Empresa> listarTodas() {

        return empresaDAO.listarTodas();
    }

    public List<Empresa> listarActivas() {

        return empresaDAO.listarActivas();
    }

    public Empresa buscarPorId(int id) {

        return empresaDAO.buscarPorId(id);
    }

    public boolean guardar(Empresa empresa) {

        if (empresa == null) {
            return false;
        }

        if (empresa.getNombre() == null
                || empresa.getNombre().trim().isEmpty()) {

            return false;
        }

        if (empresa.getDireccion() == null
                || empresa.getDireccion().trim().isEmpty()) {

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

        if (empresa.getContacto() == null
                || empresa.getContacto().trim().isEmpty()) {

            return false;
        }

        if (empresa.getId() == 0) {

            return empresaDAO.insertar(empresa);

        } else {

            return empresaDAO.actualizar(empresa);
        }
    }

    public boolean eliminar(int id) {

        return empresaDAO.eliminar(id);
    }

    public boolean cambiarEstado(int id) {

        return empresaDAO.cambiarEstado(id);
    }
}