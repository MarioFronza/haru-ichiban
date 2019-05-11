package br.udesc.ppr55.hi;

import br.udesc.ppr55.hi.view.ChoiceFrame;
import javax.swing.SwingUtilities;

/**
 * Main class that is responsible for starting the game
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 2.0.0
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
