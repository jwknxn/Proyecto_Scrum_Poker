package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Desarrollador;
import modelo.Participante;
import modelo.ProductOwner;
import modelo.ScrumPokerModelo;
import vista.Participantes;

public class ParticipanteControlador {

    private final Participantes vista;
    private final ScrumPokerModelo modelo;

    public ParticipanteControlador(Participantes vista, ScrumPokerModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        iniciarEventos();
        cargarParticipantes();
    }

    private void iniciarEventos() {
        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnLimpiar().addActionListener(e -> limpiar());
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    private void guardar() {
        try {
            Participante participante = crearParticipante();

            modelo.registrarParticipante(participante);

            JOptionPane.showMessageDialog(
                    vista,
                    "Participante registrado correctamente."
            );

            cargarParticipantes();
            limpiar();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private Participante crearParticipante() {
        String rol = vista.getCboRol();

        if (rol.equals("Desarrollador")) {
            return new Desarrollador(
                    0,
                    vista.getTxtNombre(),
                    vista.getTxtApellido()
            );
        }

        if (rol.equals("Product Owner")) {
            return new ProductOwner(
                    0,
                    vista.getTxtNombre(),
                    vista.getTxtApellido()
            );
        }

        throw new IllegalArgumentException("Seleccione un rol válido.");
    }

    private void cargarParticipantes() {
        try {
            List<Participante> lista = modelo.consultarParticipantes();
            DefaultTableModel tabla =
                    (DefaultTableModel) vista.getTblParticipantes().getModel();

            tabla.setRowCount(0);

            for (Participante participante : lista) {
                tabla.addRow(new Object[]{
                    participante.getId(),
                    participante.getNombre(),
                    participante.getApellido(),
                    participante.obtenerRol()
                });
            }

        } catch (Exception e) {
            mostrarError("Error al cargar participantes:\n" + e.getMessage());
        }
    }

    private void limpiar() {
        vista.setTxtId("");
        vista.setTxtNombre("");
        vista.setTxtApellido("");
        vista.setCboRol("Desarrollador");
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
