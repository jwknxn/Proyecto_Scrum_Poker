package modelo;

public class PuntoEstimacion {

    private int id;
    private int valor;

    public PuntoEstimacion() {
    }

    public PuntoEstimacion(int id, int valor) {
        this.id = id;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
