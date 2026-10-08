package sv.edu.itca.practicas.service;

import java.util.List;
import sv.edu.itca.practicas.dao.AlumnoDAO;
import sv.edu.itca.practicas.model.Alumno;

public class AlumnoService {

    private final AlumnoDAO alumnoDAO;


    public AlumnoService() {

        alumnoDAO = new AlumnoDAO();
    }


    public List<Alumno> listarTodos() {

        return alumnoDAO.listarTodos();
    }


    public Alumno buscarPorId(int id) {

        return alumnoDAO.buscarPorId(id);
    }


    public Alumno buscarPorCarnet(
            String carnet) {

        return alumnoDAO.buscarPorCarnet(carnet);
    }


    public boolean guardar(
            Alumno alumno) {

        if (alumno == null) {

            return false;
        }


        if (alumno.getCarnet() == null
                || alumno.getCarnet()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (alumno.getTelefono() == null
                || alumno.getTelefono()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        String carnet =
                alumno.getCarnet()
                        .trim();


        if (alumnoDAO.existeCarnet(
                carnet,
                alumno.getId())) {

            return false;
        }


        alumno.setCarnet(carnet);

        alumno.setTelefono(
                alumno.getTelefono()
                        .trim()
        );


        if (alumno.getId() == 0) {

            return alumnoDAO.insertar(
                    alumno
            );

        } else {

            return alumnoDAO.actualizar(
                    alumno
            );
        }
    }


    public boolean eliminar(int id) {

        return alumnoDAO.eliminar(id);
    }
}