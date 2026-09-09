package controlador;

import modelo.ScrumPokerModelo;
import vista.HistoriasUsuario;
import vista.Menu;
import vista.Participantes;
import vista.ScrumPoker;

public class MenuControlador {

    private final Menu vista;
    private final ScrumPokerModelo modelo;

    public MenuControlador(Menu vista, ScrumPokerModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.getBtnParticipantes().addActionListener(e -> abrirParticipantes());
        vista.getBtnHistorias().addActionListener(e -> abrirHistorias());
        vista.getBtnScrumPoker().addActionListener(e -> abrirScrumPoker());
        vista.getBtnSalir().addActionListener(e -> System.exit(0));
    }

    private void abrirParticipantes() {
        Participantes ventana = new Participantes();
        new ParticipanteControlador(ventana, modelo);
        mostrarVentana(ventana);
    }

    private void abrirHistorias() {
        HistoriasUsuario ventana = new HistoriasUsuario();
        new HistoriaUsuarioControlador(ventana, modelo);
        mostrarVentana(ventana);
    }

    private void abrirScrumPoker() {
        ScrumPoker ventana = new ScrumPoker();
        new ScrumPokerControlador(ventana, modelo);
        mostrarVentana(ventana);
    }

    private void mostrarVentana(javax.swing.JFrame ventana) {
        ventana.setLocationRelativeTo(vista);
        ventana.setVisible(true);
    }
}
