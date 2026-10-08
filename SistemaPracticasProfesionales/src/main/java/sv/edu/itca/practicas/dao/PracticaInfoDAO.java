package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;

import sv.edu.itca.practicas.model.PracticaInfo;

public class PracticaInfoDAO {

    private static final List<PracticaInfo> practicas =
            new ArrayList<>();

    static {

        practicas.add(
                new PracticaInfo(
                        1,
                        1,
                        "Andersson Cienfuegos",
                        "Ingeniería de Sistemas",
                        640,
                        "Empresa Tecnológica",
                        "Desarrollo de Software",
                        "San Salvador, El Salvador",
                        "Maestro Supervisor",
                        "maestro@itca.edu.sv",
                        "2222-0000",
                        "01/09/2026",
                        "30/12/2026",
                        "EN PROCESO"
                )
        );
    }

    public PracticaInfo buscarPorAlumno(int alumnoId) {

        for (PracticaInfo practica : practicas) {

            if (practica.getAlumnoId() == alumnoId) {

                return practica;
            }
        }

        return null;
    }

    public List<PracticaInfo> listar() {

        return new ArrayList<>(practicas);
    }

    public boolean insertar(PracticaInfo practica) {

        if (practica == null) {

            return false;
        }

        practicas.add(practica);

        return true;
    }

    public boolean actualizar(PracticaInfo actualizada) {

        if (actualizada == null) {

            return false;
        }

        for (int i = 0; i < practicas.size(); i++) {

            if (practicas.get(i).getId()
                    == actualizada.getId()) {

                practicas.set(i, actualizada);

                return true;
            }
        }

        return false;
    }

}