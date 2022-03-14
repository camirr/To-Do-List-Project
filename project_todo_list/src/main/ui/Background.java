package ui;

import javax.swing.*;
import java.awt.*;

// To-do list's background
// This class references code from
// "https://stackoverflow.com/questions/523767/how-to-set-background-image-in-java"
public class Background extends JComponent {
    private Image image;

    // EFFECTS: constructs a background image
    public Background(Image image) {
        this.image = image;
    }

   // MODIFIES: this
   // EFFECTS: draws the image to Component
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }


}
