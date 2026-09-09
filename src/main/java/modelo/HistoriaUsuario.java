package modelo;

public class HistoriaUsuario {

    private int id;
    private String titulo;
    private String descripcion;
    private int idPO;

    public HistoriaUsuario() {
    }

    public HistoriaUsuario(int id, String titulo, String descripcion, int idPO) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idPO = idPO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdPO() {
        return idPO;
    }

    public void setIdPO(int idPO) {
        this.idPO = idPO;
    }

    @Override
    public String toString() {
        return id + " - " + titulo;
    }
}
