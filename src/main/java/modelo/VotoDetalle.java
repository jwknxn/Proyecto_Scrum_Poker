package modelo;

public class VotoDetalle {

    private int idVoto;
    private int idDesarrollador;
    private String desarrollador;
    private int idHistoria;
    private String historia;
    private int puntos;

    public VotoDetalle() {
    }

    public VotoDetalle(int idVoto, int idDesarrollador, String desarrollador,
                       int idHistoria, String historia, int puntos) {
        this.idVoto = idVoto;
        this.idDesarrollador = idDesarrollador;
        this.desarrollador = desarrollador;
        this.idHistoria = idHistoria;
        this.historia = historia;
        this.puntos = puntos;
    }

    public int getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(int idVoto) {
        this.idVoto = idVoto;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public int getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
