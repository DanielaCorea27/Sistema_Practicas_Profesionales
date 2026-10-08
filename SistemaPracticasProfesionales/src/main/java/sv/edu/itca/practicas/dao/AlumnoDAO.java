package sv.edu.itca.practicas.dao;
/**
 *
 * @author danie
 */

import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Alumno;

public class AlumnoDAO {

    private static final List<Alumno> alumnos = new ArrayList<>();

    private static int siguienteId = 1;

    /*
     * Datos temporales para probar el sistema.
     *
     * IMPORTANTE:
     * Más adelante estos datos serán reemplazados
     * completamente por MySQL.
     */
    static {

        Alumno alumno1 = new Alumno();

        alumno1.setId(siguienteId++);
        alumno1.setCarnet("20240001");
        alumno1.setTelefono("7000-1001");

        alumnos.add(alumno1);


        Alumno alumno2 = new Alumno();

        alumno2.setId(siguienteId++);
        alumno2.setCarnet("20240002");
        alumno2.setTelefono("7000-1002");

        alumnos.add(alumno2);


        Alumno alumno3 = new Alumno();

        alumno3.setId(siguienteId++);
        alumno3.setCarnet("20240003");
        alumno3.setTelefono("7000-1003");

        alumnos.add(alumno3);
    }


    public List<Alumno> listarTodos() {

        return new ArrayList<>(alumnos);
    }


    public Alumno buscarPorId(int id) {

        for (Alumno alumno : alumnos) {

            if (alumno.getId() == id) {

                return alumno;
            }
        }

        return null;
    }


    public Alumno buscarPorCarnet(String carnet) {

        if (carnet == null) {

            return null;
        }

        for (Alumno alumno : alumnos) {

            if (carnet.equalsIgnoreCase(
                    alumno.getCarnet())) {

                return alumno;
            }
        }

        return null;
    }


    public boolean existeCarnet(
            String carnet,
            int idExcluir) {

        if (carnet == null) {

            return false;
        }

        for (Alumno alumno : alumnos) {

            if (alumno.getCarnet()
                    .equalsIgnoreCase(carnet)
                    && alumno.getId() != idExcluir) {

                return true;
            }
        }

        return false;
    }


    public boolean insertar(Alumno alumno) {

        if (alumno == null) {

            return false;
        }

        alumno.setId(siguienteId++);

        alumnos.add(alumno);

        return true;
    }


    public boolean actualizar(Alumno alumnoActualizado) {

        if (alumnoActualizado == null) {

            return false;
        }

        for (int i = 0;
                i < alumnos.size();
                i++) {

            Alumno alumno = alumnos.get(i);

            if (alumno.getId()
                    == alumnoActualizado.getId()) {

                alumnos.set(
                        i,
                        alumnoActualizado
                );

                return true;
            }
        }

        return false;
    }


    public boolean eliminar(int id) {

        Alumno alumno = buscarPorId(id);

        if (alumno != null) {

            alumnos.remove(alumno);

            return true;
        }

        return false;
    }
}