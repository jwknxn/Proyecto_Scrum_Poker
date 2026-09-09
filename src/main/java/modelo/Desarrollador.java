package modelo;

public class Desarrollador extends Participante {

    public Desarrollador() {
    }

    public Desarrollador(int id, String nombre, String apellido) {
        super(id, nombre, apellido);
    }

    @Override
    public String obtenerRol() {
        return "Desarrollador";
    }
}
