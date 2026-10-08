package sv.edu.itca.practicas.dao;

import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Carrera;

public class CarreraAdminDAO {

    private static final List<Carrera> carreras = new ArrayList<>();

    private static int siguienteId = 4;

    static {

        Carrera carrera1 = new Carrera();
        carrera1.setId(1);
        carrera1.setNombre("Ingeniería en Desarrollo de Software");
        carrera1.setHorasRequeridas(640);
        carreras.add(carrera1);

        Carrera carrera2 = new Carrera();
        carrera2.setId(2);
        carrera2.setNombre("Ingeniería en Sistemas");
        carrera2.setHorasRequeridas(640);
        carreras.add(carrera2);

        Carrera carrera3 = new Carrera();
        carrera3.setId(3);
        carrera3.setNombre("Técnico en Desarrollo de Software");
        carrera3.setHorasRequeridas(320);
        carreras.add(carrera3);
    }

    public List<Carrera> listarTodos() {
        return new ArrayList<>(carreras);
    }

    public Carrera buscarPorId(int id) {

        for (Carrera carrera : carreras) {

            if (carrera.getId() == id) {
                return carrera;
            }
        }

        return null;
    }

    public boolean insertar(Carrera carrera) {

        if (carrera == null) {
            return false;
        }

        carrera.setId(siguienteId++);
        carreras.add(carrera);

        return true;
    }

    public boolean eliminar(int id) {

        Carrera carrera = buscarPorId(id);

        if (carrera == null) {
            return false;
        }

        return carreras.remove(carrera);
    }

    public int contarTodos() {
        return carreras.size();
    }
}