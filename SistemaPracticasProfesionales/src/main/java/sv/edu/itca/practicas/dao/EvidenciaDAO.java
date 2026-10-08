package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.EvidenciaResumen;

public class EvidenciaDAO {

    private static final List<EvidenciaResumen> evidencias =
            new ArrayList<>();

    private static int siguienteId = 4;

    static {

        evidencias.add(
                new EvidenciaResumen(
                        1,
                        1,
                        "Andersson Cienfuegos",
                        "Informe semanal 1",
                        "Informe correspondiente a las actividades realizadas durante la primera semana.",
                        "INFORME",
                        "14/09/2026",
                        "PENDIENTE"
                )
        );

        evidencias.add(
                new EvidenciaResumen(
                        2,
                        1,
                        "Andersson Cienfuegos",
                        "Capturas del sistema",
                        "Capturas de pantalla de las funcionalidades desarrolladas.",
                        "CAPTURA",
                        "16/09/2026",
                        "APROBADA"
                )
        );

        evidencias.add(
                new EvidenciaResumen(
                        3,
                        2,
                        "Rodrigo Martínez",
                        "Informe semanal 1",
                        "Informe de actividades de la primera semana.",
                        "INFORME",
                        "15/09/2026",
                        "PENDIENTE"
                )
        );
    }

    public List<EvidenciaResumen> listarTodos() {
        return new ArrayList<>(evidencias);
    }

    public List<EvidenciaResumen> listarPorAlumno(int alumnoId) {

        List<EvidenciaResumen> resultado =
                new ArrayList<>();

        for (EvidenciaResumen evidencia : evidencias) {

            if (evidencia.getAlumnoId() == alumnoId) {
                resultado.add(evidencia);
            }
        }

        return resultado;
    }

    public EvidenciaResumen buscarPorId(int id) {

        for (EvidenciaResumen evidencia : evidencias) {

            if (evidencia.getId() == id) {
                return evidencia;
            }
        }

        return null;
    }

    public boolean insertar(EvidenciaResumen evidencia) {

        evidencia.setId(siguienteId++);
        evidencias.add(evidencia);

        return true;
    }

    public boolean eliminar(int id) {

        EvidenciaResumen evidencia =
                buscarPorId(id);

        if (evidencia == null) {
            return false;
        }

        return evidencias.remove(evidencia);
    }

    public boolean aprobar(int id) {

        EvidenciaResumen evidencia =
                buscarPorId(id);

        if (evidencia == null) {
            return false;
        }

        evidencia.setEstado("APROBADA");

        return true;
    }

    public boolean rechazar(int id) {

        EvidenciaResumen evidencia =
                buscarPorId(id);

        if (evidencia == null) {
            return false;
        }

        evidencia.setEstado("RECHAZADA");

        return true;
    }

    public int contarPendientes() {

        int cantidad = 0;

        for (EvidenciaResumen evidencia : evidencias) {

            if ("PENDIENTE".equalsIgnoreCase(
                    evidencia.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarAprobadas() {

        int cantidad = 0;

        for (EvidenciaResumen evidencia : evidencias) {

            if ("APROBADA".equalsIgnoreCase(
                    evidencia.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}