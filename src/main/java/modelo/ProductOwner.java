package modelo;

public class ProductOwner extends Participante {

    public ProductOwner() {
    }

    public ProductOwner(int id, String nombre, String apellido) {
        super(id, nombre, apellido);
    }

    @Override
    public String obtenerRol() {
        return "Product Owner";
    }
}
