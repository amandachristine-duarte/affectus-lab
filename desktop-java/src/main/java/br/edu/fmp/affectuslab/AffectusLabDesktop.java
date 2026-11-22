
package br.edu.fmp.affectuslab;

import br.edu.fmp.affectuslab.view.TelaLogin;
import javax.swing.SwingUtilities;

public class AffectusLabDesktop {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });

    }
}
