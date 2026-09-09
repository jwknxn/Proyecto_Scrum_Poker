package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.HistoriaUsuario;
import modelo.ProductOwner;
import modelo.ScrumPokerModelo;
import vista.HistoriasUsuario;

public class HistoriaUsuarioControlador {

    private final HistoriasUsuario vista;
    private final ScrumPokerModelo modelo;

    public HistoriaUsuarioControlador(HistoriasUsuario vista, ScrumPokerModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        iniciarEventos();
        cargarProductOwners();
        cargarHistorias();
    }

    private void iniciarEventos() {
        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnLimpiar().addActionListener(e -> limpiar());
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    private void cargarProductOwners() {
        try {
            vista.getCmbProductOwner().removeAllItems();

            for (ProductOwner po : modelo.consultarProductOwners()) {
                vista.getCmbProductOwner().addItem(po);
            }

        } catch (Exception e) {
            mostrarError("Error al cargar Product Owners:\n" + e.getMessage());
        }
    }

    private void guardar() {
        try {
            ProductOwner po =
                    (ProductOwner) vista.getCmbProductOwner().getSelectedItem();

            int idPO = po == null ? 0 : po.getId();

            HistoriaUsuario historia = new HistoriaUsuario(
                    0,
                    vista.getTxtTitulo(),
                    vista.getTxtDescripcion(),
                    idPO
            );

            modelo.registrarHistoria(historia);

            JOptionPane.showMessageDialog(
                    vista,
                    "Historia de Usuario registrada correctamente."
            );

            cargarHistorias();
            limpiar();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void cargarHistorias() {
        try {
            List<HistoriaUsuario> lista = modelo.consultarHistorias();
            DefaultTableModel tabla =
                    (DefaultTableModel) vista.getTblHistorias().getModel();

            tabla.setRowCount(0);

            for (HistoriaUsuario historia : lista) {
                tabla.addRow(new Object[]{
                    historia.getId(),
                    historia.getTitulo(),
                    historia.getDescripcion(),
                    historia.getIdPO()
                });
            }

        } catch (Exception e) {
            mostrarError("Error al cargar historias:\n" + e.getMessage());
        }
    }

    private void limpiar() {
        vista.setTxtId("");
        vista.setTxtTitulo("");
        vista.setTxtDescripcion("");

        if (vista.getCmbProductOwner().getItemCount() > 0) {
            vista.getCmbProductOwner().setSelectedIndex(0);
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
