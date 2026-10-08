package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.MaestroAdmin;

public class MaestroAdminDAO {

    private static final List<MaestroAdmin> maestros =
            new ArrayList<>();

    private static int siguienteId = 4;

    static {

        maestros.add(
                new MaestroAdmin(
                        1,
                        "Maestro Principal",
                        "maestro",
                        "Desarrollo de Software",
                        "ACTIVO"
                )
        );

        maestros.add(
                new MaestroAdmin(
                        2,
                        "Carlos Hernández",
                        "carlos",
                        "Bases de Datos",
                        "ACTIVO"
                )
        );

        maestros.add(
                new MaestroAdmin(
                        3,
                        "María López",
                        "maria",
                        "Programación",
                        "ACTIVO"
                )
        );
    }

    public List<MaestroAdmin> listarTodos() {

        return new ArrayList<>(maestros);
    }

    public MaestroAdmin buscarPorId(int id) {

        for (MaestroAdmin maestro : maestros) {

            if (maestro.getId() == id) {
                return maestro;
            }
        }

        return null;
    }

    public boolean insertar(MaestroAdmin maestro) {

        maestro.setId(siguienteId++);

        maestros.add(maestro);

        return true;
    }

    public boolean cambiarEstado(int id) {

        MaestroAdmin maestro =
                buscarPorId(id);

        if (maestro == null) {
            return false;
        }

        if ("ACTIVO".equalsIgnoreCase(
                maestro.getEstado())) {

            maestro.setEstado("INACTIVO");

        } else {

            maestro.setEstado("ACTIVO");
        }

        return true;
    }

    public int contarTodos() {

        return maestros.size();
    }

    public int contarActivos() {

        int cantidad = 0;

        for (MaestroAdmin maestro : maestros) {

            if ("ACTIVO".equalsIgnoreCase(
                    maestro.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}