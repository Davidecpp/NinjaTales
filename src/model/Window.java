package model;

import view.GraphicPanel;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
public class Window  {
    public JFrame window;
    public Window(GraphicPanel gp){
        window = new JFrame("GAME");
        window.setResizable(false);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.add(gp);
        window.pack();
        window.setVisible(true);
    }
}
