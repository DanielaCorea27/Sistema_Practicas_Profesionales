package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.AlumnoAdmin;

public class AlumnoAdminDAO {

    private static final List<AlumnoAdmin> alumnos =
            new ArrayList<>();

    private static int siguienteId = 4;

    static {

        alumnos.add(
                new AlumnoAdmin(
                        1,
                        "Andersson Cienfuegos",
                        "20240001",
                        "Ingeniería en Desarrollo de Software",
                        "andersson",
                        "ACTIVO"
                )
        );

        alumnos.add(
                new AlumnoAdmin(
                        2,
                        "Rodrigo Martínez",
                        "20240002",
                        "Ingeniería en Desarrollo de Software",
                        "rodrigo",
                        "ACTIVO"
                )
        );

        alumnos.add(
                new AlumnoAdmin(
                        3,
                        "Yanci López",
                        "20240003",
                        "Ingeniería en Desarrollo de Software",
                        "yanci",
                        "ACTIVO"
                )
        );
    }

    public List<AlumnoAdmin> listarTodos() {

        return new ArrayList<>(alumnos);
    }

    public AlumnoAdmin buscarPorId(int id) {

        for (AlumnoAdmin alumno : alumnos) {

            if (alumno.getId() == id) {
                return alumno;
            }
        }

        return null;
    }

    public boolean insertar(AlumnoAdmin alumno) {

        alumno.setId(siguienteId++);

        alumnos.add(alumno);

        return true;
    }

    public boolean cambiarEstado(int id) {

        AlumnoAdmin alumno =
                buscarPorId(id);

        if (alumno == null) {
            return false;
        }

        if ("ACTIVO".equalsIgnoreCase(
                alumno.getEstado())) {

            alumno.setEstado("INACTIVO");

        } else {

            alumno.setEstado("ACTIVO");
        }

        return true;
    }

    public int contarTodos() {

        return alumnos.size();
    }

    public int contarActivos() {

        int cantidad = 0;

        for (AlumnoAdmin alumno : alumnos) {

            if ("ACTIVO".equalsIgnoreCase(
                    alumno.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}