package br.udesc.ppr55.hi.main;

import br.udesc.ppr55.hi.view.ChoiceFrame;
import javax.swing.SwingUtilities;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */

public class HaruIchiban {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChoiceFrame().start();
            }
        });
    }

}
