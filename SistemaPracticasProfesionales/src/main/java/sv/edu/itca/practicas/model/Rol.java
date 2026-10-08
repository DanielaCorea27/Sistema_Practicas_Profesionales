package sv.edu.itca.practicas.model;

import static sv.edu.itca.practicas.model.Rol.values;

/**
 * Roles del sistema. El id coincide con roles.id_rol en la base de datos.
 */
public enum Rol {

    ADMINISTRADOR(1),
    ALUMNO(2),
    MAESTRO(3),
    EMPRESA(4);

    private final int id;

    Rol(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Rol fromId(int id) {

        for (Rol rol : values()) {

            if (rol.id == id) {
                return rol;
            }
        }

        return null;
    }
}
