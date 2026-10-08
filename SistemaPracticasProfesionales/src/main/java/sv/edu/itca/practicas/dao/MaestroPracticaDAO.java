package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.PracticaResumen;

public class MaestroPracticaDAO {

    private static final List<PracticaResumen> practicas =
            new ArrayList<>();

    static {

        practicas.add(
                new PracticaResumen(
                        1,
                        "Andersson Cienfuegos",
                        "Empresa Tecnológica S.A. de C.V.",
                        "Ingeniería de Sistemas Informáticos",
                        "01/09/2026",
                        "30/11/2026",
                        "ACTIVA"
                )
        );

        practicas.add(
                new PracticaResumen(
                        2,
                        "Rodrigo Martínez",
                        "Soluciones Digitales S.A. de C.V.",
                        "Ingeniería de Sistemas Informáticos",
                        "01/09/2026",
                        "30/11/2026",
                        "ACTIVA"
                )
        );

        practicas.add(
                new PracticaResumen(
                        3,
                        "Yanci López",
                        "Innovaciones IT S.A. de C.V.",
                        "Ingeniería de Sistemas Informáticos",
                        "15/09/2026",
                        "15/12/2026",
                        "PENDIENTE"
                )
        );
    }

    public List<PracticaResumen> listarTodos() {
        return new ArrayList<>(practicas);
    }

    public PracticaResumen buscarPorId(int id) {

        for (PracticaResumen practica : practicas) {

            if (practica.getId() == id) {
                return practica;
            }
        }

        return null;
    }

    public int contarPracticas() {
        return practicas.size();
    }

    public int contarActivas() {

        int cantidad = 0;

        for (PracticaResumen practica : practicas) {

            if ("ACTIVA".equalsIgnoreCase(
                    practica.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}