package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.AlumnoResumen;

public class MaestroAlumnoDAO {

    private static final List<AlumnoResumen> alumnos =
            new ArrayList<>();

    static {

        alumnos.add(
                new AlumnoResumen(
                        1,
                        "20240001",
                        "Andersson Cienfuegos",
                        "Ingeniería de Sistemas Informáticos",
                        "Empresa Tecnológica S.A. de C.V.",
                        "ACTIVO"
                )
        );

        alumnos.add(
                new AlumnoResumen(
                        2,
                        "20240002",
                        "Rodrigo Martínez",
                        "Ingeniería de Sistemas Informáticos",
                        "Soluciones Digitales S.A. de C.V.",
                        "ACTIVO"
                )
        );

        alumnos.add(
                new AlumnoResumen(
                        3,
                        "20240003",
                        "Yanci López",
                        "Ingeniería de Sistemas Informáticos",
                        "Innovaciones IT S.A. de C.V.",
                        "PENDIENTE"
                )
        );
    }

    public List<AlumnoResumen> listarTodos() {
        return new ArrayList<>(alumnos);
    }

    public AlumnoResumen buscarPorId(int id) {

        for (AlumnoResumen alumno : alumnos) {

            if (alumno.getId() == id) {
                return alumno;
            }
        }

        return null;
    }

    public int contarAlumnos() {
        return alumnos.size();
    }

    public int contarActivos() {

        int cantidad = 0;

        for (AlumnoResumen alumno : alumnos) {

            if ("ACTIVO".equalsIgnoreCase(
                    alumno.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}