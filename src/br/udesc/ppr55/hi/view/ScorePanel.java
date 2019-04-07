package br.udesc.ppr55.hi.view;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
    
    private JLabel iconImage;
    private Image image;
    
    public ScorePanel() {
        try {
            this.setBackground(Color.white);
            this.image = ImageIO.read(new File("src/br/udesc/ppr55/hi/view/images/image--000.png"));
            this.iconImage = new JLabel(new ImageIcon(this.image));
            this.add(this.iconImage);
        } catch (IOException ex) {
            Logger.getLogger(ScorePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
