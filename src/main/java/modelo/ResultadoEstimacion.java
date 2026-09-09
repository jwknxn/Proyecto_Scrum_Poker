package modelo;

public class ResultadoEstimacion {

    private int idHistoria;
    private String titulo;
    private int cantidadVotos;
    private String estimacionPromedio;

    public ResultadoEstimacion() {
    }

    public ResultadoEstimacion(int idHistoria, String titulo, int cantidadVotos, String estimacionPromedio) {
        this.idHistoria = idHistoria;
        this.titulo = titulo;
        this.cantidadVotos = cantidadVotos;
        this.estimacionPromedio = estimacionPromedio;
    }

    public int getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public String getEstimacionPromedio() {
        return estimacionPromedio;
    }

    public void setEstimacionPromedio(String estimacionPromedio) {
        this.estimacionPromedio = estimacionPromedio;
    }
}
