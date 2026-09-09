package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Desarrollador;
import modelo.HistoriaUsuario;
import modelo.PuntoEstimacion;
import modelo.ResultadoEstimacion;
import modelo.ScrumPokerModelo;
import modelo.Voto;
import modelo.VotoDetalle;
import vista.ScrumPoker;

public class ScrumPokerControlador {

    private final ScrumPoker vista;
    private final ScrumPokerModelo modelo;

    public ScrumPokerControlador(ScrumPoker vista, ScrumPokerModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        iniciarEventos();
        cargarDatos();
    }

    private void iniciarEventos() {
        vista.getBtnVotar().addActionListener(e -> registrarVoto());
        vista.getBtnConsultar().addActionListener(e -> consultarVotos());
        vista.getBtnCalcular().addActionListener(e -> calcularEstimacion());
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    private void cargarDatos() {
        try {
            cargarHistorias();
            cargarDesarrolladores();
            cargarPuntos();
        } catch (Exception e) {
            mostrarError("Error al cargar datos:\n" + e.getMessage());
        }
    }

    private void cargarHistorias() throws Exception {
        vista.getCmbHistoria().removeAllItems();

        for (HistoriaUsuario historia : modelo.consultarHistorias()) {
            vista.getCmbHistoria().addItem(historia);
        }
    }

    private void cargarDesarrolladores() throws Exception {
        vista.getCmbDesarrollador().removeAllItems();

        for (Desarrollador desarrollador : modelo.consultarDesarrolladores()) {
            vista.getCmbDesarrollador().addItem(desarrollador);
        }
    }

    private void cargarPuntos() throws Exception {
        vista.getCmbPunto().removeAllItems();

        for (PuntoEstimacion punto : modelo.consultarPuntos()) {
            vista.getCmbPunto().addItem(punto);
        }
    }

    private void registrarVoto() {
        try {
            HistoriaUsuario historia =
                    (HistoriaUsuario) vista.getCmbHistoria().getSelectedItem();

            Desarrollador desarrollador =
                    (Desarrollador) vista.getCmbDesarrollador().getSelectedItem();

            PuntoEstimacion punto =
                    (PuntoEstimacion) vista.getCmbPunto().getSelectedItem();

            if (historia == null || desarrollador == null || punto == null) {
                throw new IllegalArgumentException(
                        "Seleccione historia, desarrollador y punto."
                );
            }

            Voto voto = new Voto(
                    0,
                    desarrollador.getId(),
                    historia.getId(),
                    punto.getId()
            );

            modelo.registrarVoto(voto);

            JOptionPane.showMessageDialog(
                    vista,
                    "Voto registrado correctamente."
            );

            consultarVotos();

        } catch (Exception e) {
            mostrarError("No se pudo registrar el voto:\n" + e.getMessage());
        }
    }

    private void consultarVotos() {
        try {
            HistoriaUsuario historia =
                    (HistoriaUsuario) vista.getCmbHistoria().getSelectedItem();

            if (historia == null) {
                throw new IllegalArgumentException("Seleccione una historia.");
            }

            List<VotoDetalle> votos =
                    modelo.consultarVotos(historia.getId());

            DefaultTableModel tabla =
                    (DefaultTableModel) vista.getTblVotos().getModel();

            tabla.setRowCount(0);

            for (VotoDetalle voto : votos) {
                tabla.addRow(new Object[]{
                    voto.getDesarrollador(),
                    voto.getHistoria(),
                    voto.getPuntos()
                });
            }

        } catch (Exception e) {
            mostrarError("Error al consultar votos:\n" + e.getMessage());
        }
    }

    private void calcularEstimacion() {
        try {
            HistoriaUsuario historia =
                    (HistoriaUsuario) vista.getCmbHistoria().getSelectedItem();

            if (historia == null) {
                throw new IllegalArgumentException("Seleccione una historia.");
            }

            ResultadoEstimacion resultado =
                    modelo.calcularEstimacion(historia.getId());

            if (resultado == null) {
                vista.setTxtEstimacion("Sin resultado");
                return;
            }

            vista.setTxtEstimacion(
                    "Promedio: " + resultado.getEstimacionPromedio()
                    + " | Votos: " + resultado.getCantidadVotos()
            );

        } catch (Exception e) {
            mostrarError("Error al calcular la estimación:\n" + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                vista,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
