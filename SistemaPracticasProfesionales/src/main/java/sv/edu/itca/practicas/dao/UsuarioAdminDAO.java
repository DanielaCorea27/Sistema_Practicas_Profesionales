package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.UsuarioAdmin;

public class UsuarioAdminDAO {

    private static final List<UsuarioAdmin> usuarios =
            new ArrayList<>();

    private static int siguienteId = 4;

    static {

        usuarios.add(
                new UsuarioAdmin(
                        1,
                        "Andersson Cienfuegos",
                        "andersson",
                        "ALUMNO",
                        "ACTIVO"
                )
        );

        usuarios.add(
                new UsuarioAdmin(
                        2,
                        "Rodrigo Martínez",
                        "rodrigo",
                        "ALUMNO",
                        "ACTIVO"
                )
        );

        usuarios.add(
                new UsuarioAdmin(
                        3,
                        "Maestro Principal",
                        "maestro",
                        "MAESTRO",
                        "ACTIVO"
                )
        );
    }

    public List<UsuarioAdmin> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public UsuarioAdmin buscarPorId(int id) {

        for (UsuarioAdmin usuario : usuarios) {

            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    public boolean insertar(UsuarioAdmin usuario) {

        usuario.setId(siguienteId++);

        usuarios.add(usuario);

        return true;
    }

    public boolean cambiarEstado(int id) {

        UsuarioAdmin usuario =
                buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        if ("ACTIVO".equalsIgnoreCase(
                usuario.getEstado())) {

            usuario.setEstado("INACTIVO");

        } else {

            usuario.setEstado("ACTIVO");
        }

        return true;
    }

    public int contarTodos() {
        return usuarios.size();
    }

    public int contarActivos() {

        int cantidad = 0;

        for (UsuarioAdmin usuario : usuarios) {

            if ("ACTIVO".equalsIgnoreCase(
                    usuario.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}