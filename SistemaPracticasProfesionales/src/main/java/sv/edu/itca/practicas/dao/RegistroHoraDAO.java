package sv.edu.itca.practicas.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.RegistroHora;

public class RegistroHoraDAO {

    private static final List<RegistroHora> registros =
            new ArrayList<>();

    private static int siguienteId = 1;

    // =====================================================
    // DATOS DE PRUEBA
    // =====================================================

    static {

        registros.add(
                new RegistroHora(
                        siguienteId++,
                        1,
                        LocalDate.of(2026, 9, 14),
                        8,
                        "Inducción a la empresa y reconocimiento del área de trabajo.",
                        "PENDIENTE",
                        ""
                )
        );

        registros.add(
                new RegistroHora(
                        siguienteId++,
                        1,
                        LocalDate.of(2026, 9, 15),
                        8,
                        "Análisis de requerimientos del sistema.",
                        "APROBADO",
                        "Actividad realizada correctamente."
                )
        );

        registros.add(
                new RegistroHora(
                        siguienteId++,
                        1,
                        LocalDate.of(2026, 9, 16),
                        8,
                        "Diseño de interfaces del sistema.",
                        "APROBADO",
                        ""
                )
        );

        registros.add(
                new RegistroHora(
                        siguienteId++,
                        1,
                        LocalDate.of(2026, 9, 17),
                        7,
                        "Desarrollo de módulo de usuarios.",
                        "PENDIENTE",
                        ""
                )
        );
    }

    // =====================================================
    // LISTAR POR ALUMNO
    // =====================================================

    public List<RegistroHora> listarPorAlumno(
            int alumnoId) {

        List<RegistroHora> resultado =
                new ArrayList<>();

        for (RegistroHora registro : registros) {

            if (registro.getAlumnoId() == alumnoId) {

                resultado.add(registro);
            }
        }

        return resultado;
    }

    // =====================================================
    // LISTAR TODOS
    // =====================================================

    public List<RegistroHora> listarTodos() {

        return new ArrayList<>(
                registros
        );
    }

    // =====================================================
    // BUSCAR POR ID
    // =====================================================

    public RegistroHora buscarPorId(
            int id) {

        for (RegistroHora registro : registros) {

            if (registro.getId() == id) {

                return registro;
            }
        }

        return null;
    }

    // =====================================================
    // INSERTAR
    // =====================================================

    public boolean insertar(
            RegistroHora registro) {

        if (registro == null) {

            return false;
        }

        registro.setId(
                siguienteId++
        );

        registros.add(
                registro
        );

        return true;
    }

    // =====================================================
    // ACTUALIZAR
    // =====================================================

    public boolean actualizar(
            RegistroHora actualizado) {

        if (actualizado == null) {

            return false;
        }

        for (int i = 0;
                i < registros.size();
                i++) {

            RegistroHora registro =
                    registros.get(i);

            if (registro.getId()
                    == actualizado.getId()) {

                registros.set(
                        i,
                        actualizado
                );

                return true;
            }
        }

        return false;
    }

    // =====================================================
    // ELIMINAR
    // =====================================================

    public boolean eliminar(
            int id) {

        RegistroHora registro =
                buscarPorId(id);

        if (registro != null) {

            registros.remove(
                    registro
            );

            return true;
        }

        return false;
    }

    // =====================================================
    // HORAS DE UN ALUMNO
    // =====================================================

    public double obtenerTotalHoras(
            int alumnoId) {

        double total = 0;

        for (RegistroHora registro : registros) {

            if (registro.getAlumnoId()
                    == alumnoId) {

                total += registro.getHoras();
            }
        }

        return total;
    }

    public double obtenerHorasAprobadas(
            int alumnoId) {

        double total = 0;

        for (RegistroHora registro : registros) {

            if (registro.getAlumnoId()
                    == alumnoId
                    && "APROBADO".equals(
                            registro.getEstado())) {

                total += registro.getHoras();
            }
        }

        return total;
    }

    // =====================================================
    // ESTADÍSTICAS
    // =====================================================

    public int contarRegistros() {

        return registros.size();
    }

    public int contarPendientes() {

        int total = 0;

        for (RegistroHora registro : registros) {

            if ("PENDIENTE".equals(
                    registro.getEstado())) {

                total++;
            }
        }

        return total;
    }

    public int contarAprobados() {

        int total = 0;

        for (RegistroHora registro : registros) {

            if ("APROBADO".equals(
                    registro.getEstado())) {

                total++;
            }
        }

        return total;
    }

    public int contarRechazados() {

        int total = 0;

        for (RegistroHora registro : registros) {

            if ("RECHAZADO".equals(
                    registro.getEstado())) {

                total++;
            }
        }

        return total;
    }

    // =====================================================
    // HORAS GENERALES DEL SISTEMA
    // =====================================================

    public double obtenerHorasTotalesSistema() {

        double total = 0;

        for (RegistroHora registro : registros) {

            total += registro.getHoras();
        }

        return total;
    }

    public double obtenerHorasAprobadasSistema() {

        double total = 0;

        for (RegistroHora registro : registros) {

            if ("APROBADO".equals(
                    registro.getEstado())) {

                total += registro.getHoras();
            }
        }

        return total;
    }

    public double obtenerHorasPendientesSistema() {

        double total = 0;

        for (RegistroHora registro : registros) {

            if ("PENDIENTE".equals(
                    registro.getEstado())) {

                total += registro.getHoras();
            }
        }

        return total;
    }

    // =====================================================
    // APROBAR
    // =====================================================

    public boolean aprobar(
            int id,
            String observacion) {

        RegistroHora registro =
                buscarPorId(id);

        if (registro == null) {

            return false;
        }

        if (!"PENDIENTE".equals(
                registro.getEstado())) {

            return false;
        }

        registro.setEstado(
                "APROBADO"
        );

        if (observacion == null) {

            registro.setObservacion("");

        } else {

            registro.setObservacion(
                    observacion.trim()
            );
        }

        return true;
    }

    // =====================================================
    // RECHAZAR
    // =====================================================

    public boolean rechazar(
            int id,
            String observacion) {

        RegistroHora registro =
                buscarPorId(id);

        if (registro == null) {

            return false;
        }

        if (!"PENDIENTE".equals(
                registro.getEstado())) {

            return false;
        }

        registro.setEstado(
                "RECHAZADO"
        );

        if (observacion == null
                || observacion
                        .trim()
                        .isEmpty()) {

            registro.setObservacion(
                    "Registro rechazado por el maestro."
            );

        } else {

            registro.setObservacion(
                    observacion.trim()
            );
        }

        return true;
    }

    // =====================================================
    // OBTENER REGISTROS PENDIENTES
    // =====================================================

    public int obtenerRegistrosPendientes() {

        int cantidad = 0;

        for (RegistroHora registro : registros) {

            if ("PENDIENTE".equalsIgnoreCase(
                    registro.getEstado())) {

                cantidad++;
            }
        }

        return cantidad;
    }
}