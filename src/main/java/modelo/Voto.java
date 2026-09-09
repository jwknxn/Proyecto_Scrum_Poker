package modelo;

public class Voto {

    private int id;
    private int idDesarrollador;
    private int idHistoriaUsuario;
    private int idPunto;

    public Voto() {
    }

    public Voto(int id, int idDesarrollador, int idHistoriaUsuario, int idPunto) {
        this.id = id;
        this.idDesarrollador = idDesarrollador;
        this.idHistoriaUsuario = idHistoriaUsuario;
        this.idPunto = idPunto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdHistoriaUsuario() {
        return idHistoriaUsuario;
    }

    public void setIdHistoriaUsuario(int idHistoriaUsuario) {
        this.idHistoriaUsuario = idHistoriaUsuario;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }
}
