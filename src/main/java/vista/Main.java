package vista;

import controlador.MenuControlador;
import modelo.ScrumPokerModelo;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Se mantiene el estilo de Java por defecto.
        }

        SwingUtilities.invokeLater(() -> {
            ScrumPokerModelo modelo = new ScrumPokerModelo();
            Menu menu = new Menu();

            new MenuControlador(menu, modelo);

            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }
}
